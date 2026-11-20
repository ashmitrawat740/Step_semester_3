import java.util.*;

public class PlacementShortlist {
    static class Candidate {
        final String name;
        final int[] scores;
        final int total;
        final int bestClimb;
        final int startTest; // 1-based
        final int endTest;   // 1-based

        Candidate(String name, int[] scores) {
            this.name = name;
            this.scores = scores;
            int sum = 0;
            for (int s : scores) sum += s;
            this.total = sum;

            // Kadane's algorithm on weekly differences to find max consecutive gain
            int maxGain = Integer.MIN_VALUE;
            int currentGain = 0;
            int tempStart = 1;
            int bestStart = 1;
            int bestEnd = 2;

            for (int i = 0; i < scores.length - 1; i++) {
                int diff = scores[i + 1] - scores[i];
                if (currentGain < 0) {
                    currentGain = diff;
                    tempStart = i + 1;
                } else {
                    currentGain += diff;
                }

                // Strict '>' preserves earliest run on ties
                if (currentGain > maxGain) {
                    maxGain = currentGain;
                    bestStart = tempStart;
                    bestEnd = i + 2;
                }
            }

            this.bestClimb = maxGain;
            this.startTest = bestStart;
            this.endTest = bestEnd;
        }
    }

    // Stable Merge Sort: descending by total; ties preserve original order
    public static void mergeSort(Candidate[] arr, int left, int right) {
        if (left >= right) return;
        int mid = left + (right - left) / 2;
        mergeSort(arr, left, mid);
        mergeSort(arr, mid + 1, right);
        merge(arr, left, mid, right);
    }

    private static void merge(Candidate[] arr, int left, int mid, int right) {
        Candidate[] temp = new Candidate[right - left + 1];
        int i = left, j = mid + 1, k = 0;

        while (i <= mid && j <= right) {
            // '>=' ensures left element comes first on ties (stability)
            if (arr[i].total >= arr[j].total) {
                temp[k++] = arr[i++];
            } else {
                temp[k++] = arr[j++];
            }
        }
        while (i <= mid) temp[k++] = arr[i++];
        while (j <= right) temp[k++] = arr[j++];

        System.arraycopy(temp, 0, arr, left, temp.length);
    }

    // Binary search on descending array: count candidates with total >= threshold
    public static int countAtLeast(Candidate[] sorted, int threshold) {
        int low = 0, high = sorted.length - 1;
        int ans = sorted.length; // first index where total < threshold

        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (sorted[mid].total >= threshold) {
                low = mid + 1;
            } else {
                ans = mid;
                high = mid - 1;
            }
        }
        return ans;
    }

    // Two pointers on descending array to find pair summing to target
    public static String findPair(Candidate[] sorted, int target) {
        int left = 0, right = sorted.length - 1;
        while (left < right) {
            int sum = sorted[left].total + sorted[right].total;
            if (sum == target) {
                return sorted[left].name + " + " + sorted[right].name;
            } else if (sum > target) {
                left++;  // need a smaller sum
            } else {
                right--; // need a larger sum
            }
        }
        return "no pair";
    }

    public static void main(String[] args) {
        Candidate[] candidates = {
            new Candidate("Asha",  new int[]{60, 55, 70, 72, 68, 80}),
            new Candidate("Ravi",  new int[]{70, 72, 65, 60, 75, 78}),
            new Candidate("Neha",  new int[]{50, 65, 62, 70, 74, 84}),
            new Candidate("Kiran", new int[]{80, 70, 66, 72, 70, 60}),
            new Candidate("Mohan", new int[]{55, 60, 58, 64, 70, 73})
        };

        mergeSort(candidates, 0, candidates.length - 1);

        System.out.println("Shortlist:\n");
        for (int i = 0; i < candidates.length; i++) {
            Candidate c = candidates[i];
            String sign = c.bestClimb >= 0 ? "+" : "";
            System.out.printf("%d. %s %d | best climb %s%d (test %d to %d)%n",
                i + 1, c.name, c.total, sign, c.bestClimb, c.startTest, c.endTest);
        }

        int[] cutoffs = {405, 410};
        for (int x : cutoffs) {
            System.out.printf("CUTOFF %d: %d candidates%n", x, countAtLeast(candidates, x));
        }

        int[] targets = {785, 900};
        for (int t : targets) {
            System.out.printf("PAIR %d: %s%n", t, findPair(candidates, t));
        }
    }
}