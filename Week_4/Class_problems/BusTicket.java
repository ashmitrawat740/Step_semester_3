import java.util.HashSet;
import java.util.Set;

public class BusTicket {
    private final String passengerName;
    private final String destination;
    private boolean checkedIn;

    public BusTicket(String passengerName, String destination) {
        if (passengerName == null || passengerName.trim().isEmpty() || !passengerName.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException("Invalid passenger name.");
        }
        if (destination == null || destination.trim().isEmpty() || !destination.matches("^[a-zA-Z\\s]+$")) {
            throw new IllegalArgumentException("Invalid destination.");
        }
        this.passengerName = passengerName.trim();
        this.destination = destination.trim();
        this.checkedIn = false;
    }

    public void markCheckedIn() {
        if (this.checkedIn) {
            System.out.println("Notice: Ticket already checked in.");
        } else {
            this.checkedIn = true;
            System.out.println("Check-in successful.");
        }
    }

    public static void processBatch(String[][] rawBookings) {
        if (rawBookings == null) return;

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;
        Set<String> seenPairs = new HashSet<>();

        for (String[] entry : rawBookings) {
            if (entry == null || entry.length < 2) {
                rejected++;
                continue;
            }

            try {
                BusTicket ticket = new BusTicket(entry[0], entry[1]);
                String key = ticket.passengerName.toLowerCase() + "|" + ticket.destination.toLowerCase();
                if (seenPairs.contains(key)) {
                    duplicates++;
                } else {
                    seenPairs.add(key);
                    valid++;
                }
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }

        System.out.println("Valid: " + valid + " | Rejected: " + rejected + " | Duplicates skipped: " + duplicates);
    }

    public static void main(String[] args) {
        String[][] raw = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };
        processBatch(raw);
    }
}