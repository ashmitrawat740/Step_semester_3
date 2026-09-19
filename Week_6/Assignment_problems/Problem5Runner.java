class RaceEntry {
    private static int bibCounter = 0;

    protected final String entryCode;
    protected final String bibNumber;
    protected final double entryFee;
    protected double balanceDue;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber");
        }
        bibCounter++;
        this.entryCode = "ENTRY-" + bibCounter;
        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.balanceDue = entryFee;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.balanceDue = Math.max(0.0, this.balanceDue - amount);
        }
    }

    public void pay(double amount, String mode) {
        System.out.println("Paying via " + mode);
        pay(amount);
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }
}

class RelayTeamEntry extends RaceEntry {
    private final int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = Math.max(1, teamSize);
    }

    public int getTeamSize() {
        return teamSize;
    }
}

class EliteRunnerEntry extends RaceEntry {
    public EliteRunnerEntry(String bibNumber, double entryFee) {
        super(bibNumber, entryFee);
    }
}

public class Problem5Runner {
    public static String settleNight(RaceEntry[] entries) {
        if (entries == null) {
            return "0 processed 0 null skipped | 0 relay | 0 individual";
        }

        int processed = 0;
        int nullSkipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry e : entries) {
            if (e == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (e instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed + " processed " + nullSkipped + " null skipped | " +
               relay + " relay | " + individual + " individual";
    }

    public static void main(String[] args) {
        System.out.println(RaceEntry.isValidDiscountCode("M123A"));
        System.out.println(RaceEntry.isValidDiscountCode("M12A"));
        System.out.println(RaceEntry.isValidDiscountCode("X123A"));

        RaceEntry r = new RaceEntry("BIB2001", 80);
        r.pay(10, "UPI");

        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] batch = { eliteEntry, null, relayEntry };
        System.out.println(settleNight(batch));

        System.out.println(RaceEntry.getBibCounter());
    }
}