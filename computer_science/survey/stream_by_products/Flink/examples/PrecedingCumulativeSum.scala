/**
 * We need to setup the socket first
 * Shell> nc -l 9234 
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 */
val numbers = senv.socketTextStream("localhost", 9234, '\n')
val numbersTable = numbers.toTable(stenv, 'number, 'snapshot_date.proctime)
stenv.registerTable("Numbers", numbersTable)

stenv.sqlQuery("""
               | select 
               |   cast(number as int) as number,
               |   sum(cast(number as int)) over (order by snapshot_date range unbounded preceding) as cumulative_numbers
               | from Numbers n
               """.stripMargin).toRetractStream[Row].print()

senv.execute("My streaming program")
