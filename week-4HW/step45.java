class DeliveryAccount {

    private String studentId;
    protected double orderValue;

    static String systemName;

    static {
        systemName = "Campus Delivery System";
    }

    public DeliveryAccount(String studentId, double orderValue) {
        this.studentId = studentId;
        this.orderValue = orderValue;
    }

    public DeliveryAccount(String studentId) {
        this(studentId, 0);
    }

    public final double calculateSurgeFee(int delayMinutes) {

        if (delayMinutes < 0) {
            throw new IllegalArgumentException("Delay cannot be negative.");
        }

        if (delayMinutes == 0) {
            return 0.0;
        }

        double surgePercent = 0.0;

        int firstTier = Math.min(delayMinutes, 5);
        surgePercent += firstTier * 0.5;

        if (delayMinutes > 5) {
            int secondTier = Math.min(delayMinutes - 5, 10);
            surgePercent += secondTier * 1.0;
        }

        if (delayMinutes > 15) {
            surgePercent += (delayMinutes - 15) * 2.0;
        }

        return orderValue * surgePercent / 100;
    }

    public String getStudentId() {
        return studentId;
    }
}

class PremiumDeliveryAccount extends DeliveryAccount {

    public PremiumDeliveryAccount(String studentId, double orderValue) {
        super(studentId, orderValue);
    }
}

public class step45 {

    static int processed = 0;
    static int nullSkipped = 0;
    static int premiumCount = 0;
    static int regularCount = 0;
    static double grandTotalSurgeFees = 0;

    public static void processAccount(
            DeliveryAccount account,
            double amount,
            int delayMinutes) {

        if (account == null) {
            return;
        }

        double surgeFee = account.calculateSurgeFee(delayMinutes);

        if (account instanceof PremiumDeliveryAccount) {
            premiumCount++;
            surgeFee = surgeFee * 0.5;
        } else {
            regularCount++;
        }

        processed++;
        grandTotalSurgeFees += surgeFee;
    }

    public static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        if (accounts == null ||
                amounts == null ||
                delayMinutesArray == null) {

            System.out.println("Invalid batch.");
            return;
        }

        if (accounts.length != amounts.length ||
                accounts.length != delayMinutesArray.length) {

            System.out.println("Array lengths do not match. Batch rejected.");
            return;
        }

        processed = 0;
        nullSkipped = 0;
        premiumCount = 0;
        regularCount = 0;
        grandTotalSurgeFees = 0;

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            processAccount(
                    accounts[i],
                    amounts[i],
                    delayMinutesArray[i]);
        }

        System.out.println(processed + " processed | "
                + nullSkipped + " null skipped | "
                + premiumCount + " premium | "
                + regularCount + " regular | "
                + "grand total surge fees = Rs "
                + grandTotalSurgeFees);
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
                new PremiumDeliveryAccount("STU001", 500),
                null,
                new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
                500, 400, 300
        };

        int[] delayMinutesArray = {
                10, 5, 0
        };

        processBatch(
                accounts,
                amounts,
                delayMinutesArray);
    }
}