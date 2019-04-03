import java.util.*;
import java.io.*;

public class MostFrequentLastDigit {
	public int[] generate(int n, int d) {
        int [] res = new int[n];
        int sum = d == 9 ? d : 10 + d, left_d = sum/2, right_d = sum - left_d;
        for(int i = 0; i < n/2; i++) {
            res[i] = i * 10 + left_d; 
        }
        for(int i = n/2; i < n; i++) {
            res[i] = i * 10 + right_d; 
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
