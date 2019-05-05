
/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * Use maxBy to get the latest version
 * Input
 * 1,1,X00,100,2019-03-28
 * 1,1,X00,200,2019-03-29
 * 1,1,X00,80,2019-03-27
 * Output
 * (1,1,X00,100,2019-03-28 00:00:00.0)
 * (1,1,X00,200,2019-03-29 00:00:00.0) 
 * (1,1,X00,200,2019-03-29 00:00:00.0) 
 */
def toTimestamp(date: String) = {
    val format = new java.text.SimpleDateFormat("yyyy-MM-dd")
    new java.sql.Timestamp(format.parse(date).getTime())
}

import java.sql.Timestamp
import org.apache.flink.streaming.api.TimeCharacteristic
senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

val inventory = {
  senv.socketTextStream("localhost", 9234, '\n')
    .map(x => {
      val split = x.split(",")
      //'merchant_id, 'marketplace_id, 'fnsku, 'quantity, 'event_time
      (split(0).toInt, split(1).toInt, split(2), split(3).toInt, toTimestamp(split(4)))
    })
    .keyBy(0, 1, 2)
    .maxBy(4)
    .print()
}

senv.execute("My streaming program")
