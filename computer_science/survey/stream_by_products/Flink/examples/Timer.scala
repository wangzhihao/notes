/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * To prevent the stream is shut down too early even before the timer is triggered, we need to use a socket stream instead of a collection stream.
*/

import org.apache.flink.streaming.api.functions.ProcessFunction
import org.apache.flink.util.Collector

/**
  * The data type stored in the state
  */
case class CountWithTimestamp(key: String, count: Long, lastModified: Long)

/**
  * The implementation of the ProcessFunction that maintains the count and timeouts
  */
class CountWithTimeoutFunction extends ProcessFunction[(String, String), (String, Long)] {

  override def processElement(value: (String, String), ctx: ProcessFunction[(String, String), (String, Long)]#Context, out: Collector[(String, Long)]): Unit = {
    out.collect("hi", 1L)
    println(s"currentProcessingTime = ${new java.util.Date(ctx.timerService.currentProcessingTime)}")
    ctx.timerService.registerProcessingTimeTimer(ctx.timerService.currentProcessingTime + 3000)
  }

  override def onTimer(timestamp: Long, ctx: ProcessFunction[(String, String),(String, Long)]#OnTimerContext, out: Collector[(String, Long)]): Unit = {
    println("timer callback is invoked.")
    println(s"ctx.timestamp = ${new java.util.Date(ctx.timestamp)}")
    out.collect("hello", 2L)
  }
}

val number = senv.socketTextStream("localhost", 9234, '\n').map(x => {
    val split = x.split(",")
    (split(0), split(1))
}).keyBy(0).process(new CountWithTimeoutFunction())

number.print()
senv.execute("My streaming program")
