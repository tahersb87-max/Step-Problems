class Employee {
    private int empId;
    private String empName;
    private double salary;

    Employee(int empId, String empName, double salary) {
        this.empId = empId;
        this.empName = empName;
        this.salary = salary;
    }

    double getSalary() {
        return salary;
    }
}

class ManagerEmployee extends Employee {
    private double teamBonus;

    ManagerEmployee(int empId, String empName, double salary, double teamBonus) {
        super(empId, empName, salary);
        this.teamBonus = teamBonus;
    }

    double effectiveSalary() {
        return getSalary() + teamBonus;
    }
}

class InternEmployee extends Employee {
    private double stipendCap;

    InternEmployee(int empId, String empName, double salary, double stipendCap) {
        super(empId, empName, salary);
        this.stipendCap = stipendCap;
    }

    double effectiveSalary() {
        if (getSalary() < stipendCap)
            return getSalary();
        else
            return stipendCap;
    }
}

public class stepweek32 {
    public static void main(String[] args) {
        Employee e1 = new Employee(1, "A", 40000);
        Employee e2 = new ManagerEmployee(2, "B", 70000, 8000);
        Employee e3 = new InternEmployee(3, "C", 12000, 10000);

        Employee[] employees = { e1, e2, e3 };

        for (Employee e : employees) {
            if (e instanceof ManagerEmployee) {
                ManagerEmployee m = (ManagerEmployee) e;
                System.out.println("Manager effective pay: Rs " +
                        m.effectiveSalary());
            } else if (e instanceof InternEmployee) {
                InternEmployee i = (InternEmployee) e;
                System.out.println("Intern effective pay: Rs " +
                        i.effectiveSalary());
            } else {
                System.out.println("Plain employee pay: Rs " +
                        e.getSalary());
            }
        }
    }
}