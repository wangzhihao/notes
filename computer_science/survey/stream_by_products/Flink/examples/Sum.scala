/**
 * We need to setup the socket first
 * Shell> nc -l 9234 
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 */
val numbers = senv.socketTextStream("localhost", 9234, '\n')
val numbersTable = numbers.toTable(stenv, 'number)
stenv.registerTable("Numbers", numbersTable)

/*
scala> stenv.explain(table)
res2: String =
"== Abstract Syntax Tree ==
LogicalAggregate(group=[{}], EXPR$0=[SUM($0)])
  LogicalProject($f0=[CAST($0):INTEGER])
    LogicalTableScan(table=[[Numbers]])

== Optimized Logical Plan ==
DataStreamGroupAggregate(select=[SUM($f0) AS EXPR$0])
  DataStreamCalc(select=[CAST(number) AS $f0])
    DataStreamScan(table=[[_DataStreamTable_0]])

== Physical Execution Plan ==
Stage 1 : Data Source
        content : collect elements with CollectionInputFormat

        Stage 2 : Operator
                content : from: (number)
                ship_strategy : REBALANCE

                Stage 3 : Operator
                        content : select: (CAST(number) AS $f0)
                        ship_strategy : FORWARD

                        Stage 5 : Operator
                                content : select: (SUM($f0) AS EXPR$0)
                                ship_strategy : HASH

"
 */ 
stenv.sqlQuery("select sum(cast(number as int)) from Numbers n").toRetractStream[Row].print()

senv.execute("My streaming program")
