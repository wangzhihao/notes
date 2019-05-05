import java.util.*;
import java.io.*;


class Pair implements Comparable<Pair>{
    private int first;
    private int second;

    public Pair(int a, int b){
        this.first = a;
        this.second = b;
    }

    @Override
    public int compareTo(Pair p) {
        return this.first - p.first;
    }

    @Override
    public String toString() {
        return first + "," + second;
    }
}

public class MaxCutFree {

    private int[] scc(int n, int[] a, int[] b) {
    
    }

    
    public int solve(int n, int[] a, int[] b) {
       int [] components = scc(n, a, b);

       boolean [] invalid = new boolean[n];
       int [] deg = new int[n];
       List<Integer>[] edges = new ArraryList<Integer>[n];
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
       PriorityQueue<Pair> queue = new PriorityQueue<Pair>();
       for(int i = 0; i < n; i++) { 
           queue.add(new Pair(deg[i],i));
       }
       Pair head;
       int res = 0;
       while((head = queue.poll()) != null) {
           int node = head.second;
           if(invalid[node]) continue;
           res ++;
           invalid[node] = true;
           for(int i = 0; i < edges[node].size(); i++) {
                int next = edges[node][i];
                if(invalid[next])continue;
                invalid[next] = true;
                for (int j = 0; j < edges[next].size(); j++) {
                    int nnext = edges[next][j];
                    if(invalid[nnext]) continue;
                    deg[nnext]--;
                    queue.add(new Pair(deg[nnext], nnext));
                }
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
