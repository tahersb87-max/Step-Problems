class Employee {
    private int empId;
    private String name;
    private double salary;

    Employee(int empId, String name, double salary) {
        this.empId = empId;
        this.name = name;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int id, String name, double salary, double bonus) {
        super(id, name, salary);
        teamBonus = bonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class ParkingSlot {
    String slotNo;
    int capacity, occupiedCount;

    ParkingSlot(String slotNo, int capacity, int occupiedCount) {
        this.slotNo = slotNo;
        this.capacity = capacity;
        this.occupiedCount = occupiedCount;
    }

    boolean allot() {
        if (occupiedCount < capacity) {
            occupiedCount++;
            return true;
        }
        return false;
    }
}

class CompanyEmployeeRecord {
    String name;
    String empId;
    Employee employee;
    ParkingSlot slot;

    static int totalRecords = 0;

    CompanyEmployeeRecord(String name, String empId,
            Employee employee, ParkingSlot slot) {
        this.name = name;
        this.empId = empId;
        this.employee = employee;
        this.slot = slot;
        totalRecords++;
    }

    void fullProfile() {
        double pay;

        if (employee instanceof ManagerEmployee)
            pay = ((ManagerEmployee) employee).effectiveSalary();
        else
            pay = employee.getSalary();

        System.out.print(name + " | Pay: Rs " + pay + " | Slot: ");

        if (slot != null)
            System.out.println(slot.slotNo);
        else
            System.out.println("no parking assigned");
    }
}

public class stepweek35 {
    public static void main(String[] args) {
        Employee e1 = new ManagerEmployee(1, "Divya", 70000, 8000);
        Employee e2 = new Employee(2, "Karan", 40000);
        Employee e3 = new Employee(3, "Meera", 10000);

        ParkingSlot s1 = new ParkingSlot("A1", 1, 0);
        ParkingSlot s2 = new ParkingSlot("A2", 1, 0);

        s1.allot();
        s2.allot();

        CompanyEmployeeRecord r1 = new CompanyEmployeeRecord("Divya", "E101", e1, s1);

        CompanyEmployeeRecord r2 = new CompanyEmployeeRecord("Karan", "E102", e2, s2);

        CompanyEmployeeRecord r3 = new CompanyEmployeeRecord("Meera", "E103", e3, null);

        r1.fullProfile();
        r2.fullProfile();
        r3.fullProfile();

        System.out.println("Total records: " +
                CompanyEmployeeRecord.totalRecords);
    }
}