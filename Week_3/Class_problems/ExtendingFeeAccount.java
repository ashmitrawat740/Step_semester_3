// Base class: Untouched and unmodified
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
            System.out.println("Invalid payment amount: " + amount);
        }
    }

    public double getDue() {
        return Math.max(0.0, totalFee - amountPaid);
    }

    public String getRegNo() {
        return regNo;
    }
}

// Subclass 1: Two installments support
class HostelFeeAccount extends FeeAccount {
    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    public void payInTwoInstallments(double amount) {
        double installment = amount / 2.0;
        pay(installment);
        pay(installment);
    }
}

// Subclass 2: Scholarship percentage support
class ScholarshipFeeAccount extends FeeAccount {
    private double scholarshipPercent; // 0 to 100

    public ScholarshipFeeAccount(String regNo, double totalFee, double scholarshipPercent) {
        super(regNo, totalFee);
        this.scholarshipPercent = Math.min(100.0, Math.max(0.0, scholarshipPercent));
    }

    public double effectiveDue() {
        double rawDue = getDue();
        return rawDue - (rawDue * (scholarshipPercent / 100.0));
    }
}

public class FeeDemo {
    public static void main(String[] args) {
        FeeAccount plain = new FeeAccount("RA001", 150000);
        plain.pay(150000);

        HostelFeeAccount hostel = new HostelFeeAccount("RA002", 200000);
        hostel.payInTwoInstallments(60000);

        ScholarshipFeeAccount scholarship = new ScholarshipFeeAccount("RA003", 180000, 20.0);
        // Paid 0, 20% scholarship

        FeeAccount[] accounts = { plain, hostel, scholarship };

        for (FeeAccount acc : accounts) {
            if (acc instanceof ScholarshipFeeAccount) {
                ScholarshipFeeAccount sfa = (ScholarshipFeeAccount) acc;
                System.out.printf("Scholarship account effective due: Rs %.1f%n", sfa.effectiveDue());
            } else if (acc instanceof HostelFeeAccount) {
                System.out.printf("Hostel account due: Rs %.1f%n", acc.getDue());
            } else {
                System.out.printf("Plain account due: Rs %.1f%n", acc.getDue());
            }
        }
    }
}