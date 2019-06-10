## Some optimizations
```
					Nodes	Main Zone (KB)	Temp Zone (KB)	All Zones (KB)
loop assignment analysis		0	0.8046875	0		1112.140625
graph builder				2518	585.1015625	125.2578125	159.359375
inlining				1631	392.203125	42.875		0
early graph trimming			0	0		33.40625	0
typer					10	22.3828125	46		0
typed lowering				208	59.5859375	16.5859375	0
concurrent optimization preparation	3	0.28125		0		0
loop peeling				276	72.2421875	240.34375	1187.507813
load elimination			3	0.2578125	503.28125	221.3828125
escape analysis				1	0.1171875	187.28125	0
simplified lowering			161	56.4140625	293.515625	16.0703125
generic lowering			233	102.3671875	24.7578125	0
early optimization			2	0.21875		175.0859375	0
effect linearization			3631	778.8359375	720.328125	520.203125
dead code elimination			2	0.125		41.2734375	0
store-store elimination			0	0		193.921875	0
control flow optimization		0	0		28.3046875	0
memory optimization			161	140.28125	113.34375	162.2421875
late optimization			21	1.96875		244.859375	59.7578125
late graph trimming			0	0		73.21875	0
scheduling				604	621.3046875	1534.375	1639.53125
select instructions			0	0		242.6328125	295.7578125
```

## load elimination
[CR: Add very basic CsaLoadElimination phase](https://chromium-review.googlesource.com/c/v8/v8/+/1635449)

## Zone
For BUG=[chromium:700364](https://bugs.chromium.org/p/chromium/issues/detail?id=700364)

The Zone object maintains [a linked list of FreeBlock in memory](https://github.com/v8/v8/blob/master/src/zone/zone-allocator.h#L137) and try to reuse them if possible. In this way to achieve fast allocate and deallocate. [In the list, the previous element's size is always larger than the later element's](https://github.com/v8/v8/blob/master/src/zone/zone-allocator.h#L103-L129). It's [to fix the issue that std:deque didn't work well with Zone object](https://github.com/v8/v8/commit/b90a20b2c70540188b1b1b44ea70c51af1badaa1). [The test code](https://github.com/v8/v8/commit/b90a20b2c70540188b1b1b44ea70c51af1badaa1#diff-59f126a77d4e4af8e59e18d8c7bb16d7) explains the behavior well.

src/zone/zone-splay-tree.h

