public class FoodOrder {
    private final String studentName;
    private final String dishName;
    private boolean isDelivered;

    public FoodOrder(String studentName, String dishName) {
        if (studentName == null || studentName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid student name.");
        }
        if (dishName == null || dishName.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid dish name.");
        }
        this.studentName = studentName.trim();
        this.dishName = dishName.trim();
        this.isDelivered = false;
    }

    public void markDelivered() {
        if (this.isDelivered) {
            System.out.println("Warning: Order for " + studentName + " was already marked delivered!");
        } else {
            this.isDelivered = true;
            System.out.println("Order for " + studentName + " marked delivered.");
        }
    }

    public static void processBatch(String[][] rawOrders) {
        if (rawOrders == null) return;
        int valid = 0;
        int rejected = 0;

        for (String[] entry : rawOrders) {
            if (entry == null || entry.length < 2) {
                rejected++;
                continue;
            }
            try {
                new FoodOrder(entry[0], entry[1]);
                valid++;
            } catch (IllegalArgumentException e) {
                rejected++;
            }
        }
        System.out.println("Valid: " + valid + " | Rejected: " + rejected);
    }

    public static void main(String[] args) {
        String[][] raw = {
            {"Ravi", "Paneer Butter Masala"},
            {"", "Chole Bhature"},
            {"Meera", " "},
            {"Divya", "Veg Biryani"}
        };
        processBatch(raw);
    }
}