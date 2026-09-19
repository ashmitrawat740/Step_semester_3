class EventTicket {
    private static int counter = 1000;
    private static int ticketsIssued = 0;

    protected final String ticketId;
    protected final double basePrice;
    protected double balanceDue;

    public EventTicket(double basePrice) {
        ticketsIssued++;
        this.ticketId = "TCK-" + (++counter);
        this.basePrice = basePrice;
        this.balanceDue = basePrice;
    }

    public String getTicketId() {
        return ticketId;
    }

    public static int getTicketsIssued() {
        return ticketsIssued;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.balanceDue = Math.max(0.0, this.balanceDue - amount);
        }
    }

    public void pay(double amount, String mode) {
        pay(amount);
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public static boolean isValidPromoCode(String code) {
        if (code == null || code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'F') {
            return false;
        }
        for (int i = 1; i <= 3; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        return Character.isUpperCase(code.charAt(4));
    }
}

class GroupTicket extends EventTicket {
    private final int groupSize;

    public GroupTicket(double basePrice, int groupSize) {
        super(basePrice);
        this.groupSize = Math.max(1, groupSize);
    }

    public int getGroupSize() {
        return groupSize;
    }
}

public class TicketSettlementEngine {
    public static String processNightlySettlement(EventTicket[] tickets) {
        if (tickets == null) {
            return "0 processed 0 null skipped | 0 group | 0 individual";
        }

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket t : tickets) {
            if (t == null) {
                nullSkipped++;
                continue;
            }

            processed++;
            if (t instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed " + nullSkipped + " null skipped | " +
               group + " group | " + individual + " individual";
    }

    public static void main(String[] args) {
        EventTicket t1 = new EventTicket(500);
        System.out.println(t1.getTicketId());
        System.out.println(EventTicket.getTicketsIssued());

        System.out.println(EventTicket.isValidPromoCode("F123A"));
        System.out.println(EventTicket.isValidPromoCode("F12A"));
        System.out.println(EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");
        System.out.println(t1.getBalanceDue());

        EventTicket[] batch = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };
        System.out.println(processNightlySettlement(batch));
    }
}