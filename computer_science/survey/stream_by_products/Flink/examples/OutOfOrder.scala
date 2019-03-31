/**
 * We need to setup the socket first
 * Shell> nc -l 9235
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * This code can handle out of order, but the state will be large. 
 */
val event = senv.socketTextStream("localhost", 9235, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1).toInt, split(2), split(3).toInt, split(4).toInt)
})
val eventTable = event.toTable(stenv, 'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time, 'proc_time.proctime)
stenv.registerTable("Event", eventTable)

val eventPrecedingCumulativeSumTable = stenv.sqlQuery("""
    |select 
    |   a.event_time,
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

eventPrecedingCumulativeSumTable.toRetractStream[Row].print()
senv.execute("My streaming program")
