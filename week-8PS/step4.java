import java.time.LocalDate;
import java.util.*;

interface LeavePolicy {
    boolean isAllowed(int days);

    String getTypeName();
}

class FullTimePolicy implements LeavePolicy {
    public boolean isAllowed(int days) {
        return days <= 30;
    }

    public String getTypeName() {
        return "Full-time";
    }
}

class PartTimePolicy implements LeavePolicy {
    public boolean isAllowed(int days) {
        return days <= 15;
    }

    public String getTypeName() {
        return "Part-time";
    }
}

class ContractPolicy implements LeavePolicy {
    public boolean isAllowed(int days) {
        return days <= 10;
    }

    public String getTypeName() {
        return "Contract";
    }
}

class Employee {
    private String name;
    private LeavePolicy policy;

    public Employee(String name, LeavePolicy policy) {
        this.name = name;
        this.policy = policy;
    }

    public String getName() {
        return name;
    }

    public LeavePolicy getPolicy() {
        return policy;
    }
}

enum LeaveStatus {
    PENDING,
    APPROVED,
    REJECTED
}

class LeaveRequest {
    private Employee employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private LeaveStatus status;

    public LeaveRequest(Employee employee,
            LocalDate startDate,
            LocalDate endDate) {
        this.employee = employee;
        this.startDate = startDate;
        this.endDate = endDate;
        status = LeaveStatus.PENDING;
    }

    public Employee getEmployee() {
        return employee;
    }

    public LeaveStatus getStatus() {
        return status;
    }

    public boolean review(boolean approve) {
        if (status != LeaveStatus.PENDING) {
            return false;
        }

        status = approve
                ? LeaveStatus.APPROVED
                : LeaveStatus.REJECTED;

        return true;
    }
}

class LeaveManager {
    public LeaveRequest submit(Employee employee,
            LocalDate startDate,
            LocalDate endDate) {

        int days = (int) (endDate.toEpochDay()
                - startDate.toEpochDay()
                + 1);

        if (!employee.getPolicy().isAllowed(days)) {
            System.out.println(
                    "Leave request rejected: "
                            + employee.getName()
                            + " exceeds the "
                            + employee.getPolicy().getTypeName()
                            + " leave policy.");

            return null;
        }

        LeaveRequest request = new LeaveRequest(
                employee,
                startDate,
                endDate);

        System.out.println(
                "Leave request submitted by "
                        + employee.getName()
                        + " for "
                        + startDate
                        + " to "
                        + endDate
                        + ". Status: Pending.");

        return request;
    }

    public void approve(LeaveRequest request) {
        if (request.review(true)) {
            System.out.println(
                    "Leave request for "
                            + request.getEmployee().getName()
                            + " approved. Status: Approved.");
        } else {
            System.out.println(
                    "Cannot change status: Approved or Rejected "
                            + "request cannot revert to Pending.");
        }
    }

    public void reject(LeaveRequest request) {
        if (request.review(false)) {
            System.out.println(
                    "Leave request for "
                            + request.getEmployee().getName()
                            + " rejected. Status: Rejected.");
        } else {
            System.out.println(
                    "Cannot change status: Approved or Rejected "
                            + "request cannot revert to Pending.");
        }
    }
}

public class step4 {
    public static void main(String[] args) {
        Employee john = new Employee(
                "John Doe",
                new FullTimePolicy());

        Employee jane = new Employee(
                "Jane Smith",
                new PartTimePolicy());

        LeaveManager manager = new LeaveManager();

        LeaveRequest johnRequest = manager.submit(
                john,
                LocalDate.of(2024, 10, 10),
                LocalDate.of(2024, 10, 12));

        manager.approve(johnRequest);

        LeaveRequest janeRequest = manager.submit(
                jane,
                LocalDate.of(2024, 11, 1),
                LocalDate.of(2024, 11, 5));

        manager.approve(janeRequest);
    }
}