public final class SurgeFeeCalculator {
    private final double minimumSurgePercent;

    public SurgeFeeCalculator(double minimumSurgePercent) {
        if (minimumSurgePercent < 0) {
            throw new IllegalArgumentException("Minimum surge percentage cannot be negative.");
        }
        this.minimumSurgePercent = minimumSurgePercent;
    }

    public final double calculateSurgeFee(double orderValue, int delayMinutes) {
        if (orderValue < 0 || delayMinutes < 0) {
            throw new IllegalArgumentException("Order value and delay minutes must be non-negative.");
        }
        if (delayMinutes == 0) {
            return 0.0;
        }

        int b1 = Math.min(delayMinutes, 5);
        int b2 = Math.max(0, Math.min(delayMinutes - 5, 10));
        int b3 = Math.max(0, delayMinutes - 15);

        double tieredRate = (b1 * 0.005) + (b2 * 0.01) + (b3 * 0.02);
        double tieredFee = orderValue * tieredRate;

        double floorFee = orderValue * (minimumSurgePercent / 100.0);
        return Math.max(tieredFee, floorFee);
    }

    public static void main(String[] args) {
        SurgeFeeCalculator calc = new SurgeFeeCalculator(1.0);
        System.out.println("Rs " + calc.calculateSurgeFee(500, 0));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 1));
        System.out.println("Rs " + calc.calculateSurgeFee(500, 16));
    }
}