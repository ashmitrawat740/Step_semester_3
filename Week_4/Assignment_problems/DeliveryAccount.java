class PremiumAccount extends DeliveryAccount {
    public PremiumAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }

    public PremiumAccount(String studentId) {
        super(studentId);
    }
}

public class DeliveryAccount {
    private static double standardRate;

    static {
        standardRate = 0.01;
    }

    private final String studentId;
    private final double orderValue;

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0.0);
    }

    public final double calculateSurgeFee(int delayMinutes) {
        if (delayMinutes <= 0) return 0.0;
        return orderValue * standardRate * delayMinutes;
    }

    public static void processAccount(DeliveryAccount account, double amount, int delayMinutes) {
    }

    public static void processBatch(DeliveryAccount[] accounts, double[] amounts, int[] delayMinutesArray) {
        if (accounts == null || amounts == null || delayMinutesArray == null) {
            System.out.println("Invalid batch input.");
            return;
        }

        int length = Math.min(accounts.length, Math.min(amounts.length, delayMinutesArray.length));
        int processed = 0;
        int nullSkipped = 0;
        int premium = 0;
        int regular = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < length; i++) {
            DeliveryAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            int delay = delayMinutesArray[i];

            if (acc instanceof PremiumAccount) {
                premium++;
                grandTotal += (acc.calculateSurgeFee(delay) * 0.5);
            } else {
                regular++;
                grandTotal += acc.calculateSurgeFee(delay);
            }
        }

        System.out.printf("%d processed | %d null skipped | %d premium | %d regular | grand total surge fees = %.2f%n",
                processed, nullSkipped, premium, regular, grandTotal);
    }

    public static void main(String[] args) {
        DeliveryAccount[] accounts = {
            new PremiumAccount("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };
        double[] amounts = {500, 400, 300};
        int[] delayMinutesArray = {10, 5, 0};

        processBatch(accounts, amounts, delayMinutesArray);
    }
}