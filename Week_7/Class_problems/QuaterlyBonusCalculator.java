abstract class StaffMember {

    private double baseSalary;
    protected double bonusRate;

    // Constructor 1
    public StaffMember(double baseSalary) {
        this(baseSalary, 0.10);
    }

    // Constructor 2
    public StaffMember(
            double baseSalary,
            double bonusRate) {

        this.baseSalary = baseSalary;
        this.bonusRate = bonusRate;
    }

    public abstract double calculateBonus();

    // JavaBean getter
    public double getSalary() {
        return baseSalary;
    }

    // JavaBean setter
    public void setSalary(double baseSalary) {

        if (baseSalary >= 0) {
            this.baseSalary = baseSalary;
        } else {
            System.out.println(
                    "Invalid salary. Salary unchanged."
            );
        }
    }
}

interface Auditable {
    String auditRecord();
}

class TeamLead extends StaffMember
        implements Auditable {

    private int teamSize;

    // Constructor using default bonus rate
    public TeamLead(
            double baseSalary,
            int teamSize) {

        super(baseSalary);
        this.teamSize = teamSize;
    }

    // Constructor with explicit bonus rate
    public TeamLead(
            double baseSalary,
            double bonusRate,
            int teamSize) {

        super(baseSalary, bonusRate);
        this.teamSize = teamSize;
    }

    @Override
    public double calculateBonus() {
        return getSalary() * bonusRate;
    }

    @Override
    public String auditRecord() {

        return "TeamLead audit: "
                + teamSize
                + " team members, salary $"
                + getSalary();
    }
}

public class Main {

    static String getAuditIfApplicable(
            StaffMember s) {

        if (s instanceof Auditable) {

            Auditable a =
                    (Auditable) s;

            return a.auditRecord();
        }

        return "No audit required";
    }

    public static void main(String[] args) {

        TeamLead t =
                new TeamLead(60000, 5);

        System.out.println(
                t.calculateBonus()
        );

        TeamLead t2 =
                new TeamLead(
                        60000,
                        0.20,
                        5
                );

        System.out.println(
                t2.calculateBonus()
        );

        t.setSalary(-5000);

        // Upcasting
        StaffMember ref = t;

        System.out.println(
                getAuditIfApplicable(ref)
        );
    }
}