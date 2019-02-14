## Fault tolerance
> Note: For this mechanism to realize its full guarantees, the data stream source (such as message queue or broker) needs to be able to rewind the stream to a defined recent point. Apache Kafka has this ability and Flink’s connector to Kafka exploits this ability. See Fault Tolerance Guarantees of Data Sources and Sinks for more information about the guarantees provided by Flink’s connectors.
-- https://ci.apache.org/projects/flink/flink-docs-release-1.7/internals/stream_checkpointing.html

However the Kinesis default retention can up to only 7 days at maximum.

## Lamdba architecture.

Independent different fields should not delay each other.
