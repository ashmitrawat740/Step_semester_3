import java.util.*;

interface PricingPlan {
    double calculateDiscountedPrice(double fullPrice);
}

class DayScholarPlan implements PricingPlan {
    @Override
    public double calculateDiscountedPrice(double fullPrice) {
        return fullPrice;
    }
}

class HostellerPlan implements PricingPlan {
    @Override
    public double calculateDiscountedPrice(double fullPrice) {
        return fullPrice * 0.90; // 10% discount
    }
}

class StaffPlan implements PricingPlan {
    @Override
    public double calculateDiscountedPrice(double fullPrice) {
        return fullPrice * 0.80; // 20% discount
    }
}

class Transaction {
    private final String description;
    private final double amount; // Positive for top-up/refund, negative for purchase

    public Transaction(String description, double amount) {
        this.description = description;
        this.amount = amount;
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }
}

class PurchaseRecord {
    private final String itemName;
    private final double chargedAmount;
    private boolean refunded = false;

    public PurchaseRecord(String itemName, double chargedAmount) {
        this.itemName = itemName;
        this.chargedAmount = chargedAmount;
    }

    public String getItemName() { return itemName; }
    public double getChargedAmount() { return chargedAmount; }
    public boolean isRefunded() { return refunded; }
    public void markRefunded() { this.refunded = true; }
}

class SmartCard {
    private final String cardId;
    private final PricingPlan pricingPlan;
    private double balance = 0.0;
    private boolean isBlocked = false;
    private final List<Transaction> transactions = new ArrayList<>();
    private final Map<String, PurchaseRecord> purchaseHistory = new HashMap<>();

    public SmartCard(String cardId, PricingPlan pricingPlan) {
        this.cardId = cardId;
        this.pricingPlan = pricingPlan;
    }

    public double getBalance() { return balance; }

    public void topUp(double amount) {
        if (isBlocked) {
            System.out.println("Top-up failed: Card " + cardId + " is blocked.");
            return;
        }
        if (amount < 100) {
            System.out.println("Top-up failed: Minimum top-up amount is ₹100.");
            return;
        }
        if (balance + amount > 5000) {
            System.out.println("Top-up failed: Exceeds maximum balance limit of ₹5000.");
            return;
        }

        balance += amount;
        transactions.add(new Transaction("Top-Up", amount));
        System.out.printf("%s topped up with ₹%.2f. Balance: ₹%.2f.%n", cardId, amount, balance);
    }

    public void purchase(String itemName, double fullPrice) {
        if (isBlocked) {
            System.out.println("Purchase failed: Card " + cardId + " is blocked.");
            return;
        }

        double finalPrice = pricingPlan.calculateDiscountedPrice(fullPrice);

        if (balance < finalPrice) {
            System.out.printf("Purchase failed: Insufficient balance (required %.2f, available %.2f).%n",
                finalPrice, balance);
            return;
        }

        balance -= finalPrice;
        transactions.add(new Transaction("Purchase: " + itemName, -finalPrice));
        purchaseHistory.put(itemName, new PurchaseRecord(itemName, finalPrice));
        System.out.printf("%s purchased for %.2f. Balance: ₹%.2f.%n", itemName, finalPrice, balance);
    }

    public void refund(String itemName) {
        if (isBlocked) {
            System.out.println("Refund failed: Card " + cardId + " is blocked.");
            return;
        }

        PurchaseRecord record = purchaseHistory.get(itemName);
        if (record == null) {
            System.out.println("Refund failed: No purchase record found for " + itemName + ".");
            return;
        }

        if (record.isRefunded()) {
            System.out.println("Refund rejected: " + itemName + " has already been refunded.");
            return;
        }

        record.markRefunded();
        double refundAmount = record.getChargedAmount();
        balance += refundAmount;
        transactions.add(new Transaction("Refund: " + itemName, refundAmount));
        System.out.printf("Refund of ₹%.2f for %s processed. Balance: ₹%.2f.%n",
            refundAmount, itemName, balance);
    }

    public void printMiniStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Mini-statement for ").append(cardId).append(": ");
        for (int i = 0; i < transactions.size(); i++) {
            double amt = transactions.get(i).getAmount();
            if (amt > 0) {
                statement.append("+").append(String.format("%.2f", amt));
            } else {
                statement.append(String.format("%.2f", amt));
            }
            if (i < transactions.size() - 1) statement.append(", ");
        }
        statement.append(" = ").append(String.format("%.2f", balance));
        System.out.println(statement.toString());
    }
}

public class Question5 {
    public static void main(String[] args) {
        // 1. Hosteller plan topped up with 500
        SmartCard card = new SmartCard("C-2045", new HostellerPlan());
        card.topUp(500);

        // 2. Buy Veg Thali (120 full price -> 108 discounted)
        card.purchase("Veg Thali", 120);

        // 3. Buy Cold Coffee (60 full price -> 54 discounted)
        card.purchase("Cold Coffee", 60);

        // 4. Attempt to buy items worth 400 (360 discounted vs 338 balance)
        card.purchase("Bulk Items", 400);

        // 5. Canteen refunds Veg Thali
        card.refund("Veg Thali");

        // 6. Canteen attempts to refund Veg Thali again
        card.refund("Veg Thali");

        // 7. Request mini-statement
        card.printMiniStatement();
    }
}