import java.util.*;
import java.io.*;

/**
 * Find strong connected components
 */
public class SCC {
    private List<Integer>[] edges;
    private List<Int> order;
    private boolean [] visit;
    private Set<Long> edgesVisit;
    private int [] components;
    private long HASH = 10000;

    private long hash(int a, int b) {
        return a * HASH + b;
    }

    private void visit(int node) {
        order.add(node); 
        visit[node] = true;
        for(int i = 0; i < edges[node].size(); i++) {
            int next = edges[node].get(i);
            if(!visit[next]) {
                edgesVisit.add(hash(next, node));
                edgesVisit.add(hash(node, next));
                visit(next);
            }
        }
    }
    private void dfs(int node) {
        for(int i = 0; i < edges[node].size(); i++) {
            int next = edges[node].get(i);
            if(edgesVisit.contains(hash(node, next)) || components[next] != -1) continue;
            components[next] = components[node];
            dfs(next);
        }
    }

    private int[] scc(int n, int[] a, int[] b) {
        // Initialize the edges
        edges = new ArrayList[n];
        edgesVisit = new HashSet<Long>();
        order = new ArrayList<Integer>();
        components = new int[n];
        visit = new boolean[n];
        for(int i = 0; i < n; i++) {
            edges[i] = new ArrayList<Integer>();
            visit[i] = false;
            components[i] = -1;
        }
        for(int i = 0; i < a.length; i++) {
            edges[a[i]].add(b[i]);
            edges[b[i]].add(a[i]);
        }

        //dfs to built a pre-order traversal
        visit(0);

        for(int i = 0; i < order.size(); i++) {
            int node = order.get(i);
            if(components[i] != -1)continue;
            components[i] = i;
            dfs(i);
        }
        return components;
    }

    public static void main(String[] args) throws Exception {
        SCC clazz = new SCC();
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int[] a = new int[m];
            int[] b = new int[m];
            for (int i = 0; i < m; i++) {
                a[i] = sc.nextInt();
                b[i] = sc.nextInt();
            }
            int[] res = clazz.scc(n, a, b);
            for(int i = 0; i < res.length; i++) {
                System.out.println("node " + i + "'s root is " + res[i]);
            }
        }
    }
}
