public class ATMPin {
    public static void checkPinLength(String pin) {
        if (pin.length() != 4) {
            System.out.println("Invalid PIN — must be exactly 4 digits.");
        } else {
            System.out.println("PIN length OK.");
        }
    }

    public static void main(String[] args) {
        // Test cases
        checkPinLength("482");   // Invalid PIN — must be exactly 4 digits.
        checkPinLength("4820");  // PIN length OK.
    }
}