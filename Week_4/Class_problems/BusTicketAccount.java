class Sleeper extends BusTicketAccount {
    public Sleeper(String bookingId, double ticketFare) {
        super(bookingId, ticketFare);
    }

    public Sleeper(String bookingId) {
        super(bookingId);
    }
}

public class BusTicketAccount {
    private static double standardLateFeeRate;

    static {
        standardLateFeeRate = 0.01;
    }

    private final String bookingId;
    private final double ticketFare;

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {
        if (minutesLate <= 0) return 0.0;
        return ticketFare * standardLateFeeRate * minutesLate;
    }

    public static void processAccount(BusTicketAccount account, double amount, int minutesLate) {
    }

    public static void processBatch(BusTicketAccount[] accounts, double[] amounts, int[] minutesLateArray) {
        if (accounts == null || amounts == null || minutesLateArray == null) {
            System.out.println("Batch inputs cannot be null.");
            return;
        }

        int length = Math.min(accounts.length, Math.min(amounts.length, minutesLateArray.length));

        int processed = 0;
        int nullSkipped = 0;
        int sleeper = 0;
        int regular = 0;
        double grandTotal = 0.0;

        for (int i = 0; i < length; i++) {
            BusTicketAccount acc = accounts[i];
            if (acc == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            int minutesLate = minutesLateArray[i];

            if (acc instanceof Sleeper) {
                sleeper++;
                grandTotal += (acc.calculatePenalty(minutesLate) * 1.20);
            } else {
                regular++;
                grandTotal += acc.calculatePenalty(minutesLate);
            }
        }

        System.out.printf("%d processed | %d null skipped | %d sleeper | %d regular | grand total penalties = %.2f%n",
                processed, nullSkipped, sleeper, regular, grandTotal);
    }

    public static void main(String[] args) {
        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };
        double[] amounts = {1200, 900, 700};
        int[] minutesLateArray = {10, 5, 0};

        processBatch(accounts, amounts, minutesLateArray);
    }
}