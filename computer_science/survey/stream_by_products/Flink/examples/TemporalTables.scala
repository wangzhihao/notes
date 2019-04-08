/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * ./flink-table/flink-table-planner/src/test/scala/org/apache/flink/table/runtime/stream/sql/TemporalJoinITCase.scala
 */

//2,EU
//16,Yen
val ordersData = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    (split(0).toInt, split(1))
})

//EU,114
//Yen,1
val ratesHistoryData = senv.socketTextStream("localhost", 9235, '\n').map(x => {
    val split = x.split(",")
    (split(0), split(1).toInt)
})

val orders = ordersData.toTable(stenv, 'amount, 'currency, 'proctime.proctime)

val ratesHistory = ratesHistoryData.toTable(stenv, 'currency, 'rate, 'proctime.proctime)

stenv.registerTable("Orders", orders)
stenv.registerTable("RatesHistory", ratesHistory)
stenv.registerFunction("Rates",ratesHistory.createTemporalTableFunction('proctime, 'currency))

stenv.sqlQuery("select * from Orders").toAppendStream[Row].print()
stenv.sqlQuery("select * from RatesHistory").toAppendStream[Row].print()

stenv.sqlQuery("""
|SELECT
|  o.amount * r.rate AS amount
|FROM
|  Orders AS o,
|  LATERAL TABLE (Rates(o.proctime)) AS r
|WHERE r.currency = o.currency
|""".stripMargin).toAppendStream[Row].print()

senv.execute("My streaming program")

