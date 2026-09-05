public class PalindromeChecker {

    // Approach 1: Two-pointer iterative scan
    public static boolean isPalindromeIterative(String text) {
        int left = 0, right = text.length() - 1;
        while (left < right) {
            if (text.charAt(left) != text.charAt(right)) return false;
            left++;
            right--;
        }
        return true;
    }

    // Approach 2: Recursive shrinking
    public static boolean isPalindromeRecursive(String text) {
        if (text.length() <= 1) return true;
        if (text.charAt(0) != text.charAt(text.length() - 1)) return false;
        return isPalindromeRecursive(text.substring(1, text.length() - 1));
    }

    // Approach 3: Character array reversal
    public static boolean isPalindromeArrayReversal(String text) {
        char[] original = text.toCharArray();
        char[] reversed = new char[original.length];
        for (int i = 0; i < original.length; i++) {
            reversed[i] = original[original.length - 1 - i];
        }
        return new String(original).equals(new String(reversed));
    }

    private static void verifyString(String text) {
        String r1 = isPalindromeIterative(text) ? "Palindrome" : "Not Palindrome";
        String r2 = isPalindromeRecursive(text) ? "Palindrome" : "Not Palindrome";
        String r3 = isPalindromeArrayReversal(text) ? "Palindrome" : "Not Palindrome";

        System.out.printf("Input: \"%s\"%n", text);
        System.out.printf("Iterative: %s | Recursive: %s | Array Reversal: %s%n%n", r1, r2, r3);
    }

    public static void main(String[] args) {
        verifyString("madam");
        verifyString("hello");
    }
}