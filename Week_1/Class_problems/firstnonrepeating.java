import java.util.HashMap;
import java.util.Map;

public class UniqueLetterHunt {

    public static char findFirstNonRepeatingChar(String text) {
        Map<Character, Integer> counts = new HashMap<>();

        // Frequency accumulation
        for (char c : text.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        // Left-to-right scan for the first unique character
        for (char c : text.toCharArray()) {
            if (counts.get(c) == 1) {
                return c;
            }
        }
        return '\0'; // Sentinel value for non-found state
    }

    public static void testInput(String text) {
        char result = findFirstNonRepeatingChar(text);
        if (result != '\0') {
            System.out.printf("Input: \"%s\" -> First Non-Repeating Character: '%c'%n", text, result);
        } else {
            System.out.printf("Input: \"%s\" -> No Non-Repeating Character Found%n", text);
        }
    }

    public static void main(String[] args) {
        testInput("swiss");
        testInput("aabbcc");
    }
}