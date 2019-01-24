> These problems motivated us to change direction. Messaging systems seem to target low-latency settings rather than the high-volume scale-out deployment that we required.

[A good summary](http://sites.computer.org/debull/A12june/pipeline.pdf) of performance tuning of kafka. Here is [the 0.8.0 source code list of kafka](https://pastebin.com/raw/4Vbc3VTB) which is a compact and small code base to get a whole view quickly.

[Another performance tuning](https://engineering.linkedin.com/kafka/benchmarking-apache-kafka-2-million-writes-second-three-cheap-machines) report of Kafka

Kafka allows replay, while in normal queue systems the message disappear after consumed.

> If a broker goes down, any message stored on it not yet consumed becomes unavailable. If the storage system on a broker is permanently damaged, any unconsumed message is lost forever.  In the future, we plan to add built-in replication in Kafka to redundantly store each message on multiple brokers.

Kafka is not reliable, at least in its intial version inferred from [this paper](https://www.microsoft.com/en-us/research/wp-content/uploads/2017/09/Kafka.pdf). Althougth it claims [to address it with replication](https://www.slideshare.net/JiangjieQin/no-data-loss-pipeline-with-apache-kafka-49753844?next_slideshow=1), concerns still remain.

TODO: Compare Kinesis vs Kafka vs DDB stream. They look like similar
> Kinesis Streams is like Kafka Core. Kinesis Analytics is like Kafka Streams. A Kinesis Shard is like Kafka Partition.

http://cloudurable.com/blog/kinesis-vs-kafka/index.html

# Reference
* Building LinkedIn’s Real-time Activity Data Pipeline, Ken Goodhope, Joel Koshy, Jay Kreps, Neha Narkhede, Richard Park, Jun Rao, Victor Yang Ye
* https://cwiki.apache.org/confluence/display/KAFKA/Kafka+papers+and+presentations
* https://en.wikipedia.org/wiki/Zero-copy
