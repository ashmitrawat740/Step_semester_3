public class Order {
    public static class LineItem {
        private final String productName;
        private final int quantity;
        private final double unitPrice;

        public LineItem(String productName, int quantity, double unitPrice) {
            this.productName = productName;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public double total() {
            return quantity * unitPrice;
        }

        public String getProductName() {
            return productName;
        }
    }

    public class Cursor {
        private int index = 0;

        public boolean hasNext() {
            return index < count;
        }

        public LineItem next() {
            if (!hasNext()) {
                throw new IllegalStateException("No more line items.");
            }
            return items[index++];
        }

        public void reset() {
            index = 0;
        }
    }

    private final LineItem[] items;
    private int count;

    public Order(int capacity) {
        this.items = new LineItem[capacity];
        this.count = 0;
    }

    public void addItem(LineItem item) {
        if (count < items.length) {
            items[count++] = item;
        }
    }

    public Cursor cursor() {
        return new Cursor();
    }

    public static void main(String[] args) {
        Order order = new Order(100);
        order.addItem(new Order.LineItem("Pen", 3, 10));
        order.addItem(new Order.LineItem("Book", 2, 250));
        order.addItem(new Order.LineItem("Bag", 1, 700));

        Order.Cursor cursor = order.cursor();
        double grandTotal = 0;

        while (cursor.hasNext()) {
            LineItem item = cursor.next();
            double lineTotal = item.total();
            grandTotal += lineTotal;
            System.out.printf("%s %.0f%n", item.getProductName(), lineTotal);
        }

        System.out.printf("Total %.0f%n", grandTotal);
        System.out.println("cursor: inner (needs direct access to the specific Order instance's private items array and count)");
        System.out.println("line item: static nested (represents a standalone product entry that requires no reference to an enclosing Order)");
    }
}