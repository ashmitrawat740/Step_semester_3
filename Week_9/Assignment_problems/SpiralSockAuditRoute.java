import java.util.ArrayList;
import java.util.List;

public class SpiralAudit {
    public static List<Integer> auditRoute(int[][] grid) {
        List<Integer> result = new ArrayList<>();
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return result;
        }
        
        int rows = grid.length;
        int cols = grid[0].length;
        int top = 0, bottom = rows - 1;
        int left = 0, right = cols - 1;
        
        while (top <= bottom && left <= right) {
            // 1. Top Row
            for (int col = left; col <= right; col++) {
                result.add(grid[top][col]);
            }
            top++;
            
            // 2. Right Column
            for (int row = top; row <= bottom; row++) {
                result.add(grid[row][right]);
            }
            right--;
            
            // 3. Bottom Row
            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    result.add(grid[bottom][col]);
                }
                bottom--;
            }
            
            // 4. Left Column
            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    result.add(grid[row][left]);
                }
                left++;
            }
        }
        
        return result;
    }
}