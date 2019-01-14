## Aha! moments

1. Google Cloud Dataflow, which is donated to Apache as Apache Beam, was designed as a marriage of Google frameworks for expressing complex batch pipelines (FlumeJava) and streaming (MillWheel) pipelines.

    Quoted from http://www.unofficialgoogledatascience.com/2016/08/next-generation-tools-for-data-science.html

2. [S]tream [Qu]ery [Al]gebra (SQuAl). [paper](https://link.springer.com/article/10.1007/s00778-003-0095-z) / [tutorial](http://www.mathcs.emory.edu/~cheung/Courses/584-StreamDB/Syllabus/02-Systems/SQuAl.html)

3. FlumeJava is so similiar to Spark, while in Java version :( .  
    
    PS1: See more ideas: https://7c00.me/houmu/14345.html

    PS2: Weak of Java, function is not first class citizen. Lots of DoFn, EmitFn inside FlumeJava. While Spark leverage on Scala is more elegant. Functional Programming, Monad, high level functions are so related to big data processing framework like FlumeJava and Spark. It's going backwards from Scala to Java.


4. No iterative algorithm that can stop early, including k-means, can be expressed in Beam

    Quoted from http://www.unofficialgoogledatascience.com/2016/08/next-generation-tools-for-data-science.html



## Questions
* Looks like we are go back to write map-reduce from e.g. Spark-SQL. Will it too complex for coding?
* How to do join in stream?

## Resources
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
