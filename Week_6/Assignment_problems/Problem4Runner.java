class RaceEntry {
    protected final String bibNumber;
    protected final double entryFee;
    protected double paidAmount;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
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

    public String announce() {
        return "Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue();
    }
}

class RunnerEntry extends RaceEntry {
    private final String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue();
    }
}

class RelayTeamEntry extends RaceEntry {
    private final int teamSize;

    public RelayTeamEntry(String bibNumber, double entryFee, int teamSize) {
        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue();
    }
}

public class Problem4Runner {
    public static String announceAll(RaceEntry[] entries) {
        if (entries == null) return "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < entries.length; i++) {
            RaceEntry e = entries[i];
            sb.append(e.announce());

            if (e instanceof RelayTeamEntry) {
                RelayTeamEntry rte = (RelayTeamEntry) e;
                sb.append(" [Team size via downcast: ").append(rte.getTeamSize()).append("]");
            }

            if (i < entries.length - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        runnerEntry.pay(30);
        runnerEntry.applyLateFee(20);

        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        RaceEntry[] fleet = { runnerEntry, relayEntry };
        System.out.println(announceAll(fleet));
    }
}