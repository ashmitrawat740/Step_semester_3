abstract class Ride {
    protected final String type;
    protected final double distance;

    public Ride(String type, double distance) {
        this.type = type;
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public abstract double calculateFare();
}

class Auto extends Ride {
    public Auto(double distance) {
        super("Auto", distance);
    }

    @Override
    public double calculateFare() {
        return 30.0 + 15.0 * distance;
    }
}

class Cab extends Ride {
    private final double surge;

    public Cab(double distance, double surge) {
        super("Cab", distance);
        this.surge = surge;
    }

    @Override
    public double calculateFare() {
        return (50.0 + 20.0 * distance) * surge;
    }
}

class Bike extends Ride {
    private final int waitingMinutes;

    public Bike(double distance, int waitingMinutes) {
        super("Bike", distance);
        this.waitingMinutes = waitingMinutes;
    }

    @Override
    public double calculateFare() {
        return 20.0 + 10.0 * distance + 2.0 * waitingMinutes;
    }
}

public class RideFareEngine {
    public static void main(String[] args) {
        Ride[] rides = {
            new Auto(4),
            new Cab(6, 1.5),
            new Bike(3, 5)
        };

        double grandTotal = 0;
        for (Ride ride : rides) {
            double fare = ride.calculateFare();
            grandTotal += fare;
            System.out.printf("%s: %.0f%n", ride.getType(), fare);
        }
        System.out.printf("Total: %.0f%n", grandTotal);
    }
}