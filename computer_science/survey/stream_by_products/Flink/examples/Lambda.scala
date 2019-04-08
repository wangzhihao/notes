/**
 * We need to setup the socket first
 * Shell> nc -l 9234
 *
 * This code run with flink scala shell in local mode as follows:
 * Shell> ./build-target/bin/start-scala-shell.sh local
 *
 * 0. Each record in snapshot stream has field snapshot_date.
 * 1. Each record in event stream has field event_time.
 * 2. Only the snapshot with the latest snapshot_date is used.
 * 3. Old snapshots should be able to turncate and to discard.
 * 4. Only the event records later than the latest snapshot are used.
 * 5. Old events should be able to turncate and to discard.
 * 6. A custom fold function can be provided by users to combine the snapshot and the events.
 * 7. Only one unified stream is desired for the final output.
 */
import java.sql.Timestamp
import org.apache.flink.streaming.api.TimeCharacteristic
senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

// Due to scala Class.getSimpleName bug https://github.com/scala/bug/issues/2034 , don't use case class.
// Dummy Ascending Timestamps and Watermark for simplicity.
val absoluteStream = senv.fromElements(
    ("jim", 12, Timestamp.valueOf("2019-02-20 00:00:00")),
    ("Amy", 10, Timestamp.valueOf("2019-02-28 00:00:00")),
    ("jim", 20, Timestamp.valueOf("2019-03-02 00:00:00"))
).assignAscendingTimestamps(_._3.getTime())

val deltaStream = senv.fromElements(
    ("jim", 1, Timestamp.valueOf("2019-02-20 00:00:59")),
    ("Amy", 3, Timestamp.valueOf("2019-02-28 12:00:00")),
    ("jim", 8, Timestamp.valueOf("2019-03-02 00:23:00"))
).assignAscendingTimestamps(_._3.getTime())

val absoluteTable = absoluteStream.toTable(stenv, 'merchant, 'skus, 'snapshot_date.rowtime)
val deltaTable = deltaStream.toTable(stenv, 'merchant, 'skus, 'snapshot_date.rowtime)
val latestAbsolute = absoluteTable.createTemporalTableFunction('snapshot_date, 'merchant)

stenv.registerFunction("LatestAbsolute", latestAbsolute)
stenv.registerTable("Absolute", absoluteTable)
stenv.registerTable("Delta", deltaTable)

// Use Dynamic Table and Time-windowed Join to implement Lambda Architechture on Flink
//
// Error happens: Rowtime attributes must not be in the input rows of a regular join. 
// As a workaround you can cast the time attributes of input tables to TIMESTAMP before.
// ./flink-table/flink-table-planner/src/main/scala/org/apache/flink/table/plan/rules/datastream/DataStreamJoinRule.scala
//
// Use bounded time window can avoid above error.
//
stenv.sqlQuery("""
              | select 
              |   a.merchant, a.skus, a.snapshot_date, a.skus + sum(d.skus) as real_time_skus 
              | from 
              |   Absolute a, Delta d 
              | where 
              |   a.merchant = d.merchant and d.snapshot_date > a.snapshot_date and d.snapshot_date < a.snapshot_date + INTERVAL '7' DAY
              | group by 
              |   a.merchant, a.skus, a.snapshot_date
              """.stripMargin).toRetractStream[Row].print()

// Can we use [Temporal table](https://ci.apache.org/projects/flink/flink-docs-release-1.7/dev/table/streaming/joins.html) to setup
// Lambda Achitechture on Flink?
stenv.sqlQuery("""
              | select * from 
              |   Delta d, LATERAL TABLE (LatestAbsolute(d.snapshot_date)) a 
              | where a.merchant = d.merchant
              """.stripMargin).toAppendStream[Row].print()

senv.execute("My streaming program")

