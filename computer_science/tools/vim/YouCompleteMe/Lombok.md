As a workaround, currently we can enable lombok like this.

```
JAVA_TOOL_OPTIONS='-javaagent:/home/zhihaow/local/eclipse/lombok.jar -Xbootclasspath/p:/home/zhihaow/local/eclipse/lombok.jar' vim .
```

However such an workaround is buggy, it breaks the format feature.

```
ResponseFailedException: Request failed: -32603: Internal error.
```

Another post talked about this issue: https://github.com/Valloric/YouCompleteMe/issues/3288

The issue still persists even if I modified [the code]( https://github.com/Valloric/ycmd/blob/master/ycmd/completers/java/java_completer.py#L359) directly.
