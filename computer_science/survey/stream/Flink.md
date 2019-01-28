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

The entry point is [Dataset](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/DataSet.scala). Take the [PairwiseSP](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-examples/stratosphere-java-examples/src/main/java/eu/stratosphere/example/java/record/shortestpaths/PairwiseSP.java) as an example for [cogroup operation](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/operators/CoGroupOperator.scala), it uses [Marcos](https://docs.scala-lang.org/overviews/macros/overview.html), then it calls [CoGroupFunctionBase](https://github.com/apache/flink/blob/v0.4-rc1/stratosphere-scala/src/main/scala/eu/stratosphere/api/scala/functions/CoGroupFunction.scala#L23).

# Reference
* http://www.vldb.org/pvldb/vol10/p1718-carbone.pdf
* http://www.diva-portal.org/smash/get/diva2:1059537/FULLTEXT01.pdf
Alexandrov, Alexander, et al. "The stratosphere platform for big data analytics." The VLDB Journal—The International Journal on Very Large Data Bases 23.6 (2014): 939-964.
