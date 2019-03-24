As a workaround, currently we can enable lombok like this.

```
JAVA_TOOL_OPTIONS='-javaagent:/home/zhihaow/local/eclipse/lombok.jar -Xbootclasspath/p:/home/zhihaow/local/eclipse/lombok.jar' vim .
```

However such an workaround is buggy, it breaks the format feature.

```
ResponseFailedException: Request failed: -32603: Internal error.
```
