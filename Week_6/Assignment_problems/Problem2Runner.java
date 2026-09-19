class RaceEntry {
    protected final String bibNumber;
    protected final double entryFee;
    protected double paidAmount;

    public RaceEntry(String bibNumber, double entryFee) {
        if (bibNumber == null || bibNumber.trim().isEmpty() || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bibNumber");
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

    public void announce() {
        System.out.println("Race Entry | Bib: " + bibNumber + " | Balance: " + getBalanceDue());
    }
}

class RunnerEntry extends RaceEntry {
    protected final String category;

    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public void announce() {
        System.out.println("Runner Entry | Bib: " + bibNumber + " | Category: " + category + " | Balance: " + getBalanceDue());
    }
}

class EliteRunnerEntry extends RunnerEntry {
    private final double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double entryFee, String category, double sponsorBonus) {
        super(bibNumber, entryFee, category);
        this.sponsorBonus = sponsorBonus;
    }

    @Override
    public void announce() {
        System.out.println("Elite Runner | Bib: " + bibNumber + " | Category: " + category + " | Sponsor Bonus: " + sponsorBonus + " | Balance: " + getBalanceDue());
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
    public void announce() {
        System.out.println("Relay Team | Bib: " + bibNumber + " | Team Size: " + teamSize + " | Balance: " + getBalanceDue());
    }
}

public class Problem2Runner {
    public static String classifyGeneration(RaceEntry entry) {
        if (entry instanceof EliteRunnerEntry) {
            return "Multilevel descendant (3 generations deep)";
        } else if (entry instanceof RelayTeamEntry) {
            return "Hierarchical sibling (independent branch)";
        } else if (entry instanceof RunnerEntry) {
            return "Single-inheritance child";
        }
        return "Base parent";
    }

    public static double getTotalBalanceDue(RaceEntry[] entries) {
        if (entries == null) return 0.0;
        double total = 0.0;
        for (RaceEntry e : entries) {
            if (e != null) {
                total += e.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        RunnerEntry runnerEntry = new RunnerEntry("BIB2001", 80, "Open 10K");
        EliteRunnerEntry eliteEntry = new EliteRunnerEntry("BIB3001", 150, "Elite Full Marathon", 500);
        RelayTeamEntry relayEntry = new RelayTeamEntry("BIB4001", 300, 4);

        runnerEntry.announce();
        eliteEntry.announce();
        relayEntry.announce();

        System.out.println(classifyGeneration(eliteEntry));
        System.out.println(classifyGeneration(relayEntry));

        RaceEntry[] mixedField = {runnerEntry, eliteEntry, relayEntry};
        System.out.println(getTotalBalanceDue(mixedField));
    }
}