import java.util.HashMap;
import java.util.Map;

public class NetBalance {
    public static int countPeriods(int[] transactions, int k) {
        int count = 0;
        long currentSum = 0;
        Map<Long, Integer> prefixCounts = new HashMap<>();
        prefixCounts.put(0L, 1); // Base case for subarrays starting at index 0
        
        for (int x : transactions) {
            currentSum += x;
            long target = currentSum - k;
            if (prefixCounts.containsKey(target)) {
                count += prefixCounts.get(target);
            }
            prefixCounts.put(currentSum, prefixCounts.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
}