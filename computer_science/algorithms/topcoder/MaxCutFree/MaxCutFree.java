import java.util.*;
import java.io.*;
/**
 * Greeedy algorithm. Each time pick the valid node with lowest degree.
 * Time complexity: max(O(n^2), O(n*m)) m is the edge number. 
 */
public class MaxCutFree {

    private int[] deg;
    private boolean[] invalid;

    private void deg(int n, int[] a, int[] b) {
        for (int i = 0; i < n; i++) {
            deg[i] = 0;
        }
        for (int i = 0; i < a.length; i++) {
            if (invalid[a[i]] || invalid[b[i]]) {
                continue;
            }
            deg[a[i]]++;
            deg[b[i]]++;
        }
    }

    private void visit(int node, int[] a, int[] b) {
        invalid[node] = true;
        for(int i = 0; i < a.length; i++) {
            if(a[i] == node || b[i] == node) {
                invalid[a[i]] = true;
                invalid[b[i]] = true;
            }
        }
    }

    public int solve(int n, int[] a, int[] b) {
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
                if(!invalid[i] && (lowestDeg == -1 || lowestDeg >= deg[i])) {
                    lowestDeg = deg[i];
                    lowestDegNode = i;
                }
            }
            if(lowestDeg == -1) break;
            visit(lowestDegNode, a, b);
            res ++; 
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
}
