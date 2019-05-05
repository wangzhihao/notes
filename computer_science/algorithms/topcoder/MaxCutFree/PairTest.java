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

public class PairTest {
    public static void main(String [] args) throws Exception {
       PriorityQueue<Pair> queue = new PriorityQueue<Pair>();
       queue.add(new Pair(1,1));
       queue.add(new Pair(3,1));
       queue.add(new Pair(2,1));
       Pair head;
       while((head = queue.poll()) != null) {
            System.out.println(head); 
       }
    }
}
