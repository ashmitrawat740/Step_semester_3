import java.time.LocalDate;
import java.util.*;

abstract class Room {
    private final String roomNumber;
    private final double pricePerNight;

    public Room(String roomNumber, double pricePerNight) {
        this.roomNumber = roomNumber;
        this.pricePerNight = pricePerNight;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public double getPricePerNight() {
        return pricePerNight;
    }

    public abstract String getCategoryName();

    public double calculatePrice(long nights) {
        return nights * pricePerNight;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String roomNumber) {
        super(roomNumber, 200.0);
    }

    @Override
    public String getCategoryName() {
        return "Deluxe Room";
    }
}

class StandardRoom extends Room {
    public StandardRoom(String roomNumber) {
        super(roomNumber, 150.0);
    }

    @Override
    public String getCategoryName() {
        return "Standard Room";
    }
}

class Customer {
    private final String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

enum ReservationStatus {
    ACTIVE,
    CANCELLED
}

class Reservation {
    private final String reservationId;
    private final Room room;
    private final Customer customer;
    private final LocalDate checkIn;
    private final LocalDate checkOut;
    private final double totalPrice;
    private ReservationStatus status;

    public Reservation(String reservationId, Room room, Customer customer, LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = reservationId;
        this.room = room;
        this.customer = customer;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        this.totalPrice = room.calculatePrice(nights);
        this.status = ReservationStatus.ACTIVE;
    }

    public Room getRoom() {
        return room;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean overlapsWith(LocalDate start, LocalDate end) {
        if (this.status == ReservationStatus.CANCELLED) return false;
        return checkIn.isBefore(end) && start.isBefore(checkOut);
    }

    public boolean cancel(LocalDate requestDate) {
        if (requestDate.isBefore(checkIn)) {
            this.status = ReservationStatus.CANCELLED;
            return true;
        }
        return false;
    }
}

class HotelManager {
    private final List<Reservation> reservations = new ArrayList<>();

    public boolean isRoomAvailable(Room room, LocalDate start, LocalDate end) {
        for (Reservation res : reservations) {
            if (res.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                if (res.overlapsWith(start, end)) {
                    return false;
                }
            }
        }
        return true;
    }

    public Reservation bookRoom(String resId, Room room, Customer customer, LocalDate start, LocalDate end) {
        if (!isRoomAvailable(room, start, end)) {
            System.out.printf("Booking failed: %s %s is not available for %s to %s.%n",
                    room.getCategoryName(), room.getRoomNumber(), start, end);
            return null;
        }
        Reservation res = new Reservation(resId, room, customer, start, end);
        reservations.add(res);
        System.out.printf("%s %s booked from %s to %s. Total price: $%.2f.%n",
                room.getCategoryName(), room.getRoomNumber(), start, end, res.getTotalPrice());
        return res;
    }

    public void cancelReservation(Reservation res, LocalDate cancelDate) {
        if (res != null && res.cancel(cancelDate)) {
            System.out.printf("Reservation for %s %s cancelled successfully.%n",
                    res.getRoom().getCategoryName(), res.getRoom().getRoomNumber());
        } else {
            System.out.println("Cancellation failed: Deadline passed or invalid reservation.");
        }
    }
}

public class HotelBookingSystem {
    public static void main(String[] args) {
        HotelManager manager = new HotelManager();
        Customer customer = new Customer("John");

        Room deluxe101 = new DeluxeRoom("101");
        Room standard205 = new StandardRoom("205");

        Reservation res1 = manager.bookRoom("R1", deluxe101, customer, LocalDate.of(2024, 12, 1), LocalDate.of(2024, 12, 5));
        Reservation res2 = manager.bookRoom("R2", standard205, customer, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));
        Reservation res3 = manager.bookRoom("R3", deluxe101, customer, LocalDate.of(2024, 12, 3), LocalDate.of(2024, 12, 7));

        manager.cancelReservation(res1, LocalDate.of(2024, 11, 30));
    }
}