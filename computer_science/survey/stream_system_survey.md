# Terminology
* Strong Consistency
* Eventual Consistency
* At-least-once delivery
* Exact-once delivery

# External
1. Algebra: It seems that [CQL](https://www.microsoft.com/en-us/research/wp-content/uploads/2016/02/cql.pdf) gains a lot of popularity. Compares it with Aurora. In Aurora, out of order event is meant to be discarded. This is useful in the cases the event can be replayed or affordable to be missing. However it might not satisfy our requirement. 
    * http://cs.brown.edu/research/borealis/public/
    * http://cs.brown.edu/research/aurora/
    * http://cs.brown.edu/research/aurora/cidr05.borealis.pdf
    * http://eolo.cps.unizar.es/docencia/doctorado/Articulos/DataStreams/Data%20Streams.%20Algorithms%20and%20Applications.pdf
    * https://people.cs.umass.edu/~mcgregor/papers/13-graphsurvey.pdf
    * http://hirzels.com/martin/papers/csur14-streamopt.pdf
2. process calculus 
    * Baeten, Jos CM. "A brief history of process algebra." Theoretical Computer Science 335.2-3 (2005): 131-146.
    * R. Milner. A Calculus of Communicating Systems. Number 92 in Lecture Notes in Computer Science. Springer Verlag, 1980.
    * https://en.wikipedia.org/wiki/Actor_model_and_process_calculi_history
    * https://en.wikipedia.org/wiki/Process_calculus
    * https://en.wikipedia.org/wiki/%CE%A0-calculus
    * https://en.wikipedia.org/wiki/Communicating_sequential_processes
    * Milner, Robin. "A calculus of communicating systems." LNCS 92 (1980). [link](https://drive.google.com/open?id=1LGxFKD2nyv0nLDIVYzSZNhcVTg1CdTJP)
3. [Stream algorithms](https://en.wikipedia.org/wiki/Streaming_algorithm). this item talks about stream algorithms like frequency moments, approximate distinct counts, aggreation over windows. The [FlatFAT](http://www.vldb.org/pvldb/vol8/p702-tangwongsan.pdf) is a nice binary tree data structure to support efficient sliding-window aggregation for those commulative operations like SUM, AVG, MEAN, DEV, MAX etc.
    * http://infolab.stanford.edu/~ullman/mmds/ch4a.pdf
    * [Frequency moments](https://m.tau.ac.il/~nogaa/PDFS/amsz4.pdf)
    * HyperLogLog
    * [Frequent Items](https://people.eecs.berkeley.edu/~satishr/cs270/sp11/rough-notes/Streaming-two.pdf), [top-k problem](http://www.cse.ust.hk/~raywong/comp5331/References/EfficientComputationOfFrequentAndTop-kElementsInDataStreams.pdf)
9. Actor model 
    * https://www.doc.ic.ac.uk/~nd/surprise_97/journal/vol2/pjm2/
    * https://en.wikipedia.org/wiki/History_of_the_Actor_model
    * Agha, Gul A. Actors: A model of concurrent computation in distributed systems. No. AI-TR-844. MASSACHUSETTS INST OF TECH CAMBRIDGE ARTIFICIAL INTELLIGENCE LAB, 1985.

# Implementations
1. Kafka
2. Apache Flink
    * http://www.vldb.org/pvldb/vol10/p1718-carbone.pdf
    * http://www.diva-portal.org/smash/get/diva2:1059537/FULLTEXT01.pdf
    * Lightweight asynchronous snapshots for distributed dataflows
    * https://www.kth.se/profile/parisc/
    * http://kth.diva-portal.org/smash/record.jsf?pid=diva2%3A1240814&dswid=5599
3. Apache Beam
4. Druid
5. Storm
6. Spark streaming
7. IBM streaming
# Internal
* Sable
https://portal2010.amazon.com/sites/sable/public/Shared%20Documents/Deep%20Dive%20on%20Datapath%20-%20May%202016.pdf
* AWS Step function
* AWS Kinesis
* DataPath Precompute
    * https://w.amazon.com/bin/view/Datapath/Precompute
    * https://w.amazon.com/index.php/Datapath/Internal/EventBus/Design
    After confirmed with DataPath team, they don't support scheduled triggers currently. See [the email](https://code.amazon.com/packages/Zhihaow_work_notes/blobs/mainline/--/logs/Re-%20Is%20there%20any%20Scheduled%20trigger%20for%20DataPath%20Precompute.eml?download=1)
# Existing projects
* FIRM
* Excess page
* Stranded
* SMS
# Some problem to concern
We have to be careful to design stream applications. Since it's harder to diagnoise, monitor, backfill than batch applications. 
* mointor: In batch applications, we have clear sense how much data is delayed. In current stream job, this is unclear. Some data might probably be very old without being notice.  
* diagnose: In batch applications, we can simply scan all data for bugs. In stream applications, which I mean key-value store here, we have limited visiblity of overall view. We don't know what are stored in key-value store if the key is unknown. It means diagnose with stream applications are some kind of half-blind.
* backfill: the same reason as above, there is a chance that stale data always resides there, and no way to backfill it if the key is unknown.
* history: stream system should consider to have a cold batch system as companion to store history data.
* durability: Is the stream system durable? Some opeartions like loading shedding might leave data damages. Does the stream system ensure durability? 
* Scalability/Performance: We need some load test to show it is scalable. Given the EMR and Redshift experience, we need to assert it works in very large data quantitatively.
* IMR cost: How much does the solution cost?
* Space consumption: How much space will the system consume? Will the storage be a bottle neck in the stream system?
* test: how to test the behavior after a change deployed. A simple way is that we can sync the stream data into a data warehouse in a scheduled manner, and then query against the data warehouse.
## Aha! moment:
1. Google Cloud Dataflow, which is donated to Apache as Apache Beam, was designed as a marriage of Google frameworks for expressing complex batch pipelines (FlumeJava) and streaming (MillWheel) pipelines.
    Quoted from http://www.unofficialgoogledatascience.com/2016/08/next-generation-tools-for-data-science.html
2. FlumeJava is so similiar to Spark, while in Java version :( .
    PS1: See more ideas: https://7c00.me/houmu/14345.html
    PS2: Weak of Java, function is not first class citizen. Lots of DoFn, EmitFn inside FlumeJava. While Spark leverage on Scala is more elegant. Functional Programming, Monad, high level functions are so related to big data processing framework like FlumeJava and Spark. It's going backwards from Scala to Java.
3. No iterative algorithm that can stop early, including k-means, can be expressed in Beam
    Quoted from http://www.unofficialgoogledatascience.com/2016/08/next-generation-tools-for-data-science.html
4. Streaming system can persist all events, then to replay, to transform, to revise, or to handle them in batch can be possible. Some benckmark [like this](https://ieeexplore.ieee.org/abstract/document/7841533) would also help.
## Questions:
Looks like we are go back to write map-reduce from e.g. Spark-SQL. Will it too complex for coding?
How to do join in stream?
## Resources:
* [Fundamentals of stream processing with Apache Beam] https://www.youtube.com/watch?v=_lne0yuTHCw
* [Foundations of streaming SQL by Tyler Akidau] https://www.youtube.com/watch?v=UlPsp7LaA38
* [Cochran–Mantel–Haenszel] http://www.biostathandbook.com/cmh.html
* https://www.oreilly.com/ideas/the-world-beyond-batch-streaming-101
* https://www.oreilly.com/ideas/the-world-beyond-batch-streaming-102
* https://www.oreilly.com/library/view/streaming-systems/9781491983867
* [Lambda Architecture] http://nathanmarz.com/blog/how-to-beat-the-cap-theorem.html
* http://radar.oreilly.com/2014/07/questioning-the-lambda-architecture.html
* http://radar.oreilly.com/2014/07/why-local-state-is-a-fundamental-primitive-in-stream-processing.html
* https://pkghosh.wordpress.com/2014/09/10/realtime-trending-analysis-with-approximate-algorithms/
* https://databricks.com/blog/2015/01/28/introducing-streaming-k-means-in-spark-1-2.html

## Reference
* [Read] Abadi, Daniel J., et al. "Aurora: a new model and architecture for data stream management." the VLDB Journal 12.2 (2003): 120-139.
* [Partial Read] Alon, Noga, Yossi Matias, and Mario Szegedy. "The space complexity of approximating the frequency moments." Journal of Computer and system sciences 58.1 (1999): 137-147.
* [Reading] Carbone, Paris, et al. "State management in Apache Flink®: consistent stateful distributed stream processing." Proceedings of the VLDB Endowment 10.12 (2017): 1718-1729. [link](http://www.vldb.org/pvldb/vol10/p1718-carbone.pdf)
* [To be Read] Carbone, Paris, et al. "Apache flink: Stream and batch processing in a single engine." Bulletin of the IEEE Computer Society Technical Committee on Data Engineering 36.4 (2015). 
* [Reading] Carbone, P. (2018). Scalable and Reliable Data Stream Processing (PhD dissertation). Retrieved from http://urn.kb.se/resolve?urn=urn:nbn:se:kth:diva-233527

