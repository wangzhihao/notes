[FileSystem](https://github.com/apache/hadoop/blob/trunk/hadoop-common-project/hadoop-common/src/main/java/org/apache/hadoop/fs/FileSystem.java) is a good abstraction that we can plug within our own implementation. For example, we can wrap a light weight instrumental proxy around the specific file system class we want to study. Two configurations are useful to plug into our implementations. We'd better to set `fs.file.impl.disable.cache` to `true` to disable cache, so that we can changes file system in runtime. Here are some samples:
```
fs.file.impl.disable.cache=true
fs.file.impl=com.amazon.fba.diagnose.extensions.DummyLocalFileSystem
```
