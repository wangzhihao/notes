/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Use a dummy field to force cartesian join.
 */

import org.apache.flink.streaming.api.functions.source.SourceFunction

def getPeriodicalEventStream(delay: Long, period: Long): DataStream[String] = {
    senv.addSource(new SourceFunction[String]() {
      @volatile var isRunning = true
      val SLEEP_INTERVAL = 1000L;
      def run(ctx: SourceFunction.SourceContext[String]) {
        val t = new java.util.Timer()
        val task = new java.util.TimerTask {
          def run() =  ctx.collect((new java.util.Date().toString()))
        }
        t.schedule(task, delay, period)
        while (isRunning) {
          Thread.sleep(SLEEP_INTERVAL)
        }
      }
      
      def cancel() {
        isRunning = false
      }
    })
} 

val DUMMY = "dummy"
val periodicalEvent = getPeriodicalEventStream(0, 3000L).map((_, DUMMY))
stenv.registerTable("PeriodicalEvents", periodicalEvent.toTable(stenv, 'event, 'dummy))

val date = senv.fromElements((192), (2000), (300000)).map((_, DUMMY))
stenv.registerTable("SnapshotDate", date.toTable(stenv, 'delta, 'dummy))

stenv.sqlQuery("""
   | select TIMESTAMPADD(SECOND, d.delta, CURRENT_TIMESTAMP) from SnapshotDate d, PeriodicalEvents e
   | where d.dummy = e.dummy
    """.stripMargin).toRetractStream[Row].print()

senv.execute("My streaming program")

