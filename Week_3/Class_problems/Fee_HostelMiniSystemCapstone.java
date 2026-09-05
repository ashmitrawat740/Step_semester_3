class FeeAccount {
    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = Math.max(0, totalFee);
        this.amountPaid = 0.0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            this.amountPaid += amount;
        } else {
            System.err.println("Rejected non-positive payment: Rs " + amount);
        }
    }

    public double getDue() {
        return Math.max(0.0, totalFee - amountPaid);
    }
}

class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount / 2.0);
        pay(amount / 2.0);
    }
}

class HostelRoom {
    private String roomNo;
    private int beds;
    private int occupied;

    public HostelRoom(String roomNo, int beds, int occupied) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = occupied;
    }

    public boolean allot() {
        if (occupied < beds) {
            occupied++;
            return true;
        }
        return false;
    }

    public String getRoomNo() {
        return roomNo;
    }
}

class SrmStudent {
    private String name;
    private String regNo;
    private HostelFeeAccount feeAccount; // Object composition
    private HostelRoom room;             // Object composition (null if unallotted)

    public static int totalStudents = 0;

    public SrmStudent(String name, String regNo, double totalFee) {
        this.name = name;
        this.regNo = regNo;
        this.feeAccount = new HostelFeeAccount(regNo, totalFee);
        this.room = null;
        totalStudents++;
    }

    public void assignRoom(HostelRoom targetRoom) {
        if (targetRoom != null && targetRoom.allot()) {
            this.room = targetRoom;
        }
    }

    public HostelFeeAccount getFeeAccount() {
        return feeAccount;
    }

    public String fullStatus() {
        String roomStr = (this.room != null) ? this.room.getRoomNo() : "unallotted";
        return String.format("%s | Due: Rs %.1f | Room: %s", 
                this.name, this.feeAccount.getDue(), roomStr);
    }
}

public class HostelMiniSystem {
    public static void main(String[] args) {
        // Setup rooms
        HostelRoom r1 = new HostelRoom("C-214", 3, 2); // 1 bed open
        HostelRoom r2 = new HostelRoom("C-507", 2, 1); // 1 bed open

        // Create 3 students
        SrmStudent s1 = new SrmStudent("Ravi", "RA01", 200000);
        SrmStudent s2 = new SrmStudent("Anitha", "RA02", 200000);
        SrmStudent s3 = new SrmStudent("Karthik", "RA03", 200000);

        // Process payments
        s1.getFeeAccount().payInTwoInstallments(60000); // Remaining: 140000
        s2.getFeeAccount().pay(20000);                  // Remaining: 180000
        s3.getFeeAccount().pay(-5000);                  // Rejected payment

        // Allot rooms to 2 students; leave Karthik unallotted
        s1.assignRoom(r1);
        s2.assignRoom(r2);

        // Print statuses
        System.out.println(s1.fullStatus());
        System.out.println(s2.fullStatus());
        System.out.println(s3.fullStatus());

        System.out.println("Total students: " + SrmStudent.totalStudents);
    }
}