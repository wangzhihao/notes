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

val inventory = senv.socketTextStream("localhost", 9234, '\n')
val inventoryTable = numbers.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'total_quantity, 'event_time.proctime)
stenv.registerTable("Inventory", inventoryTable)

val event = senv.socketTextStream("localhost", 9235, '\n')
val eventTable = numbers.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_type, 'event_time.proctime)
stenv.registerTable("Event", eventTable)

val inflowSumTable = stenv.sqlQuery("""
      | select
      | merchant_id, marketplace_id, fnsku,
      | sum(cast(quantity as int)) as event_total_quantity
      | from Event
      | where event_type = 'inflow'
      """)
stenv.registerTable("InflowSum", inflowSumTable)

val inflowFollowingCumulativeSumTable = stenv.sqlQuery("""
     | select
     |   merchant_id, marketplace_id, fnsku,
     |   sum(cast(quantity as int)) over (order by snapshot_date range unbounded preceding) as cumulative_quantity,
     |   event_time
     | from Event
     | where event_type = 'inflow'
     """.stripMargin)
stenv.registerTable("InflowFollowingSum", inflowFollowingCumulativeSumTable)

val inflowFollowingCumulativeSumTable = stenv.sqlQuery("""
     | select
     |   p.merchant_id, p.marketplace_id, p.fnsku,
     |   s.event_total_quantity - p.cumulative_quantity as cumulative_quantity,
     |   p.event_time
     | from InflowSum s, InflowFollowingSum p
     | where s.merchant_id = p.merchant_id
     | and s.marketplace_id = p.marketplace_id
     | and s.fnsku = p.fnsku
     """.stripMargin)

stenv.registerTable("InflowFollowingSum", inflowFollowingCumulativeSumTable)

val inventoryAgeTable = stenv.sqlQuery("""
     | select
     |   I.merchant_id, I.marketplace_id, I.fnsku,
     |   case when E.quantity > I.total_quantity - E.cumulative_quantity then
     |       case when I.total_quantity - E.cumulative_quantity > 0 then I.total_quantity - E.cumulative_quantity else 0 end
     |   else
     |       E.quantity
     |   end as quantity,
     |   E.event_time
     | from InventoryAge I, InflowFollowingSum E
     | where I.merchant_id = E.merchant_id
     | and I.marketplace_id = E.marketplace_id
     | and I.fnsku = E.fnsku
     """.stripMargin)

followingCumulativeSumTable.toRetractStream[Row].print()


senv.execute("My streaming program")
