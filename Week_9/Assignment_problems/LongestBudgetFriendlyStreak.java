public class BudgetStreak {
    public static int[] longestStreak(int[] costs, int budget) {
        int maxLen = 0;
        int startIdx = -1;
        long currentSum = 0;
        int left = 0;
        
        for (int right = 0; right < costs.length; right++) {
            currentSum += costs[right];
            
            while (currentSum > budget && left <= right) {
                currentSum -= costs[left];
                left++;
            }
            
            int windowLen = right - left + 1;
            if (windowLen > maxLen) {
                maxLen = windowLen;
                startIdx = left;
            }
        }
        
        return maxLen > 0 ? new int[]{maxLen, startIdx} : new int[]{0, -1};
    }
}