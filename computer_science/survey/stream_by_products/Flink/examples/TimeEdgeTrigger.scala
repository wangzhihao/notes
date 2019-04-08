/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Use a dummy field to force cartesian join.
 */
val DUMMY = "dummy"
val periodicalTimeTrigger = senv.socketTextStream("localhost", 9234, '\n').map((_, DUMMY))
stenv.registerTable("PeriodicalEvents", periodicalTimeTrigger.toTable(stenv, 'event, 'dummy))

val date = senv.fromElements((192), (2000), (300000)).map((_, DUMMY))
stenv.registerTable("SnapshotDate", date.toTable(stenv, 'delta, 'dummy))

stenv.sqlQuery("""
   | select TIMESTAMPADD(SECOND, d.delta, CURRENT_TIMESTAMP) from SnapshotDate d, PeriodicalEvents e
   | where d.dummy = e.dummy
    """.stripMargin).toRetractStream[Row].print()

senv.execute("My streaming program")


