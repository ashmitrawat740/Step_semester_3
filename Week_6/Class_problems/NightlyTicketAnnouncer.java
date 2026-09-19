class EventTicket {
    protected final double basePrice;
    protected double paidAmount;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        this.paidAmount = 0.0;
    }

    public double getBalanceDue() {
        return Math.max(0.0, this.basePrice - this.paidAmount);
    }

    public String printTicket() {
        return "Standard Balance: " + getBalanceDue();
    }
}

class WorkshopTicket extends EventTicket {
    private final String track;

    public WorkshopTicket(double basePrice, String track) {
        super(basePrice);
        this.track = track;
    }

    public String getTrack() {
        return track;
    }

    @Override
    public String printTicket() {
        return "Workshop | Track: " + track + " | Balance: " + getBalanceDue();
    }
}

public class NightlyTicketAnnouncer {
    public static String batchPrint(EventTicket[] tickets) {
        if (tickets == null) return "";
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < tickets.length; i++) {
            EventTicket t = tickets[i];
            sb.append(t.printTicket());

            if (t instanceof WorkshopTicket) {
                WorkshopTicket wt = (WorkshopTicket) t;
                sb.append(" [Track via downcast: ").append(wt.getTrack()).append("]");
            }

            if (i < tickets.length - 1) {
                sb.append(" | ");
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        EventTicket[] batch = {
            new EventTicket(500),
            new WorkshopTicket(1200, "AI/ML")
        };
        System.out.println(batchPrint(batch));
    }
}