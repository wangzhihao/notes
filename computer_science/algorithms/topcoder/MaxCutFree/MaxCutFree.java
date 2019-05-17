import java.util.*;
import java.io.*;

public class MaxCutFree {

    private int[] scc(int n, int[] a, int[] b) {
    
    }

    
    public int solve(int n, int[] a, int[] b) {
       int [] components = scc(n, a, b);

       // build an adjacency list for graph with bridge edge only.
       boolean [] invalid = new boolean[n];
       int [] deg = new int[n];
       List<Integer>[] edges = new ArraryList[n];
       for(int i = 0; i < n; i++) { 
           invalid[i] = false;
           deg[i] = 0;
           edges[i] = new ArraryList<Integer>();
       }

       int m = a.length;
       for (int i = 0; i < m; i++) {
         if (components[a[i]] == components[b[i]]) {
            // not a bridge, ignore it.
            continue;
         }
         deg[a[i]] ++;
         deg[b[i]] ++;
         edges[a[i]].add(b[i]);
         edges[b[i]].add(a[i]);
       }
       // Greedy, always handle the node with lowest deg.
       List<Integer> next = new ArrayList<Integer>();
       for(int i = 0; i < n; i++) { 
           if(deg[i] <= 1) next.add(i);
       }
       int res = 0;
       while(next.size() > 0) {
           for(int i = 0; i < next.size(); i++) { 
               int node = next[i];
               if(invalid[node]) continue;
               res ++;
               invalid[node] = true;
               for(int i = 0; i < edges[node].size(); i++) {
                    int node2 = edges[node][i];
                    if(invalid[node2])continue;
                    invalid[node2] = true;
                    for (int j = 0; j < edges[node2].size(); j++) {
                        int node3= edges[node2][j];
                        if(invalid[node3]) continue;
                        deg[node3]--;
                    }
               }
           }
           next.clear();
           for(int i = 0; i < n; i++) { 
               if(deg[i] <= 1 && !invalid[i]) next.add(i);
           }
       }
    
       return res;
    }
    public static void main(String [] args) throws Exception {
        MaxCutFree clazz = new MaxCutFree();
        Scanner sc = new Scanner(System.in);
        while (true) {
            int n = sc.nextInt();
            int m = sc.nextInt();
            int [] a = new int[m];
            int [] b = new int[m];
            for ( int i = 0; i < m; i++) {
                a[i] = sc.nextInt();
                b[i] = sc.nextInt();
            }
            int res = clazz.generate(n, a, b);
            System.out.println(res);
        }
}
