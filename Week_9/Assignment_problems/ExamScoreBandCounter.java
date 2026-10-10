public class ExamScoreBand {
    public static int countInBand(int[] scores, int low, int high) {
        int leftIdx = lowerBound(scores, low);
        int rightIdx = upperBound(scores, high);
        return rightIdx - leftIdx;
    }
    
    // First position where score >= low
    private static int lowerBound(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
    
    // First position where score > high
    private static int upperBound(int[] arr, int target) {
        int low = 0, high = arr.length;
        while (low < high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > target) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }
        return low;
    }
}