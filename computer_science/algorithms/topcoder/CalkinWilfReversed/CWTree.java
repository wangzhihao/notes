import java.util.Scanner;

public class CWTree {
    public long getDepth(long a, long b) {
        if(a < b) return getDepth(b, a);
        if(b == 0) return -1;
        return a / b + getDepth(b, a % b);
    }
    public static void main(String [] args) throws Exception {
        CWTree tree = new CWTree();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            System.out.println(tree.getDepth(sc.nextLong(), sc.nextLong()));
        }
    }
}
