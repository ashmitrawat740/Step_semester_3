abstract class Vehicle {
    private final String licensePlate;
    private final String model;
    private boolean isAvailable;

    public Vehicle(String licensePlate, String model) {
        this.licensePlate = licensePlate;
        this.model = model;
        this.isAvailable = true;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public abstract double calculateRentalCharge(int days);
}

class StandardCar extends Vehicle {
    private static final double DAILY_RATE = 50.0;

    public StandardCar(String licensePlate, String model) {
        super(licensePlate, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class LuxuryCar extends Vehicle {
    private static final double DAILY_RATE = 100.0;

    public LuxuryCar(String licensePlate, String model) {
        super(licensePlate, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class SUV extends Vehicle {
    private static final double DAILY_RATE = 80.0;

    public SUV(String licensePlate, String model) {
        super(licensePlate, model);
    }

    @Override
    public double calculateRentalCharge(int days) {
        return days * DAILY_RATE;
    }
}

class Rental {
    private final Vehicle vehicle;
    private final int days;
    private final double totalCharge;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
        this.totalCharge = vehicle.calculateRentalCharge(days);
        vehicle.setAvailable(false);
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void returnVehicle() {
        vehicle.setAvailable(true);
        System.out.println(vehicle.getModel() + " returned. Now available.");
    }
}

class RentalService {
    public Rental rentVehicle(Vehicle vehicle, int days) {
        if (!vehicle.isAvailable()) {
            System.out.println(vehicle.getModel() + " is currently unavailable.");
            return null;
        }
        Rental rental = new Rental(vehicle, days);
        System.out.printf("%s rented for %d days. Total charge: $%.2f%n", 
                vehicle.getModel(), days, rental.getTotalCharge());
        return rental;
    }
}

public class VehicleRentalSystem {
    public static void main(String[] args) {
        RentalService service = new RentalService();

        Vehicle luxuryCar = new LuxuryCar("LX-001", "Luxury Car A");
        Vehicle standardCar = new StandardCar("ST-002", "Standard Car B");

        Rental rental1 = service.rentVehicle(luxuryCar, 3);
        Rental rental2 = service.rentVehicle(standardCar, 5);

        if (rental1 != null) {
            rental1.returnVehicle();
        }
    }
}