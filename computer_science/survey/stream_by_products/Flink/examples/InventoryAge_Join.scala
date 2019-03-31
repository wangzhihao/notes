/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 * Shell> nc -l 9235
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Since Flink doesn't support following unbounded window by default, we can implment it by maintaining
 * two streams: a sum stream and a preceding unbounded window stream. Substract them gives the answer.
 */

// 1,1,X00,100,2019-03-28
val inventory = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val eventTime = new java.sql.Timestamp(format.parse(split(4)).getTime())
    (split(0).toInt, split(1).toInt, split(2), split(3).toInt, eventTime)
})
val inventoryTable = inventory.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Inventory", inventoryTable)

// 1,1,X00,50,2018-03-26
// 1,1,X00,25,2018-03-25
// 1,1,X00,100,2018-03-24
val event = senv.socketTextStream("localhost", 9235, '\n').map(x => {
    val split = x.split(",")
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val eventTime = new java.sql.Timestamp(format.parse(split(4)).getTime())
    (split(0).toInt, split(1).toInt, split(2), split(3).toInt, eventTime)
})
val eventTable = event.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Event", eventTable)

// Event table should be maintained small to avoid a huge state.
val eventCumulativeSumTable = stenv.sqlQuery("""
    |select
    |   a.merchant_id, a.marketplace_id, a.fnsku, a.event_time,
    |   sum(b.quantity) as cumulative_quantity
    | from Event a
    | join Event b
    | on
    | a.merchant_id = b.merchant_id
    | and a.marketplace_id = b.marketplace_id
    | and a.fnsku = b.fnsku
    | where a.event_time >= b.event_time
    | group by a.event_time
    """.stripMargin)

stenv.registerTable("EventCumulativeSum", eventCumulativeSumTable)

val inventoryAgeTable = stenv.sqlQuery("""
     | select
     |   I.merchant_id, I.marketplace_id, I.fnsku,
     |   case when E.quantity > I.quantity - E.cumulative_quantity then
     |       case when I.quantity - E.cumulative_quantity > 0 then I.quantity - E.cumulative_quantity else 0 end
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

