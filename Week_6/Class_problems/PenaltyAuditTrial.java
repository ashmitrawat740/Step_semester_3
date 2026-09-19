import java.util.Arrays;

class EventTicket {
    protected final String attendeeId;
    protected final double basePrice;
    protected double paidAmount;
    protected double lateFeesAdded;
    private final double[] lateFeeHistory;
    private int historyCount;

    public EventTicket(String attendeeId, double basePrice) {
        this.attendeeId = attendeeId;
        this.basePrice = basePrice;
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
        return Math.max(0.0, (this.basePrice + this.lateFeesAdded) - this.paidAmount);
    }

    public double[] getLateFeeHistory() {
        return Arrays.copyOf(this.lateFeeHistory, this.historyCount);
    }
}

class WorkshopTicket extends EventTicket {
    public WorkshopTicket(double basePrice) {
        super("DEFAULT", basePrice);
    }

    public WorkshopTicket(String attendeeId, double basePrice) {
        super(attendeeId, basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class PenaltyAuditTrail {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);
        w.pay(1200);
        w.applyLateFee(100);
        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();
        System.out.println(Arrays.toString(history));
        history[0] = 999;
        System.out.println(Arrays.toString(w.getLateFeeHistory()));
    }
}