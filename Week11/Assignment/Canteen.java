import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class CanteenPriceStreak {
    public static int[] priceStreak(int[] price) {
        if (price == null || price.length == 0) {
            return new int[0];
        }

        int n = price.length;
        int[] streak = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            while (!stack.isEmpty() && price[stack.peek()] <= price[i]) {
                stack.pop();
            }

            if (stack.isEmpty()) {
                streak[i] = i + 1;
            } else {
                streak[i] = i - stack.peek();
            }

            stack.push(i);
        }

        return streak;
    }

    public static void main(String[] args) {
        int[] price1 = {100, 80, 60, 70, 60, 75, 85};
        System.out.println(Arrays.toString(priceStreak(price1)));

        int[] price2 = {10, 20, 30};
        System.out.println(Arrays.toString(priceStreak(price2)));
    }
}