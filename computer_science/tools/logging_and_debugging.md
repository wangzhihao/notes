### Logging & Debugging

When we troubleshoot the runtime issues of an application which is of a complex code base or at remote environment. It will be extremely useful to get extra information to assist our diagnosis. Usually it's hard to recompile such an application. It might be time-consuming to recompile or just impossible if the issues involve a lot of third parties dependencies.

Logging can serve as the information source for our diagnosis. It doesn't need to recompile the application, but configuration changes are suffcient. Here are some examples to inject our custom configurations into Java applications.

For **Log4j** we can set the system property `log4j.configurationFile` to our custom log4j configuration file, via the following syntax.
```
-Dlog4j.configurationFile=path/to/log4j2.xml
```
For **Java Logging** we can set the system property `java.util.logging.config.file` to our custom Java logging configuration file, via the following syntax.
```
-Djava.util.logging.config.file=path/to/logging.properties
```
Here is a sample logging.properties.
```
.level = CONFIG
.handlers = java.util.logging.FileHandler, java.util.logging.ConsoleHandler

java.util.logging.ConsoleHandler.level = ALL
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

java.util.logging.FileHandler.level=ALL
java.util.logging.FileHandler.pattern=/output/logs/java%u.log
java.util.logging.FileHandler.formatter=java.util.logging.SimpleFormatter
```

Sometimes the logging might be insuffcient to detect what's going wrong. For example the application has few logging code near the suspicious code. At such an case, we can use debug, and especailly remote debug for remote application. Take Java as example, the Java Platform Debugging Architecture(JPDA) enables us to remote debug a Java application. We can add the following configurations to java to enable remote debugging, replace the 8000 port with the port you desired.
```
-Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y
```
If we are in the headless console, we can use `jdb` to debug the Java application.
```
jdb -attach localhost:8000
```
