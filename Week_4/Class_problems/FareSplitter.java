import java.util.Arrays;

public class FareSplitter {
    private final String tripId;
    private final double totalFare;
    private final int passengerCount;

    public FareSplitter(String tripId, double totalFare, int passengerCount) {
        if (totalFare < 0) {
            throw new IllegalArgumentException("Total fare cannot be negative.");
        }
        if (passengerCount <= 0) {
            throw new IllegalArgumentException("Passenger count must be positive.");
        }
        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public FareSplitter(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public FareSplitter(String tripId) {
        this(tripId, 0.0, 2);
    }

    public double[] fareBreakdown() {
        double[] shares = new double[passengerCount];
        if (totalFare == 0.0) {
            Arrays.fill(shares, 0.0);
            return shares;
        }

        long totalPaisa = Math.round(totalFare * 100.0);
        long basePaisa = totalPaisa / passengerCount;
        long remainderPaisa = totalPaisa % passengerCount;

        for (int i = 0; i < passengerCount; i++) {
            shares[i] = basePaisa / 100.0;
        }

        shares[passengerCount - 1] += (remainderPaisa / 100.0);
        return shares;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new FareSplitter("TRIP001", 100000, 3).fareBreakdown()));
        System.out.println(Arrays.toString(new FareSplitter("TRIP003").fareBreakdown()));
    }
}