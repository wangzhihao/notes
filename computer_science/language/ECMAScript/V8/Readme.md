src/compiler/csa-load-elimination.cc

## Zone
The Zone object maintains [a linked list of FreeBlock in memory](https://github.com/v8/v8/blob/master/src/zone/zone-allocator.h#L137) and try to reuse them if possible. In this way to achieve fast allocate and deallocate. [In the list, the previous element's size is always larger than the later element's](https://github.com/v8/v8/blob/master/src/zone/zone-allocator.h#L103-L129).

src/zone/zone-splay-tree.h

