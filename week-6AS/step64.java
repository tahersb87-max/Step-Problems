class RaceEntry {

    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    public RaceEntry(String bibNumber, double entryFee) {

        if (bibNumber == null || bibNumber.trim().length() < 4) {
            throw new IllegalArgumentException("Invalid bib number");
        }

        this.bibNumber = bibNumber.trim();
        this.entryFee = entryFee;
        this.amountPaid = 0;
    }

    public void pay(double amount) {

        if (amount > 0) {
            amountPaid += amount;

            if (amountPaid > entryFee) {
                amountPaid = entryFee;
            }
        }
    }

    public double getBalanceDue() {
        return entryFee - amountPaid;
    }

    public String announce() {
        return "Race Entry | Bib: " +
                bibNumber +
                " | Balance: " +
                getBalanceDue();
    }
}

class RunnerEntry extends RaceEntry {

    protected String category;

    public RunnerEntry(
            String bibNumber,
            double entryFee,
            String category) {

        super(bibNumber, entryFee);
        this.category = category;
    }

    @Override
    public String announce() {
        return "Runner Entry | Bib: " +
                bibNumber +
                " | Category: " +
                category +
                " | Balance: " +
                getBalanceDue();
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

    public int getTeamSize() {
        return teamSize;
    }

    @Override
    public String announce() {
        return "Relay Team | Bib: " +
                bibNumber +
                " | Team Size: " +
                teamSize +
                " | Balance: " +
                getBalanceDue();
    }
}

public class step64 {

    static String announceAll(RaceEntry[] entries) {

        StringBuilder result = new StringBuilder();

        for (RaceEntry entry : entries) {

            result.append(entry.announce());

            if (entry instanceof RelayTeamEntry) {

                RelayTeamEntry relay = (RelayTeamEntry) entry;

                result.append(
                        " [Team size via downcast: " +
                                relay.getTeamSize() +
                                "]");
            }

            result.append(" | ");
        }

        return result.toString();
    }

    public static void main(String[] args) {

        RunnerEntry runner = new RunnerEntry(
                "BIB2001",
                80,
                "Open 10K");

        runner.pay(30);

        RelayTeamEntry relay = new RelayTeamEntry(
                "BIB4001",
                300,
                4);

        RaceEntry[] fleet = {
                runner,
                relay
        };

        System.out.println(
                announceAll(fleet));
    }
}