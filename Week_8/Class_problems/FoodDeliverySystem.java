import java.util.*;

// Strategy Pattern for Payment Methods
interface IPaymentMethod {
    boolean processPayment(double amount);
}

class CreditCardPayment implements IPaymentMethod {
    @Override
    public boolean processPayment(double amount) {
        System.out.printf("Payment via Credit Card successful. Charge: $%.2f%n", amount);
        return true;
    }
}

class DigitalWalletPayment implements IPaymentMethod {
    private final boolean shouldSucceed;

    public DigitalWalletPayment(boolean shouldSucceed) {
        this.shouldSucceed = shouldSucceed;
    }

    @Override
    public boolean processPayment(double amount) {
        if (shouldSucceed) {
            System.out.printf("Payment via Digital Wallet successful. Charge: $%.2f%n", amount);
            return true;
        } else {
            System.out.println("Payment via Digital Wallet failed.");
            return false;
        }
    }
}

class FoodItem {
    private final String name;
    private final double price;

    public FoodItem(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class LineItem {
    private final FoodItem item;
    private final int quantity;

    public LineItem(FoodItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return item.getPrice() * quantity;
    }

    public String getDescription() {
        return item.getName() + " (Qty " + quantity + ")";
    }
}

enum OrderStatus {
    CREATED,
    PENDING_PAYMENT,
    PAID
}

class Order {
    private static int idCounter = 123;
    private final int orderId;
    private final List<LineItem> items;
    private OrderStatus status;

    public Order() {
        this.orderId = idCounter++;
        this.items = new ArrayList<>();
        this.status = OrderStatus.CREATED;
        System.out.println("Order created.");
    }

    public void addItem(FoodItem item, int quantity) {
        LineItem lineItem = new LineItem(item, quantity);
        items.add(lineItem);
        System.out.println("Added " + lineItem.getDescription() + ".");
    }

    public double calculateTotal() {
        double total = 0.0;
        for (LineItem item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public boolean placeAndPay(IPaymentMethod paymentMethod) {
        if (items.isEmpty()) {
            System.out.println("Cannot place order: Order must contain at least one item.");
            return false;
        }
        System.out.println("Order placed successfully.");
        
        boolean success = paymentMethod.processPayment(calculateTotal());
        if (success) {
            this.status = OrderStatus.PAID;
            System.out.println("Order status: Paid.");
            System.out.printf("Notification: Order #%d placed and paid.%n", orderId);
        } else {
            this.status = OrderStatus.PENDING_PAYMENT;
            System.out.println("Order status: Pending Payment.");
            System.out.printf("Notification: Order #%d placed, awaiting payment.%n", orderId);
        }
        return success;
    }
}

public class FoodDeliverySystem {
    public static void main(String[] args) {
        FoodItem pizza = new FoodItem("Pizza", 12.0);
        FoodItem soda = new FoodItem("Soda", 2.0);
        FoodItem burger = new FoodItem("Burger", 8.0);

        // Order 1
        Order order1 = new Order();
        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);
        order1.placeAndPay(new CreditCardPayment());

        System.out.println();

        // Empty Cart Test
        Order emptyOrder = new Order();
        emptyOrder.placeAndPay(new CreditCardPayment());

        System.out.println();

        // Order 2 (Failed Payment Test)
        Order order2 = new Order();
        order2.addItem(burger, 1);
        order2.placeAndPay(new DigitalWalletPayment(false));
    }
}