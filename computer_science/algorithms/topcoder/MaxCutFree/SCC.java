import java.util.*;
import java.io.*;

/**
 * Find strong connected components
 */
public class SCC {
    private List<Integer>[] edges;
    private List<Integer> order;
    private int [] orderReverse;
    private boolean [] visit;
    private Set<Long> edgesVisit;
    private int [] components;
    private long HASH = 10000;

    private long hash(int a, int b) {
        return a * HASH + b;
    }

    private void visit(int node) {
        order.add(node); 
        orderReverse[node] = order.size() - 1;
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

    private void bfs(int node) {
        Queue<Integer> S = new LinkedList<Integer>(); 
        for(int i = 0; i < edges[node].size(); i++) {
            int next = edges[node].get(i);
            if(edgesVisit.contains(hash(node, next)) || components[next] != -1) continue;
            S.add(next);
        }
        while(!S.isEmpty()) {
            int next = S.poll();
            components[next] = components[node];
            for(int i = 0; i < edges[next].size(); i++) {
                int nnext = edges[next].get(i);
                if(!edgesVisit.contains(hash(nnext, next)) || components[nnext] != -1 || orderReverse[nnext] > orderReverse[next]) continue;
                S.add(nnext);
            }
        }
    }

    private int[] scc(int n, int[] a, int[] b) {
        // Initialize the edges
        edges = new ArrayList[n];
        edgesVisit = new HashSet<Long>();
        order = new ArrayList<Integer>();
        orderReverse = new int[n];
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

        //bfs to built a pre-order traversal
        for(int i = 0; i < n; i++) {
            if(!visit[i]) visit(i);
        }
        print("orderReverse", orderReverse);

        for(int i = 0; i < order.size(); i++) {
            int node = order.get(i);
            if(components[node] != -1)continue;
            components[node] = node;
            bfs(node);
        }
        return components;
    }

    private static void print(String name, int [] elements) {
            System.out.println(name);
            for(Object element: elements) {
                System.out.print(element);
                System.out.print(" ,");
            }
            System.out.println();
    
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
            print("components", res);
        }
    }
}
