class RaceEntry {

    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private static int bibCounter = 0;

    private final String entryCode;

    public RaceEntry(
            String bibNumber,
            double entryFee) {

        if (bibNumber == null ||
                bibNumber.trim().length() < 4) {

            throw new IllegalArgumentException(
                    "Invalid bib number");
        }

        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.amountPaid = 0;

        bibCounter++;

        entryCode = "RACE-" + bibCounter;
    }

    public void pay(double amount) {

        if (amount > 0) {

            amountPaid += amount;

            if (amountPaid > entryFee) {
                amountPaid = entryFee;
            }
        }
    }

    public void pay(
            double amount,
            String mode) {

        pay(amount);

        System.out.println(
                "Paying via " + mode);
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String getEntryCode() {
        return entryCode;
    }

    public static boolean isValidDiscountCode(
            String code) {

        if (code == null ||
                code.length() != 5) {

            return false;
        }

        if (code.charAt(0) != 'M') {
            return false;
        }

        if (!Character.isDigit(code.charAt(1))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(2))) {
            return false;
        }

        if (!Character.isDigit(code.charAt(3))) {
            return false;
        }

        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }

        return true;
    }

    public static int getBibCounter() {
        return bibCounter;
    }

    public static String settleNight(
            RaceEntry[] entries) {

        int processed = 0;
        int skipped = 0;
        int relay = 0;
        int individual = 0;

        for (RaceEntry entry : entries) {

            if (entry == null) {
                skipped++;
                continue;
            }

            processed++;

            if (entry instanceof RelayTeamEntry) {
                relay++;
            } else {
                individual++;
            }
        }

        return processed +
                " processed | " +
                skipped +
                " null skipped | " +
                relay +
                " relay | " +
                individual +
                " individual";
    }
}

class RunnerEntry extends RaceEntry {

    private String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }
}

class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(
            String bibNumber,
            double entryFee,
            String category,
            double sponsorBonus) {

        super(
                bibNumber,
                entryFee,
                category);

        this.sponsorBonus = sponsorBonus;
    }
}

class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(
            String bibNumber,
            double entryFee,
            int teamSize) {

        super(bibNumber, entryFee);
        this.teamSize = teamSize;
    }
}

public class step65 {

    public static void main(String[] args) {

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M123A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "M12A"));

        System.out.println(
                RaceEntry.isValidDiscountCode(
                        "X123A"));

        RunnerEntry runner = new RunnerEntry(
                "BIB2001",
                80,
                "Open 10K");

        EliteRunnerEntry elite = new EliteRunnerEntry(
                "BIB3001",
                150,
                "Elite Full Marathon",
                500);

        RelayTeamEntry relay = new RelayTeamEntry(
                "BIB4001",
                300,
                4);

        runner.pay(10, "UPI");

        RaceEntry[] entries = {
                elite,
                null,
                relay
        };

        System.out.println(
                RaceEntry.settleNight(entries));

        System.out.println(
                RaceEntry.getBibCounter());
    }
}