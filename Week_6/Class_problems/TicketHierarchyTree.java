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

    public void printTicket() {
        System.out.println("Standard Event Ticket | Balance Due: " + getBalanceDue());
    }
}

class WorkshopTicket extends EventTicket {
    protected final String track;

    public WorkshopTicket(String attendeeId, double basePrice, String track) {
        super(attendeeId, basePrice);
        this.track = track;
    }

    @Override
    public void printTicket() {
        System.out.println("Workshop Ticket | Track: " + track + " | Balance Due: " + getBalanceDue());
    }
}

class PremiumWorkshopTicket extends WorkshopTicket {
    private final double kitFee;

    public PremiumWorkshopTicket(String attendeeId, double basePrice, String track, double kitFee) {
        super(attendeeId, basePrice, track);
        this.kitFee = kitFee;
    }

    @Override
    public double getBalanceDue() {
        return Math.max(0.0, (this.basePrice + this.kitFee) - this.paidAmount);
    }

    @Override
    public void printTicket() {
        System.out.println("Premium Workshop Ticket | Track: " + track + " | Kit Fee: " + kitFee + " | Balance Due: " + getBalanceDue());
    }
}

class HackathonTicket extends EventTicket {
    private final String teamName;

    public HackathonTicket(String attendeeId, double basePrice, String teamName) {
        super(attendeeId, basePrice);
        this.teamName = teamName;
    }

    @Override
    public void printTicket() {
        System.out.println("Hackathon Ticket | Team: " + teamName + " | Balance Due: " + getBalanceDue());
    }
}

public class TicketHierarchyTree {
    public static String classifyGeneration(EventTicket ticket) {
        if (ticket instanceof PremiumWorkshopTicket) {
            return "Multilevel descendant (3 generations deep)";
        } else if (ticket instanceof HackathonTicket) {
            return "Hierarchical sibling (independent branch)";
        } else if (ticket instanceof WorkshopTicket) {
            return "Single-inheritance child";
        }
        return "Base parent";
    }

    public static double getTotalBalanceDue(EventTicket[] tickets) {
        if (tickets == null) return 0.0;
        double total = 0.0;
        for (EventTicket t : tickets) {
            if (t != null) {
                total += t.getBalanceDue();
            }
        }
        return total;
    }

    public static void main(String[] args) {
        EventTicket standardTicket = new EventTicket("STU1", 500);
        WorkshopTicket workshopTicket = new WorkshopTicket("STU2", 1200, "AI/ML");
        PremiumWorkshopTicket premiumTicket = new PremiumWorkshopTicket("STU3", 2000, "Cloud Native", 300);
        HackathonTicket hackathonTicket = new HackathonTicket("STU4", 800, "Byte Force");

        standardTicket.printTicket();
        workshopTicket.printTicket();
        premiumTicket.printTicket();
        hackathonTicket.printTicket();

        System.out.println(classifyGeneration(premiumTicket));
        System.out.println(classifyGeneration(hackathonTicket));

        EventTicket[] tickets = {standardTicket, workshopTicket, premiumTicket, hackathonTicket};
        System.out.println(getTotalBalanceDue(tickets));
    }
}