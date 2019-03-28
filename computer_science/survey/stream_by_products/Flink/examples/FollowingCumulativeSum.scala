/**
 * We need to setup the socket first
 * Shell> nc -l 9234 
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Sample Input
 * Shell> nc -l 9234
 * 1
 * 2
 * 4
 * 8
 *
 * Sample Output
 * (true,1,1)
 * (true,2,3)
 * (true,4,7)
 * (true,8,15)
 *
 */

val numbers = senv.socketTextStream("localhost", 9234, '\n')
val numbersTable = numbers.toTable(stenv, 'number, 'snapshot_date.proctime)
stenv.registerTable("Numbers", numbersTable)

val sumTable = stenv.sqlQuery("select sum(cast(number as int)) as total from Numbers n")
stenv.registerTable("SumNumbers", sumTable)

val precedingCumulativeSumTable = stenv.sqlQuery("""
     | select 
     |   cast(number as int) as number,
     |   sum(cast(number as int)) over (order by snapshot_date range unbounded preceding) as cumulative_numbers
     | from Numbers n
     """.stripMargin)
stenv.registerTable("PrecedingNumbers", precedingCumulativeSumTable)

val followingCumulativeSumTable = stenv.sqlQuery("""
     | select 
     |   total - cumulative_numbers as following_cumulative_numbers
     | from SumNumbers s, PrecedingNumbers p 
     """.stripMargin)

followingCumulativeSumTable.toRetractStream[Row].print()


senv.execute("My streaming program")
