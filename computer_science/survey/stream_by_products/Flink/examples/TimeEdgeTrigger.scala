/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 * 
 * This example is not time edge trigger. The time changes won't trigger the re-execution.
 *
 * This link should provide a viable solution: https://ci.apache.org/projects/flink/flink-docs-stable/dev/stream/operators/process_function.html
 */

val number = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt)
})
val numberTable = number.toTable(stenv, 'delta)
stenv.registerTable("Number", numberTable)


val currentTime = stenv.sqlQuery("select TIMESTAMPADD(SECOND, delta, CURRENT_TIMESTAMP) from Number")
currentTime.toRetractStream[Row].print()
senv.execute("My streaming program")
