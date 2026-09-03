class FeeAccount {

    private String regNo;
    private double totalFee;
    private double amountPaid;

    public FeeAccount(String regNo, double totalFee, double amountPaid) {
        this.regNo = regNo;
        this.totalFee = totalFee;
        this.amountPaid = amountPaid;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;
        } else {
            System.out.println("Payment must be positive.");
        }
    }

    public double getDue() {
        return totalFee - amountPaid;
    }
}

class HostelFeeAccount extends FeeAccount {

    public HostelFeeAccount(String regNo, double totalFee, double amountPaid) {
        super(regNo, totalFee, amountPaid);
    }

    public void payInTwoInstallments(double amount) {
        pay(amount / 2);
        pay(amount / 2);
    }
}

class ScholarshipFeeAccount extends FeeAccount {

    private double scholarshipPercent;

    public ScholarshipFeeAccount(String regNo, double totalFee,
            double amountPaid, double scholarshipPercent) {

        super(regNo, totalFee, amountPaid);

        if (scholarshipPercent >= 0 && scholarshipPercent <= 100) {
            this.scholarshipPercent = scholarshipPercent;
        } else {
            this.scholarshipPercent = 0;
        }
    }

    public double effectiveDue() {
        return getDue() * (1 - scholarshipPercent / 100);
    }
}

public class step32 {

    public static void main(String[] args) {

        FeeAccount plain = new FeeAccount("REG101", 150000, 0);
        plain.pay(150000);

        FeeAccount hostel = new HostelFeeAccount("REG102", 200000, 0);

        if (hostel instanceof HostelFeeAccount) {
            ((HostelFeeAccount) hostel).payInTwoInstallments(60000);
        }

        FeeAccount scholarship = new ScholarshipFeeAccount("REG103", 180000, 0, 20);

        System.out.println("Plain account due: Rs " + plain.getDue());

        System.out.println("Hostel account due: Rs " + hostel.getDue());

        if (scholarship instanceof ScholarshipFeeAccount) {
            System.out.println(
                    "Scholarship account effective due: Rs " +
                            ((ScholarshipFeeAccount) scholarship).effectiveDue());
        }
    }
}