import java.util.*;
import java.io.*;

public class ReconstructNumber {
    private static final String NO_ANSWER = "";
    private static final String NO_INITIAL = "blank";

    // -1 means not visited, 0 means not possible, 1 means possible 
    private String [][] visit;

    private void init(int size) {
        visit = new String[10][size];
        for(int i = 0; i < 10; i++) Arrays.fill(visit[i], NO_INITIAL);
    }

    private boolean isValid(int a, int b, char operator) {
        switch(operator) {
            case '<':
                return a < b;
            case '>':
                return a > b;
            case '=':
                return a == b;
            default:
                return a != b;
        }
    }

    private String test(int number, int idx, String comparisons) {
        if(idx >= comparisons.length()) return String.valueOf(number);
        if(visit[number][idx] != NO_INITIAL) return visit[number][idx];

        for(int i = 0; i < 10; i++) {
            if(isValid(number, i, comparisons.charAt(idx)) && test(i, idx+1, comparisons) != NO_ANSWER) {
                visit[number][idx] = number + test(i, idx+1, comparisons);
                return visit[number][idx];
            }
        }
        visit[number][idx] = NO_ANSWER;
        return visit[number][idx];
    }

    public String smallest(String comparisons) {
        init(comparisons.length());
        for(int i = 1; i < 10; i++) {
            if(test(i, 0, comparisons) != NO_ANSWER) {
                return test(i, 0, comparisons);
            }
        }
        return NO_ANSWER;
    }

    public static void main(String [] args) throws Exception {
        ReconstructNumber clazz = new ReconstructNumber();
        Scanner sc = new Scanner(System.in);
        while (sc.hasNext()) {
            System.out.println(clazz.smallest(sc.next()));
        }
    }
}
