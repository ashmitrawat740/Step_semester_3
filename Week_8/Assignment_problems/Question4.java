import java.util.*;

abstract class Student {
    private final String name;
    private final int maxCreditLimit;
    private int currentCredits;

    public Student(String name, int maxCreditLimit, int currentCredits) {
        this.name = name;
        this.maxCreditLimit = maxCreditLimit;
        this.currentCredits = currentCredits;
    }

    public String getName() { return name; }
    public int getMaxCreditLimit() { return maxCreditLimit; }
    public int getCurrentCredits() { return currentCredits; }

    public boolean canAddCredits(int credits) {
        return (currentCredits + credits) <= maxCreditLimit;
    }

    public void addCredits(int credits) {
        this.currentCredits += credits;
    }

    public void deductCredits(int credits) {
        this.currentCredits -= credits;
    }

    public abstract String getType();
}

class RegularStudent extends Student {
    public RegularStudent(String name, int currentCredits) {
        super(name, 24, currentCredits);
    }
    @Override public String getType() { return "Regular"; }
}

class HonorsStudent extends Student {
    public HonorsStudent(String name, int currentCredits) {
        super(name, 28, currentCredits);
    }
    @Override public String getType() { return "Honors"; }
}

class ExchangeStudent extends Student {
    public ExchangeStudent(String name, int currentCredits) {
        super(name, 20, currentCredits);
    }
    @Override public String getType() { return "Exchange"; }
}

class Elective {
    private final String courseName;
    private final int credits;
    private final int capacity;
    private final List<Student> enrolledStudents = new ArrayList<>();
    private final Queue<Student> waitlist = new LinkedList<>();

    public Elective(String courseName, int credits, int capacity) {
        this.courseName = courseName;
        this.credits = credits;
        this.capacity = capacity;
    }

    public String getCourseName() { return courseName; }

    public void enrollStudent(Student student) {
        if (enrolledStudents.contains(student) || waitlist.contains(student)) {
            System.out.println(student.getName() + " is already enrolled or on the waitlist.");
            return;
        }

        // 1. Check credit limit
        if (!student.canAddCredits(credits)) {
            System.out.printf("Enrollment failed: %s would exceed the %s credit limit (%d/%d).%n",
                student.getName(), student.getType(), student.getCurrentCredits() + credits, student.getMaxCreditLimit());
            return;
        }

        // 2. Check seat capacity
        if (enrolledStudents.size() < capacity) {
            enrolledStudents.add(student);
            student.addCredits(credits);
            System.out.printf("%s enrolled in %s (credits: %d/%d).%n",
                student.getName(), courseName, student.getCurrentCredits(), student.getMaxCreditLimit());
        } else {
            waitlist.add(student);
            System.out.printf("%s is full. %s added to waitlist (position %d).%n",
                courseName, student.getName(), waitlist.size());
        }
    }

    public void dropStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            System.out.println(student.getName() + " is not enrolled in " + courseName + ".");
            return;
        }

        enrolledStudents.remove(student);
        student.deductCredits(credits);
        System.out.printf("%s dropped %s (credits: %d/%d).%n",
            student.getName(), courseName, student.getCurrentCredits(), student.getMaxCreditLimit());

        // Process Waitlist Promotion
        promoteWaitlistedStudent();
    }

    private void promoteWaitlistedStudent() {
        while (!waitlist.isEmpty()) {
            Student nextStudent = waitlist.poll();
            if (nextStudent.canAddCredits(credits)) {
                enrolledStudents.add(nextStudent);
                nextStudent.addCredits(credits);
                System.out.printf("%s promoted from waitlist and enrolled in %s (credits: %d/%d).%n",
                    nextStudent.getName(), courseName, nextStudent.getCurrentCredits(), nextStudent.getMaxCreditLimit());
                break;
            } else {
                System.out.printf("Promotion failed for %s: Credit limit exceeded.%n", nextStudent.getName());
            }
        }
    }
}

public class Question4 {
    public static void main(String[] args) {
        // 1. Create Elective
        Elective cloud = new Elective("Cloud Computing", 4, 2);

        // Create Students
        Student asha = new RegularStudent("Asha", 20);
        Student ravi = new HonorsStudent("Ravi", 22);
        Student neha = new ExchangeStudent("Neha", 12);
        Student kiran = new RegularStudent("Kiran", 22);

        // 2-5. Enrollments
        cloud.enrollStudent(asha);
        cloud.enrollStudent(ravi);
        cloud.enrollStudent(neha);
        cloud.enrollStudent(kiran);

        // 6. Asha drops the elective
        cloud.dropStudent(asha);
    }
}