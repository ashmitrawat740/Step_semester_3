class CompanyEmployeeRecord {
    private String name;
    private String empId;
    private Employee employee;
    private ParkingSlot slot;

    public static int totalRecords = 0;

    public CompanyEmployeeRecord(String name, String empId, Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    public double getCalculatedPay() {
        if (employee instanceof ManagerEmployee) {
            return ((ManagerEmployee) employee).effectiveSalary();
        } else if (employee instanceof InternEmployee) {
            return ((InternEmployee) employee).effectiveSalary();
        } else {
            return employee.getSalary();
        }
    }

    public String fullProfile() {
        String slotInfo = (slot != null) ? slot.getSlotNo() : "no parking assigned";
        return name + " | Pay: Rs " + getCalculatedPay() + " | Slot: " + slotInfo;
    }
}

public class F5_HRMiniSystem {
    public static void main(String[] args) {
        ParkingSlot slotA1 = new ParkingSlot("A1", 1, 0);
        ParkingSlot slotA2 = new ParkingSlot("A2", 1, 0);

        Employee divyaEmp = new ManagerEmployee("M101", "Divya", 70000, 8000);
        Employee karanEmp = new Employee("E102", "Karan", 40000);
        Employee meeraEmp = new InternEmployee("I103", "Meera", 12000, 10000);

        slotA1.allot("DIVYA_CAR");
        slotA2.allot("KARAN_CAR");

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "M101", divyaEmp, slotA1);
        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", karanEmp, slotA2);
        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "I103", meeraEmp, null);

        System.out.println(r1.fullProfile());
        System.out.println(r2.fullProfile());
        System.out.println(r3.fullProfile());
        System.out.println("Total records: " + CompanyEmployeeRecord.totalRecords);
    }
}