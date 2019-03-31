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

val inventory = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1).toInt, split(2), split(3).toDouble, split(4))
})
val inventoryTable = inventory.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Inventory", inventoryTable)

// 1,1,X00,53.2,2018-01-01
val event = senv.socketTextStream("localhost", 9235, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1).toInt, split(2), split(3).toDouble, split(4))
})
val eventTable = event.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Event", eventTable)

val eventSumTable = stenv.sqlQuery("""
      | select
      | merchant_id, marketplace_id, fnsku,
      | sum(quantity) as event_total_quantity
      | from Event
      | group by merchant_id, marketplace_id, fnsku
      """)
stenv.registerTable("EventSum", eventSumTable)


val eventPrecedingCumulativeSumTable = stenv.sqlQuery("""
     | select
     |   merchant_id, marketplace_id, fnsku, quantity,
     |   sum(cast(quantity as int)) over (order by proc_time range unbounded preceding) as cumulative_quantity,
     |   event_time
     | from Event
     """.stripMargin)
stenv.registerTable("EventPrecedingCumulativeSum", eventPrecedingCumulativeSumTable)

val eventFollowingCumulativeSumTable = stenv.sqlQuery("""
     | select
     |   p.merchant_id, p.marketplace_id, p.fnsku, p.quantity,
     |   s.event_total_quantity - p.cumulative_quantity as cumulative_quantity,
     |   p.event_time
     | from EventSum s, EventPrecedingCumulativeSum p
     | where s.merchant_id = p.merchant_id
     | and s.marketplace_id = p.marketplace_id
     | and s.fnsku = p.fnsku
     """.stripMargin)

stenv.registerTable("EventFollowingCumulativeSum", eventFollowingCumulativeSumTable)

val inventoryAgeTable = stenv.sqlQuery("""
     | select
     |   I.merchant_id, I.marketplace_id, I.fnsku,
     |   case when E.quantity > I.quantity - E.cumulative_quantity then
     |       case when I.quantity - E.cumulative_quantity > 0 then I.quantity - E.cumulative_quantity else 0 end
     |   else
     |       E.quantity
     |   end as quantity,
     |   E.event_time
     | from Inventory I, EventFollowingCumulativeSum E
     | where I.merchant_id = E.merchant_id
     | and I.marketplace_id = E.marketplace_id
     | and I.fnsku = E.fnsku
     """.stripMargin)

inventoryAgeTable.toRetractStream[Row].print()
senv.execute("My streaming program")
