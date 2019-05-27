https://en.wikipedia.org/wiki/Adjacency_list

## Implementation

Option 1: via an array of List

```java
// java
List<integer> edges[];
```
```c++
// c++
vector<int> edges[];
```

Option 2: implement the linked list with array instead of pointers. This option is good for c porgram [in which built-in list data structure is not available](https://stackoverflow.com/a/14001669).
```c
// c
int count = nodes_count;
int [] next, val;

add_edge(int u, int v) {
    // assign a new buffer, implement list by our own.
	count = count + 1;
	next[count] = next[u];
	next[u] = count;
	val[count] = v;
}
```
