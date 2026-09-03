class BusTicketAccount {

    private String bookingId;
    protected double ticketFare;

    static String depotName;

    static {
        depotName = "SRM Bus Depot";
    }

    public BusTicketAccount(String bookingId, double ticketFare) {
        this.bookingId = bookingId;
        this.ticketFare = ticketFare;
    }

    public BusTicketAccount(String bookingId) {
        this(bookingId, 0.0);
    }

    public final double calculatePenalty(int minutesLate) {

        if (ticketFare < 0 || minutesLate < 0) {
            throw new IllegalArgumentException(
                    "Ticket fare and minutes late cannot be negative.");
        }

        if (minutesLate == 0) {
            return 0.0;
        }

        double penaltyPercent = 0.0;

        int firstTier = Math.min(minutesLate, 5);
        penaltyPercent += firstTier * 0.5;

        if (minutesLate > 5) {
            int secondTier = Math.min(minutesLate - 5, 10);
            penaltyPercent += secondTier * 1.0;
        }

        if (minutesLate > 15) {
            penaltyPercent += (minutesLate - 15) * 2.0;
        }

        return ticketFare * penaltyPercent / 100;
    }

    public String getBookingId() {
        return bookingId;
    }
}

class SleeperBusTicketAccount extends BusTicketAccount {

    public SleeperBusTicketAccount(
            String bookingId,
            double ticketFare) {

        super(bookingId, ticketFare);
    }
}

public class step45 {

    static int processed;
    static int nullSkipped;
    static int sleeperCount;
    static int regularCount;
    static double grandTotalPenalties;

    public static void processAccount(
            BusTicketAccount account,
            double amount,
            int minutesLate) {

        if (account == null) {
            return;
        }

        double penalty = account.calculatePenalty(minutesLate);

        if (account instanceof SleeperBusTicketAccount) {
            sleeperCount++;
            penalty = penalty * 0.5;
        } else {
            regularCount++;
        }

        processed++;
        grandTotalPenalties += penalty;
    }

    public static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts == null ||
                amounts == null ||
                minutesLateArray == null) {

            System.out.println("Invalid batch.");
            return;
        }

        if (accounts.length != amounts.length ||
                accounts.length != minutesLateArray.length) {

            System.out.println(
                    "Array lengths do not match. Batch rejected.");
            return;
        }

        processed = 0;
        nullSkipped = 0;
        sleeperCount = 0;
        regularCount = 0;
        grandTotalPenalties = 0;

        for (int i = 0; i < accounts.length; i++) {

            if (accounts[i] == null) {
                nullSkipped++;
                continue;
            }

            try {
                processAccount(
                        accounts[i],
                        amounts[i],
                        minutesLateArray[i]);
            } catch (IllegalArgumentException e) {
                System.out.println(
                        "Skipped invalid account: "
                                + accounts[i].getBookingId());
            }
        }

        System.out.println(
                processed + " processed | "
                        + nullSkipped + " null skipped | "
                        + sleeperCount + " sleeper | "
                        + regularCount + " regular | "
                        + "grand total penalties = Rs "
                        + grandTotalPenalties);
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
                new SleeperBusTicketAccount("BK001", 2000),
                null,
                new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
                1200, 900, 700
        };

        int[] minutesLateArray = {
                10, 5, 0
        };

        processBatch(
                accounts,
                amounts,
                minutesLateArray);
    }
}
