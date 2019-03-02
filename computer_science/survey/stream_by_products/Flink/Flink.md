[A great dissertation](http://kth.diva-portal.org/smash/record.jsf?pid=diva2%3A1240814&dswid=5874) by [Paris Carbone](https://www.kth.se/profile/parisc/)

Flink started from a fork of [Stratosphere](http://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.640.7744&rep=rep1&type=pdf)’s distributed execution engine 

[Alexander Alexandrov](https://www.dima.tu-berlin.de/menue/staff/alexander_alexandrov) one of the original Flink creators.

Talks by Stephan Ewen, one of the original Flink creators.
* https://www.youtube.com/watch?v=GB-icNc9QtE

[Daniel Warneke](https://home.apache.org/~warneke/) etc. explored the problem that how to support auto scaling in big data processing via [project Nephele](http://stratosphere.eu/assets/papers/Nephele_09.pdf), which divides the whole exectution process into stages and snapshots each stage in a blocked-way before going into the next stage, to make it possible to apply different computing resource to different stage. Nephele later became Flink's processing framework layer. 


## Tiny questions
1. What's the purpose of SinkJoiner?


## PACT code analysis

The analysis is based on version [v0.4-rc1](https://github.com/apache/flink/tree/v0.4-rc1), which is basically the Stratosphere code base.
>
> CoGroupOperator represents a CoGroup InputContract of the PACT Programming Model.
> InputContracts are second-order functions. They have one or multiple input sets of records and a first-order
> user function (stub implementation).
> CoGroup works on two inputs and calls the first-order user function of a {@link CoGroupFunction}
> with the groups of records sharing the same key (one group per input) independently.
>
From the doc of [CoGroupOperator](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-java/src/main/java/eu/stratosphere/api/java/record/operators/CoGroupOperator.java). 
**Why second-order function?** The function which processes data directly is called first-order function, like the [CoGroupFunction](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-java/src/main/java/eu/stratosphere/api/java/record/functions/CoGroupFunction.java). The function which processes functions is called high-order function, more specifically, the function which processes the first-order function is called second-order function, like [CoGroupOperator](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-java/src/main/java/eu/stratosphere/api/java/record/operators/CoGroupOperator.java).

2. What the platform offers is to partition data into sections, and invoke CoGroupFunction for each section.

Take the [PairwiseSP](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-examples/stratosphere-java-examples/src/main/java/eu/stratosphere/example/java/record/shortestpaths/PairwiseSP.java) as an example, which is to calculate shortest path for every pair of points(floyd-algorithm). We need to write some operators like the [CoGroupOperator](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/operators/CoGroupOperator.scala) to drive the workflow, in which the operation will call the corresponding function like [CoGroupFunctionBase](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/functions/CoGroupFunction.scala#L23). Inside the function, [Dataset](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/DataSet.scala) is manipulated. The [cogroup operation](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/operators/CoGroupOperator.scala) uses [Marcos](https://docs.scala-lang.org/overviews/macros/overview.html).

# Reference
* http://www.vldb.org/pvldb/vol10/p1718-carbone.pdf
* http://www.diva-portal.org/smash/get/diva2:1059537/FULLTEXT01.pdf
Alexandrov, Alexander, et al. "The stratosphere platform for big data analytics." The VLDB Journal—The International Journal on Very Large Data Bases 23.6 (2014): 939-964.


# Flink SQL survey
Study [this code](https://github.com/apache/flink/blob/master/flink-examples/flink-examples-batch/src/main/scala/org/apache/flink/examples/scala/relational/TPCHQuery3.scala) for [TPC-H Query 3 (Q3)](http://www.tpc.org/tpc_documents_current_versions/pdf/tpc-h_v2.18.0.pdf) However, this is for batch.

A sample:
https://aws.amazon.com/blogs/big-data/build-a-real-time-stream-processing-pipeline-with-apache-flink-on-aws/


