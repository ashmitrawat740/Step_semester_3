public class TypingAccuracyChecker {

    public static void checkTypingAccuracy(String original, String typed) {
        int totalLength = original.length();
        int matchedCount = 0;
        int firstMismatchPos = -1;

        for (int i = 0; i < totalLength; i++) {
            if (original.charAt(i) == typed.charAt(i)) {
                matchedCount++;
            } else if (firstMismatchPos == -1) {
                firstMismatchPos = i + 1;
            }
        }

        double accuracy = ((double) matchedCount / totalLength) * 100.0;
        StringBuilder output = new StringBuilder();
        output.append(String.format("Matched: %d/%d | Accuracy: %.2f%% | ", matchedCount, totalLength, accuracy));

        if (firstMismatchPos != -1) {
            int idx = firstMismatchPos - 1;
            output.append(String.format("First Mismatch at position %d ('%c' vs '%c')", 
                firstMismatchPos, original.charAt(idx), typed.charAt(idx)));
        } else {
            output.append("No Mismatches");
        }

        System.out.println(output.toString());
    }

    public static void main(String[] args) {
        checkTypingAccuracy("hello world", "hello worlt");
        checkTypingAccuracy("coding", "coding");
    }
}