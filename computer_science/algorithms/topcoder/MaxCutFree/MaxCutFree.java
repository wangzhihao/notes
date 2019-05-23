import java.util.*;
import java.io.*;

/**
 * Biconnected Components.
 * Fristly remove all bridges. Then it is a maximum independent vertex set
 * problem. See
 * http://mathworld.wolfram.com/MaximumIndependentVertexSet.htmlapply a greeedy
 * algorithm. Problem:
 * https://community.topcoder.com/stat?c=problem_statement&pm=15257&rd=17422
 *
 * Greedy algorithm should also work for trees: https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pdf/IntractabilityIII-2x2.pdf
 * https://en.wikipedia.org/wiki/Bridge_(graph_theory)
 * https://en.wikipedia.org/wiki/Biconnected_component
 * http://akira.ruc.dk/~keld/teaching/algoritmedesign_f03/Artikler/06/Hopcroft73.pdf
 *
 */
public class MaxCutFree {

    private int[] deg;
    private int[] components;
    private boolean[] invalid;

    private boolean isBridge(int i, int j) {
        return components[i] == components[j];
    }

    private void deg(int n, int[] a, int[] b) {
        for (int i = 0; i < n; i++) {
            deg[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            if (isBridge(a[i], b[i]))
                continue;
            if (invalid[a[i]] || invalid[b[i]]) {
                continue;
            }
            deg[a[i]]++;
            deg[b[i]]++;
        }
    }

    private void visit(int node, int[] a, int[] b) {
        invalid[node] = true;
        for (int i = 0; i < a.length; i++) {
            if (isBridge(a[i], b[i]))
                continue;
            if (a[i] == node || b[i] == node) {
                invalid[a[i]] = true;
                invalid[b[i]] = true;
            }
        }
    }

    public int solve(int n, int[] a, int[] b) {
        components = new SCC().scc(n, a, b);
        invalid = new boolean[n];
        deg = new int[n];
        for (int i = 0; i < n; i++) {
            invalid[i] = false;
        }

        int res = 0;
        while (true) {
            int lowestDegNode = 0;
            int lowestDeg = -1;
            deg(n, a, b);
            for (int i = 0; i < n; i++) {
                if (!invalid[i] && (lowestDeg == -1 || lowestDeg >= deg[i])) {
                    lowestDeg = deg[i];
                    lowestDegNode = i;
                }
            }
            if (lowestDeg == -1)
                break;
            visit(lowestDegNode, a, b);
            res++;
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        MaxCutFree clazz = new MaxCutFree();
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
            int res = clazz.solve(n, a, b);
            System.out.println(res);
        }
    }

    /**
     * Find strong connected components
     */
    public class SCC {
        private List<Integer>[] edges;
        private List<Integer> order;
        private int[] orderReverse;
        private boolean[] visit;
        private Set<Long> edgesVisit;
        private int[] components;
        private long HASH = 10000;

        private long hash(int a, int b) {
            return a * HASH + b;
        }

        private void visit(int node) {
            order.add(node);
            orderReverse[node] = order.size() - 1;
            visit[node] = true;
            for (int i = 0; i < edges[node].size(); i++) {
                int next = edges[node].get(i);
                if (!visit[next]) {
                    edgesVisit.add(hash(next, node));
                    edgesVisit.add(hash(node, next));
                    visit(next);
                }
            }
        }

        private void bfs(int node) {
            Queue<Integer> S = new LinkedList<Integer>();
            for (int i = 0; i < edges[node].size(); i++) {
                int next = edges[node].get(i);
                if (edgesVisit.contains(hash(node, next)) || components[next] != -1)
                    continue;
                S.add(next);
            }
            while (!S.isEmpty()) {
                int next = S.poll();
                components[next] = components[node];
                for (int i = 0; i < edges[next].size(); i++) {
                    int nnext = edges[next].get(i);
                    if (!edgesVisit.contains(hash(nnext, next)) || components[nnext] != -1
                            || orderReverse[nnext] > orderReverse[next])
                        continue;
                    S.add(nnext);
                }
            }
        }

        public int[] scc(int n, int[] a, int[] b) {
            // Initialize the edges
            edges = new ArrayList[n];
            edgesVisit = new HashSet<Long>();
            order = new ArrayList<Integer>();
            orderReverse = new int[n];
            components = new int[n];
            visit = new boolean[n];
            for (int i = 0; i < n; i++) {
                edges[i] = new ArrayList<Integer>();
                visit[i] = false;
                components[i] = -1;
            }
            for (int i = 0; i < a.length; i++) {
                edges[a[i]].add(b[i]);
                edges[b[i]].add(a[i]);
            }

            // bfs to built a pre-order traversal
            for (int i = 0; i < n; i++) {
                if (!visit[i])
                    visit(i);
            }

            for (int i = 0; i < order.size(); i++) {
                int node = order.get(i);
                if (components[node] == -1)
                    components[node] = node;
                /*
                 We need bsf all nodes, here is a counter example if we only bsf those
                 components[i] == -1 nodes, in which case the answer would be wrong, 3
                 instead of 5.
                    5 6
                    0 1
                    1 2
                    2 0
                    0 3
                    3 4
                    4 0
                 */
                bfs(node);
            }
            return components;
        }
    }
}
