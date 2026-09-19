class RaceEntry {
    protected final String bibNumber;
    protected final double entryFee;
    protected double paidAmount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber: must be at least 4 non-whitespace characters.");
        }
        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.paidAmount = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.paidAmount += amount;
        }
    }

    public double getBalanceDue() {
        return Math.max(0.0, this.entryFee - this.paidAmount);
    }

    public static String registerBatch(String[] bibNumbers, double entryFee) {
        if (bibNumbers == null) {
            return "Registered: 0 Rejected: 0";
        }
        int registered = 0;
        int rejected = 0;

        for (String bib : bibNumbers) {
            try {
                new RaceEntry(bib, entryFee);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " Rejected: " + rejected;
    }
}

class RunnerEntry extends RaceEntry {
    protected final String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}

public class Problem1Runner {
    public static void main(String[] args) {
        try {
            new RaceEntry("B1", 50);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        System.out.println(r.getBalanceDue());

        String[] bibs = {"BIB1", "B1", "BIB2"};
        System.out.println(RaceEntry.registerBatch(bibs, 80));
    }
}