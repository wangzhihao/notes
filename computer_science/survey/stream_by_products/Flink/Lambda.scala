/**
 * This code run with flink scala shell in local mode as follows:
 *
 * Shell> ./build-target/bin/start-scala-shell.sh local
 */
import java.sql.Timestamp
import org.apache.flink.streaming.api.TimeCharacteristic
senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

// Due to scala Class.getSimpleName bug https://github.com/scala/bug/issues/2034 , don't use case class.
// Dummy Ascending Timestamps and Watermark for simplicity.
val absoluteStream = senv.fromElements(("jim", 12, Timestamp.valueOf("2019-02-20 00:00:00")), ("Amy", 10, Timestamp.valueOf("2019-02-28 00:00:00")), ("jim", 20, Timestamp.valueOf("2019-03-02 00:00:00"))).assignAscendingTimestamps(_._3.getTime())
val deltaStream = senv.fromElements(("jim", 1, Timestamp.valueOf("2019-02-20 00:00:59")), ("Amy", 3, Timestamp.valueOf("2019-02-28 12:00:00")), ("jim", 8, Timestamp.valueOf("2019-03-02 00:23:00"))).assignAscendingTimestamps(_._3.getTime())
val absoluteTable = absoluteStream.toTable(stenv, 'merchant, 'skus, 'snapshot_date.rowtime)
val deltaTable = deltaStream.toTable(stenv, 'merchant, 'skus, 'snapshot_date.rowtime)
val latestAbsolute = absoluteTable.createTemporalTableFunction('snapshot_date, 'merchant) 

stenv.registerFunction("LatestAbsolute", latestAbsolute)
stenv.registerTable("Absolute", absoluteTable)
stenv.registerTable("Delta", deltaTable)



// Use Dynamic Table and Time-windowed Join to implement Lambda Architechture on Flink
stenv.sqlQuery("select a.merchant, a.skus, a.snapshot_date, a.skus + sum(d.skus) as real_time_skus from Absolute a, Delta d where a.merchant = d.merchant and d.snapshot_date > a.snapshot_date group by a.merchant, a.skus, a.snapshot_date").toRetractStream[Row].print()
// Use [Temporal table](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/table/streaming/joins.html) to setup Lambda Achitechture on Flink
stenv.sqlQuery("select * from Delta d, LATERAL TABLE (LatestAbsolute(d.snapshot_date)) a where a.merchant = d.merchant").toAppendStream[Row].print()
senv.execute("My streaming program")

