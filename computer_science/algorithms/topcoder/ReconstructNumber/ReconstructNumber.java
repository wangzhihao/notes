import java.util.*;
import java.io.*;

public class ReconstructNumber {
    private static final int NO_NEXT = -1;
    private static final int NO_INITIAL = -2;
    private static final String NO_ANSWER = "";

    private int [][] next;

    private void init(int size) {
        next = new int[10][size];
        for(int i = 0; i < 10; i++) Arrays.fill(next[i], NO_INITIAL);
    }

    private boolean isValid(int a, int b, int operator) {
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

    private int haveNext(int number, int idx, String comparisons) {
        if(idx == comparisons.length()) return number;
        if(next[number][idx] != NO_INITIAL) return next[number][idx];

        for(int i = 0; i < 10; i++) {
            if(isValid(number, i, comparisons.charAt(idx)) && haveNext(i, idx+1, comparisons) != NO_NEXT) {
                next[number][idx] = i;
                return next[number][idx];
            }
        }
        next[number][idx] = NO_NEXT;
        return next[number][idx];
    }
    private String report(int number, int idx, int length) {
        return number + (idx == length ? "" : report(next[number][idx], idx+1, length));
    }
    public String smallest(String comparisons) {
        init(comparisons.length());
        for(int i = 1; i < 10; i++) {
            if(haveNext(i, 0, comparisons) != NO_NEXT) {
                return report(i, 0, comparisons.length());
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
