/**
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Emit periodical events(date) in each 3 seconds. 
 */
import org.apache.flink.streaming.api.functions.source.SourceFunction

def getPeriodicalEventStream(delay: Long, period: Long): DataStream[String] = {
    senv.getSource(new SourceFunction[String]() {
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
val periodicalEvent = getPeriodicalEventStream(0, 3000L)
periodicalEvent.print()
senv.execute("My streaming program")

