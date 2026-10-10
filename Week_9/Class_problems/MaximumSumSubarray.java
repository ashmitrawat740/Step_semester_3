public class MaxSumSubarray {
    public static int maxSumSubarray(int[] sales, int k) {
        if (sales == null || k <= 0 || k > sales.length) {
            return 0;
        }
        
        int currentSum = 0;
        for (int i = 0; i < k; i++) {
            currentSum += sales[i];
        }
        int maxSum = currentSum;
        
        for (int i = k; i < sales.length; i++) {
            currentSum += sales[i] - sales[i - k];
            if (currentSum > maxSum) {
                maxSum = currentSum;
            }
        }
        
        return maxSum;
    }
}