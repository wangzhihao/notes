# Samples
```
zhihaow@dev-dsk-zhihaow-ec2-2c-72819b04] ag memento -i -r .                                                                                             /workspace/opensource
hadoop/hadoop-mapreduce-project/hadoop-mapreduce-client/hadoop-mapreduce-client-core/src/main/java/org/apache/hadoop/mapreduce/checkpoint/CheckpointID.java
23: * This class represent the identified (memento) for a checkpoint. It is allowed

jersey/core-common/src/main/java/org/glassfish/jersey/internal/guava/MapMakerInternalMap.java
70:   * This implementation uses a per-segment queue to record a memento of the additions, removals,
101:     * ordering information is updated. This is used to avoid lock contention by recording a memento

jersey/core-common/src/main/java/org/glassfish/jersey/internal/guava/LocalCache.java
78:   * This implementation uses a per-segment queue to record a memento of the additions, removals,
109:     * ordering information is updated. This is used to avoid lock contention by recording a memento

gcc/gcc/jit/jit-recording.h
69:  void record (memento *m);
325:  auto_vec<memento *> m_mementos;
343:class memento
346:  virtual ~memento () {}
360:  memento *
374:  memento (context *ctxt)
398:class string : public memento
420:class location : public memento
425:  : memento (ctxt),
446:       appears in the context's memento list *after* the statement that
475:class type : public memento
537:    : memento (ctxt),
546:class memento_of_get_type : public type
549:  memento_of_get_type (context *ctxt,
588:class memento_of_get_pointer : public type
591:  memento_of_get_pointer (type *other_type)
638:class memento_of_get_const : public decorated_type
641:  memento_of_get_const (type *other_type)
661:class memento_of_get_volatile : public decorated_type
664:  memento_of_get_volatile (type *other_type)
678:class memento_of_get_aligned : public decorated_type
681:  memento_of_get_aligned (type *other_type, size_t alignment_in_bytes)
786:                         memento *ptr_type);
```
