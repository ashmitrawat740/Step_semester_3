final class LoanReceipt {

    private final String memberId;
    private final String[] bookIds;

    private static int totalProcessed;


    // Static block
    static {
        totalProcessed = 0;
    }


    public LoanReceipt(String memberId,
                       String[] bookIds) {

        if (memberId == null ||
            bookIds == null) {

            throw new IllegalArgumentException(
                "Invalid data"
            );
        }


        // Validate every book ID
        for (int i = 0; i < bookIds.length; i++) {

            String id = bookIds[i];

            if (id == null ||
                !id.matches("BK-\\d{3}")) {

                throw new IllegalArgumentException(
                    "Invalid book ID"
                );
            }
        }


        this.memberId = memberId;

        // Defensive copy
        this.bookIds = bookIds.clone();
    }


    public String[] getBookIds() {

        // Defensive copy
        return bookIds.clone();
    }


    public LoanReceipt withCorrectedBookId(
            int index,
            String newId) {

        if (index < 0 ||
            index >= bookIds.length) {

            throw new IndexOutOfBoundsException();
        }


        if (newId == null ||
            !newId.matches("BK-\\d{3}")) {

            throw new IllegalArgumentException(
                "Invalid book ID"
            );
        }


        // Make a new array
        String[] copy = bookIds.clone();

        copy[index] = newId;


        // Return a NEW object
        return new LoanReceipt(
            memberId,
            copy
        );
    }


    static String processNightlyCirculation(
            LoanReceipt[] receipts) {

        int processed = 0;
        int nullSkipped = 0;
        int referenceOnly = 0;
        int regular = 0;


        for (int i = 0; i < receipts.length; i++) {

            if (receipts[i] == null) {

                nullSkipped++;
                continue;
            }


            processed++;


            if (receipts[i]
                    instanceof ReferenceOnlyLoanReceipt) {

                referenceOnly++;

            } else {

                regular++;
            }
        }


        totalProcessed += processed;


        return processed + " processed | "
             + nullSkipped + " null skipped | "
             + referenceOnly + " reference-only | "
             + regular + " regular";
    }
}


class ReferenceOnlyLoanReceipt
        extends LoanReceipt {

    private final String roomNumber;


    public ReferenceOnlyLoanReceipt(
            String memberId,
            String[] bookIds,
            String roomNumber) {

        super(memberId, bookIds);

        this.roomNumber = roomNumber;
    }


    public String getRoomNumber() {
        return roomNumber;
    }
}


public class Main {

    public static void main(String[] args) {

        // Normal receipt
        LoanReceipt r =
            new LoanReceipt(
                "LIB-8841",
                new String[]{
                    "BK-100",
                    "BK-101"
                }
            );


        // Test defensive copy
        String[] ids = r.getBookIds();

        ids[0] = "HACKED";


        System.out.println(
            r.getBookIds()[0]
        );


        // Test with-style method
        LoanReceipt corrected =
            r.withCorrectedBookId(
                0,
                "BK-999"
            );


        System.out.println(
            corrected.getBookIds()[0]
        );


        // Reference-only receipt
        ReferenceOnlyLoanReceipt ref =
            new ReferenceOnlyLoanReceipt(
                "LIB-001",
                new String[]{"BK-200"},
                "Reading Room 3"
            );


        // Regular receipt
        LoanReceipt normal =
            new LoanReceipt(
                "LIB-002",
                new String[]{"BK-201"}
            );


        LoanReceipt[] receipts = {
            ref,
            null,
            normal
        };


        System.out.println(
            LoanReceipt.processNightlyCirculation(
                receipts
            )
        );
    }
}