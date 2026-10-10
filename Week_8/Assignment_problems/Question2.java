import java.util.*;

enum ParcelStatus {
    BOOKED, PICKED_UP, IN_TRANSIT, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
}

interface ShippingType {
    double calculateCharge(double weightKg);
    String getName();
}

class StandardShipping implements ShippingType {
    @Override
    public double calculateCharge(double weightKg) {
        return 40.0 + (10.0 * weightKg);
    }
    @Override
    public String getName() { return "Standard"; }
}

class ExpressShipping implements ShippingType {
    @Override
    public double calculateCharge(double weightKg) {
        return 80.0 + (15.0 * weightKg);
    }
    @Override
    public String getName() { return "Express"; }
}

class FragileShipping implements ShippingType {
    private final StandardShipping standardShipping = new StandardShipping();

    @Override
    public double calculateCharge(double weightKg) {
        return standardShipping.calculateCharge(weightKg) + 50.0;
    }
    @Override
    public String getName() { return "Fragile"; }
}

interface NotificationChannel {
    void notifyUpdate(String parcelId, ParcelStatus status);
}

class SmsChannel implements NotificationChannel {
    @Override
    public void notifyUpdate(String parcelId, ParcelStatus status) {
        System.out.println("[SMS] " + parcelId + " is now " + status + ".");
    }
}

class EmailChannel implements NotificationChannel {
    @Override
    public void notifyUpdate(String parcelId, ParcelStatus status) {
        System.out.println("[Email] " + parcelId + " is now " + status + ".");
    }
}

class Parcel {
    private final String parcelId;
    private final double weightKg;
    private final ShippingType shippingType;
    private final double charge;
    private ParcelStatus status;
    private final List<NotificationChannel> channels = new ArrayList<>();

    public Parcel(String parcelId, double weightKg, ShippingType shippingType) {
        this.parcelId = parcelId;
        this.weightKg = weightKg;
        this.shippingType = shippingType;
        this.charge = shippingType.calculateCharge(weightKg);
        this.status = ParcelStatus.BOOKED;
    }

    public String getParcelId() { return parcelId; }
    public double getCharge() { return charge; }
    public ParcelStatus getStatus() { return status; }

    public void subscribe(NotificationChannel channel) {
        channels.add(channel);
    }

    private void notifyChannels() {
        for (NotificationChannel channel : channels) {
            channel.notifyUpdate(parcelId, status);
        }
    }

    public void startBooking() {
        System.out.printf("Parcel %s booked (%s, %.0f kg). Charge: ₹%.2f.%n", 
            parcelId, shippingType.getName(), weightKg, charge);
        notifyChannels();
    }

    public boolean transitionTo(ParcelStatus nextStatus) {
        if (status == ParcelStatus.CANCELLED) {
            System.out.println("Invalid operation: Parcel is cancelled.");
            return false;
        }

        boolean isValid = false;
        switch (status) {
            case BOOKED:
                isValid = (nextStatus == ParcelStatus.PICKED_UP);
                break;
            case PICKED_UP:
                isValid = (nextStatus == ParcelStatus.IN_TRANSIT);
                break;
            case IN_TRANSIT:
                isValid = (nextStatus == ParcelStatus.OUT_FOR_DELIVERY);
                break;
            case OUT_FOR_DELIVERY:
                isValid = (nextStatus == ParcelStatus.DELIVERED);
                break;
            default:
                isValid = false;
        }

        if (isValid) {
            this.status = nextStatus;
            notifyChannels();
            return true;
        } else {
            System.out.println("Invalid transition: " + status + " → " + nextStatus + " is not allowed.");
            return false;
        }
    }

    public boolean cancel() {
        if (status == ParcelStatus.BOOKED) {
            status = ParcelStatus.CANCELLED;
            System.out.println("Parcel " + parcelId + " cancelled successfully.");
            notifyChannels();
            return true;
        } else {
            System.out.println("Cancellation failed: " + parcelId + " can be cancelled only while BOOKED.");
            return false;
        }
    }
}

public class Question2 {
    public static void main(String[] args) {
        // 1. Customer books Express parcel 'P101' weighing 2 kg and subscribes to SMS and Email updates
        Parcel p101 = new Parcel("P101", 2.0, new ExpressShipping());
        p101.subscribe(new SmsChannel());
        p101.subscribe(new EmailChannel());
        p101.startBooking();

        // 2. Courier marks 'P101' as picked up
        p101.transitionTo(ParcelStatus.PICKED_UP);

        // 3. Customer attempts to cancel 'P101'
        p101.cancel();

        // 4. Courier marks 'P101' as in transit
        p101.transitionTo(ParcelStatus.IN_TRANSIT);

        // 5. Courier attempts to mark 'P101' as delivered (Invalid transition)
        p101.transitionTo(ParcelStatus.DELIVERED);
    }
}