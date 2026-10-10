public class WarehouseSummary {
    public static class SummaryResult {
        long totalItems;
        int[] maxCoordinate;
        
        public SummaryResult(long totalItems, int[] maxCoordinate) {
            this.totalItems = totalItems;
            this.maxCoordinate = maxCoordinate;
        }
    }
    
    public static SummaryResult warehouseSummary(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return new SummaryResult(0, new int[] { -1, -1 });
        }
        
        long totalItems = 0;
        int maxItems = -1;
        int[] maxCoordinate = new int[] { -1, -1 };
        
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[0].length; c++) {
                int val = grid[r][c];
                totalItems += val;
                if (val > maxItems) {
                    maxItems = val;
                    maxCoordinate = new int[] { r, c };
                }
            }
        }
        
        return new SummaryResult(totalItems, maxCoordinate);
    }
}