public final class BoardingPenaltyCalculator {
    private final double minimumPenaltyPercent;

    public BoardingPenaltyCalculator(double minimumPenaltyPercent) {
        if (minimumPenaltyPercent < 0) {
            throw new IllegalArgumentException("Minimum penalty percent cannot be negative.");
        }
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {
        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException("Fare and late minutes must be non-negative.");
        }
        if (minutesLate == 0) {
            return 0.0;
        }

        int b1 = Math.min(minutesLate, 5);
        int b2 = Math.max(0, Math.min(minutesLate - 5, 10));
        int b3 = Math.max(0, minutesLate - 15);

        double tieredRate = (b1 * 0.005) + (b2 * 0.01) + (b3 * 0.02);
        double tieredPenalty = ticketFare * tieredRate;

        double floorPenalty = ticketFare * (minimumPenaltyPercent / 100.0);
        return Math.max(tieredPenalty, floorPenalty);
    }

    public static void main(String[] args) {
        BoardingPenaltyCalculator calc = new BoardingPenaltyCalculator(1.0);
        System.out.println("Rs " + calc.calculatePenalty(1000, 0));
        System.out.println("Rs " + calc.calculatePenalty(1000, 1));
        System.out.println("Rs " + calc.calculatePenalty(1000, 16));
    }
}