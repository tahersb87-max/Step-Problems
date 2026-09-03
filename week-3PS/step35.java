class FeeAccount {

    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment rejected: amount must be positive.");
        }
    }

    public double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    public HostelFeeAccount(String regNo, double totalFee) {
        super(regNo, totalFee);
    }

    public void payInTwoInstallments(double amount) {
        if (amount > 0) {
            pay(amount / 2);
            pay(amount / 2);
        } else {
            pay(amount);
        }
    }
}

class HostelRoom {

    String roomNo;
    int beds;
    int occupied;

    public HostelRoom(String roomNo, int beds) {
        this.roomNo = roomNo;
        this.beds = beds;
        this.occupied = 0;
    }

    public boolean allot(String name) {
        if (occupied < beds) {
            occupied++;
            return true;
        }

        return false;
    }

    public static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }
}

class SrmStudent {

    String name;
    String regNo;
    HostelFeeAccount feeAccount;
    HostelRoom room;

    static int totalStudents = 0;

    public SrmStudent(String name, String regNo,
            HostelFeeAccount feeAccount) {

        this.name = name;
        this.regNo = regNo;
        this.feeAccount = feeAccount;
        this.room = null;

        totalStudents++;
    }

    public void assignRoom(HostelRoom room) {

        if (room != null && room.allot(name)) {
            this.room = room;
        }
    }

    public String fullStatus() {

        String roomStatus;

        if (room == null) {
            roomStatus = "unallotted";
        } else {
            roomStatus = room.roomNo;
        }

        return name +
                " | Due: Rs " + feeAccount.getDue() +
                " | Room: " + roomStatus;
    }
}

public class step35 {

    public static void main(String[] args) {

        HostelRoom[] rooms = {
                new HostelRoom("C-214", 1),
                new HostelRoom("C-507", 1)
        };

        HostelFeeAccount fee1 = new HostelFeeAccount("RA101", 200000);

        HostelFeeAccount fee2 = new HostelFeeAccount("RA102", 200000);

        HostelFeeAccount fee3 = new HostelFeeAccount("RA103", 200000);

        SrmStudent student1 = new SrmStudent("Ravi", "RA101", fee1);

        SrmStudent student2 = new SrmStudent("Anitha", "RA102", fee2);

        SrmStudent student3 = new SrmStudent("Karthik", "RA103", fee3);

        student1.assignRoom(
                HostelRoom.findAvailableRoom(rooms));

        student2.assignRoom(
                HostelRoom.findAvailableRoom(rooms));

        fee1.pay(60000);
        fee2.pay(20000);
        fee2.pay(-5000);

        System.out.println(student1.fullStatus());
        System.out.println(student2.fullStatus());
        System.out.println(student3.fullStatus());

        System.out.println("Total students: " +
                SrmStudent.totalStudents);
    }
}