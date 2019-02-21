import java.util.Arrays;

public class IdenticalBags {
    public long makeBags(long[] candy, long bagSize) {
       Arrays.sort(candy);
       long left = 1, right = candy[candy.length - 1], ans = 0;
       while(left <= right) {
            long middle = (left + right) / 2; 
            if(test(candy, bagSize, middle)) {
                left = middle + 1;
                ans = middle;
            } else { 
                right = middle - 1;
            }
       }
       return ans;
    }
    private boolean test(long[] candy, long bagSize, long bagNum) {
       for(int i = candy.length - 1; i >= 0; i--) {
            bagSize -= candy[i] / bagNum;
            if(bagSize <= 0) return true;
       }
       return false;
    }
}
