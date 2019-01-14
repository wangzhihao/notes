# Gang of four
## Template method pattern

I guess Template method pattern is ubiquitous, since it's so naturally to refactor common code out into one place.

## Visitor Design Pattern

[Here](https://stackoverflow.com/a/255224/1494097) is a nice comment on this topic (and its references). Some real world examples include [Guice](https://github.com/google/guice/wiki/InspectingModules), [Presto QueryContextVisitor](https://github.com/prestodb/presto/blob/master/presto-main/src/main/java/com/facebook/presto/memory/QueryContextVisitor.java).
# Others
## Registry Pattern / Service Locator Pattern

Registry pattern enables us to maintain a bunch of resources, like runtime objects in a central place. From this point of view, it shares some common idea with Singleton pattern and Dependency Injection. However, Singleton pattern only allows us to reuse one object, Registry pattern allows us to use multiple objects even of the same type [[1]](https://www.brandonsavage.net/use-registry-to-remember-objects-so-you-dont-have-to/). When compared with Dependency Injection, we can see Registry pattern is programmatically managed and designed for a specific single purpose, e.g. [RefreshRegistry](https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/ipc/RefreshRegistry.java), [CodecRegistry](https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/io/erasurecode/CodecRegistry.java), or [MetricsRegistry](https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/metrics2/lib/MetricsRegistry.java). If the consumers can actively register and unregister themselves from the Registry, then it would be more distinctive from Dependency Injection since a client-service model is involved. See the example of [Hadoop Registry](https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/site/markdown/registry/hadoop-registry.md). 

Another distinction is that Dependency Injection automatically handles things for you while Registry pattern needs your explicit directions, as the following quote from [Guice code](https://github.com/google/guice/blob/master/core/src/com/google/inject/Injector.java#L28-L29):
> This "behind-the-scenes" operation is what distinguishes dependency injection from its cousin, the service locator pattern.
