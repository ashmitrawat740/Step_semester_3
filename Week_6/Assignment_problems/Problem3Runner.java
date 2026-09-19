import java.util.Arrays;

class RaceEntry {
    protected final String bibNumber;
    protected final double entryFee;
    protected double paidAmount;
    protected double lateFeesAdded;
    private final double[] lateFeeHistory;
    private int historyCount;

    public RaceEntry(String bibNumber, double entryFee) {
        this.bibNumber = bibNumber;
        this.entryFee = entryFee;
        this.paidAmount = 0.0;
        this.lateFeesAdded = 0.0;
        this.lateFeeHistory = new double[10];
        this.historyCount = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.paidAmount += amount;
        }
    }

    protected void applyLateFee(double amount) {
        if (amount > 0) {
            this.lateFeesAdded += amount;
            if (historyCount < lateFeeHistory.length) {
                lateFeeHistory[historyCount++] = amount;
            }
        }
    }

    public double getBalanceDue() {
        return Math.max(0.0, (this.entryFee + this.lateFeesAdded) - this.paidAmount);
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(this.lateFeeHistory, this.historyCount);
    }
}

class RunnerEntry extends RaceEntry {
    public RunnerEntry(String bibNumber, double entryFee, String category) {
        super(bibNumber, entryFee);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class Problem3Runner {
    public static void main(String[] args) {
        RunnerEntry r = new RunnerEntry("BIB2001", 80, "Open 10K");
        r.pay(30);
        r.applyLateFee(20);
        System.out.println(r.getBalanceDue());

        double[] history = r.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(r.getLateFeeHistory()));
    }
}