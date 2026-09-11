class BookInventory {

    private int copiesTotal;
    private int copiesAvailable;


    BookInventory(int copiesTotal) {

        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                "Invalid number of copies"
            );
        }

        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal;
    }


    void checkOut() {

        if (copiesAvailable > 0) {
            copiesAvailable--;
        }
    }


    void checkIn() {

        if (copiesAvailable < copiesTotal) {
            copiesAvailable++;
        }
    }


    int getCopiesAvailable() {
        return copiesAvailable;
    }
}


public class Main {

    public static void main(String[] args) {

        BookInventory b =
            new BookInventory(3);

        b.checkOut();
        b.checkOut();
        b.checkOut();

        // Fourth checkout is rejected
        b.checkOut();

        System.out.println(
            b.getCopiesAvailable()
        );

        b.checkIn();
        b.checkIn();
        b.checkIn();

        // Fourth check-in is rejected
        b.checkIn();

        System.out.println(
            b.getCopiesAvailable()
        );
    }
}