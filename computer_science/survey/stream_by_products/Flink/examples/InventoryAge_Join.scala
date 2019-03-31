/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 * Shell> nc -l 9235
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 */

def toTimestamp(date: String) = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    new java.sql.Timestamp(format.parse(date).getTime())
}

// 1,1,X00,100,2019-03-28
val inventory = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1).toInt, split(2), split(3).toInt, toTimestamp(split(4)))
})
val inventoryTable = inventory.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Inventory", inventoryTable)

// 1,1,X00,50,2018-03-26
// 1,1,X00,25,2018-03-25
// 1,1,X00,100,2018-03-24
val event = senv.socketTextStream("localhost", 9235, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1).toInt, split(2), split(3).toInt, toTimestamp(split(4)))
})
val rawEventTable = { event
  //fallback to an beginning of history in case of lacking of enough events.
  .union(inventory.map(_.copy(_5 = toTimestamp("1995-01-01"))))
  .toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
}
stenv.registerTable("RawEvent", rawEventTable)

val eventTable = stenv.sqlQuery("""
   | select
   |   merchant_id, marketplace_id, fnsku, event_time, sum(quantity) as quantity
   | from RawEvent
   | group by merchant_id, marketplace_id, fnsku, event_time
    """.stripMargin)

stenv.registerTable("Event", eventTable)


// Event table should be maintained small to avoid a huge state.
// The cumulative sum range [a.event_time, now], inclusive.
val eventCumulativeSumTable = stenv.sqlQuery("""
    |select
    |   a.merchant_id, a.marketplace_id, a.fnsku, a.event_time, a.quantity,
    |   sum(b.quantity) as cumulative_quantity
    | from Event a
    | join Event b
    | on
    | a.merchant_id = b.merchant_id
    | and a.marketplace_id = b.marketplace_id
    | and a.fnsku = b.fnsku
    | where a.event_time <= b.event_time
    | group by a.merchant_id, a.marketplace_id, a.fnsku, a.event_time, a.quantity
    """.stripMargin)

stenv.registerTable("EventCumulativeSum", eventCumulativeSumTable)

val inventoryAgeTable = stenv.sqlQuery("""
     | select
     |   I.merchant_id, I.marketplace_id, I.fnsku,
     |   case when E.cumulative_quantity > I.quantity then
     |       case when I.quantity > E.cumulative_quantity - E.quantity then
     |         I.quantity - E.cumulative_quantity + E.quantity
     |       else 0 end
     |   else
     |       E.quantity
     |   end as quantity,
     |   E.event_time
     | from Inventory I, EventCumulativeSum E
     | where I.merchant_id = E.merchant_id
     | and I.marketplace_id = E.marketplace_id
     | and I.fnsku = E.fnsku
     """.stripMargin)

inventoryAgeTable.toRetractStream[Row].print()
senv.execute("My streaming program")
