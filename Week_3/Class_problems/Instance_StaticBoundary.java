// ==========================================
// 1. BROKEN DESIGN: Demonstrates the bug
// ==========================================
class BrokenSrmStudent {
    /*
     * WHY MARKING THESE STATIC IS WRONG:
     * 1. 'name': Every student has their own name. A static name means the whole 
     *    university can hold only one student's name at a time.
     * 2. 'regNo': A registration number uniquely identifies one record. Static 
     *    sharing corrupts student identity.
     * 3. 'attendance': Attendance is individual. Making it static tracks only one 
     *    global percentage for all students.
     */
    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String n, String r, int a) {
        name = n;
        regNo = r;
        attendance = a;
    }
}

// ==========================================
// 2. FIXED DESIGN: Proper separation
// ==========================================
class FixedSrmStudent {
    // Instance fields: unique to every individual student
    private String name;
    private String regNo;
    private int attendance;

    // Static fields: shared across all instances
    private static final String UNIVERSITY = "SRM";
    private static int admissionCount = 10;

    public FixedSrmStudent(String name, int attendance) {
        this.name = name;
        this.attendance = attendance;
        admissionCount++;
        this.regNo = "RA2311003010" + admissionCount;
    }

    public void printIdCard() {
        System.out.println(this.name + " | " + this.regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + (admissionCount - 10));
    }
}

public class StaticBoundaryTest {
    public static void main(String[] args) {
        // Run broken version
        BrokenSrmStudent b1 = new BrokenSrmStudent("Ravi", "RA01", 82);
        BrokenSrmStudent b2 = new BrokenSrmStudent("Meera", "RA02", 74);

        System.out.println(BrokenSrmStudent.name);
        System.out.println(BrokenSrmStudent.name);
        System.out.println("(Ravi's data was overwritten — both students now show \"Meera\")\n");

        // Run fixed version
        FixedSrmStudent f1 = new FixedSrmStudent("Ravi", 82);
        FixedSrmStudent f2 = new FixedSrmStudent("Meera", 74);

        f1.printIdCard();
        f2.printIdCard();
        FixedSrmStudent.printTotalAdmissions();
    }
}