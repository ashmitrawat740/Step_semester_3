public class SrmStudent {
    private String name;
    private String regNo;
    private int attendance;

    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        if (newAttendance >= 0 && newAttendance <= 100) {
            this.attendance = newAttendance;
        }
    }

    // Instance method: operates on this specific student's state
    public boolean isEligible() {
        return this.attendance >= 75;
    }

    public String getName() {
        return name;
    }

    public int getAttendance() {
        return attendance;
    }

    /*
     * JUSTIFICATION:
     * 'classAverage' is declared static because calculating an average across a group 
     * is an aggregate operation that belongs to the class as a whole, not to any single student. 
     * It does not need or use an implicit 'this' reference.
     * Conversely, 'isEligible()' must be an instance method because eligibility is an individual 
     * property that evaluates a specific student's attendance.
     */
    public static double classAverage(SrmStudent[] students) {
        if (students == null || students.length == 0) return 0.0;
        int sum = 0;
        for (SrmStudent student : students) {
            sum += student.getAttendance();
        }
        return (double) sum / students.length;
    }

    public static void main(String[] args) {
        SrmStudent[] students = {
            new SrmStudent("Ravi", "RA01", 82),
            new SrmStudent("Anitha", "RA02", 68),
            new SrmStudent("Karthik", "RA03", 91),
            new SrmStudent("Meera", "RA04", 74),
            new SrmStudent("Suresh", "RA05", 60)
        };

        for (SrmStudent s : students) {
            String status = s.isEligible() ? "Eligible" : "Detained";
            System.out.printf("%s - %d%% - %s%n", s.getName(), s.getAttendance(), status);
        }

        double avg = SrmStudent.classAverage(students);
        System.out.printf("Class average: %.1f%%%n", avg);
    }
}