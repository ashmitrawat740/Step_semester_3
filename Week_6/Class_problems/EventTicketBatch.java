class EventTicket {
    protected final String attendeeId;
    protected final double basePrice;
    protected double paidAmount;

    public EventTicket(String attendeeId, double basePrice) {
        if (attendeeId == null || attendeeId.trim().isEmpty() || attendeeId.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid attendeeId");
        }
        this.attendeeId = attendeeId.trim();
        this.basePrice = basePrice;
        this.paidAmount = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.paidAmount += amount;
        }
    }

    public double getBalanceDue() {
        return Math.max(0.0, this.basePrice - this.paidAmount);
    }

    public static String registerBatch(String[] attendeeIds, double basePrice) {
        if (attendeeIds == null) {
            return "Registered: 0 Rejected: 0";
        }
        int registered = 0;
        int rejected = 0;

        for (String id : attendeeIds) {
            try {
                new EventTicket(id, basePrice);
                registered++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        return "Registered: " + registered + " Rejected: " + rejected;
    }
}

class WorkshopTicket extends EventTicket {
    protected final String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }
}

public class EventTicketBatch {
    public static void main(String[] args) {
        try {
            new EventTicket("ST1", 500);
        } catch (IllegalArgumentException e) {
            System.out.println("construction rejected");
        }

        WorkshopTicket w = new WorkshopTicket("STU2", 1200, "AI/ML");
        w.pay(500);
        System.out.println(w.getBalanceDue());

        String[] ids = {"STU1", "ST1", "STU2", "STU3", "   "};
        System.out.println(EventTicket.registerBatch(ids, 500));
    }
}