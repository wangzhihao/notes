import java.lang.Math;
import java.util.function.Function;
import java.util.Scanner;


/**
 * Search based.
 * V = 2(abc + abd + acd + bcd)
 */
public class Hyperbox {
    private int findMax(int left, int right, Function<Long, Long> Polynomial) {
        int middle, ans = left;   
        while(left <= right) {
            middle = (left + right) / 2;
            if(Polynomial.apply((long)middle) <= 0) {
                ans = middle;
                left = middle + 1;
            } else {
                right = middle - 1;
            }
        }
        return ans;
    }

    public int count(final int volume) {
        if(volume % 2 != 0) return 0;
        int count = 0;
        int max_a = (int)(Math.cbrt(volume / 8.0) + 1),
            upper_bound_b = (int)(Math.cbrt(volume) + 1),
            upper_bound_c = (int)(Math.sqrt(volume) + 1);

        for(int a = 1; a <= max_a; a++) {
            // b^3 + 3a*b^2 -V/2 <= 0
            final int A = a;
            int max_b = findMax(max_a, upper_bound_b, x -> x*x*x + 3*A*x*x - volume/2);
            for(int b = a; b <= max_b; b++) {
                // (a+b)*c^2 + 2*a*b*c -V/2 <= 0
                final int B = b;
                int max_c = findMax(max_b, upper_bound_c, x -> (A+B)*x*x + 2*A*B*x - volume/2);
                for(int c = b; c <= max_c; c++) {
                    int numerator = (volume / 2 - a*b*c);
                    if(numerator < c) break;
                    int denominator = a*b + a*c + b*c;
                    if(numerator % denominator == 0) {
                    int d = numerator / denominator;
                    if(d < c) break;
                    if(d >= c) {
                        System.out.format("One result found: a = %d, b = %d, c = %d, d = %d%n", a, b, c, d);
                        count ++;
                    }
                    }
                } 
            } 
        }
        return count;
    }
    public static void main(String [] args) throws Exception {
        Hyperbox box = new Hyperbox();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()) {
            System.out.println(box.count(sc.nextInt()));
        }
    }
}
