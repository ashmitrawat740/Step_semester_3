import java.time.LocalDate;

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

abstract class Employee {
    private final String id;
    private final String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String getEmployeeType();
}

class FullTimeEmployee extends Employee {
    public FullTimeEmployee(String id, String name) {
        super(id, name);
    }

    @Override
    public String getEmployeeType() {
        return "Full-time";
    }
}

class PartTimeEmployee extends Employee {
    public PartTimeEmployee(String id, String name) {
        super(id, name);
    }

    @Override
    public String getEmployeeType() {
        return "Part-time";
    }
}

class LeaveRequest {
    private final Employee employee;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee, LocalDate startDate, LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = LeaveStatus.PENDING;
        System.out.printf("Leave request submitted by %s for %s to %s. Status: %s.%n",
                employee.getName(), startDate, endDate, status);
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public void setStatus(LeaveStatus newStatus) {
        if (this.status != LeaveStatus.PENDING && newStatus == LeaveStatus.PENDING) {
            System.out.printf("Cannot change status: %s request cannot revert to Pending.%n",
                    this.status == LeaveStatus.APPROVED ? "Approved" : "Rejected");
            return;
        }
        this.status = newStatus;
        System.out.printf("Leave request for %s %s. Status: %s.%n",
                employee.getName(), newStatus.name().toLowerCase(), newStatus);
    }
}

class LeaveManager {
    public void approveRequest(LeaveRequest request) {
        request.setStatus(LeaveStatus.APPROVED);
    }

    public void rejectRequest(LeaveRequest request) {
        request.setStatus(LeaveStatus.REJECTED);
    }

    public void revertToPending(LeaveRequest request) {
        request.setStatus(LeaveStatus.PENDING);
    }
}

public class LeaveManagementSystem {
    public static void main(String[] args) {
        LeaveManager manager = new LeaveManager();

        Employee john = new FullTimeEmployee("E1", "John Doe");
        LeaveRequest req1 = new LeaveRequest(john, LocalDate.of(2024, 10, 10), LocalDate.of(2024, 10, 12));
        manager.approveRequest(req1);

        Employee jane = new PartTimeEmployee("E2", "Jane Smith");
        LeaveRequest req2 = new LeaveRequest(jane, LocalDate.of(2024, 11, 1), LocalDate.of(2024, 11, 5));

        manager.revertToPending(req1);
    }
}