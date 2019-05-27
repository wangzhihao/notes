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

Option 2: implement the linked list by your own.
```c
int count = vertex_count;
int [] next, val;

add_edge(int u, int v) {
	count = count + 1;
	next[count] = next[u];
	next[u] = count;
	val[count] = v;
}
```
