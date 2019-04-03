import java.util.*;
import java.io.*;

public class MostFrequentLastDigit {
	public int[] generate(int n, int d) {
        int [] res = new int[n];
        if(d == 0) {
            for(int i = 0; i < n; i++) {
                res[i] = i*10 + 5;
            }
        } else {
            for(int i = 0; i < n/2; i++) {
                res[i] = i * 10 + d; 
            }
            for(int i = n/2; i < n; i++) {
                res[i] = i * 10; 
            }
        }
        return res;
    }
    public static void main(String [] args) throws Exception {
        MostFrequentLastDigit clazz = new MostFrequentLastDigit();
        Scanner sc = new Scanner(System.in);
        while (true) {
            int [] res = clazz.generate(sc.nextInt(), sc.nextInt());
            System.out.println(Arrays.toString(res));
        }
    }
}
