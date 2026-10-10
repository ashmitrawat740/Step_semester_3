public class MallFootfall {
    public static int[] footfallReport(int[] visitors, int[][] queries) {
        int n = visitors.length;
        int[] pref = new int[n];
        pref[0] = visitors[0];
        for (int i = 1; i < n; i++) {
            pref[i] = pref[i - 1] + visitors[i];
        }
        
        int[] result = new int[queries.length];
        for (int q = 0; q < queries.length; q++) {
            int start = queries[q][0];
            int end = queries[q][1];
            if (start == 0) {
                result[q] = pref[end];
            } else {
                result[q] = pref[end] - pref[start - 1];
            }
        }
        return result;
    }
}