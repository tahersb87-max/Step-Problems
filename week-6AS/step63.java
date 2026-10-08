class RaceEntry {

    protected String bibNumber;
    protected double entryFee;
    protected double amountPaid;

    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount = 0;

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

    protected void applyLateFee(double amount) {

        entryFee += amount;

        if (lateFeeCount < 10) {
            lateFeeHistory[lateFeeCount] = amount;
            lateFeeCount++;
        }
    }

    public double[] getLateFeeHistory() {

        double[] copy = new double[lateFeeCount];

        for (int i = 0; i < lateFeeCount; i++) {
            copy[i] = lateFeeHistory[i];
        }

        return copy;
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

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class step63 {

    public static void main(String[] args) {

        RunnerEntry r = new RunnerEntry(
                "BIB2001",
                80,
                "Open 10K");

        r.pay(30);

        r.applyLateFee(20);

        System.out.println(
                r.getBalanceDue());

        double[] history = r.getLateFeeHistory();

        for (double fee : history) {
            System.out.print(fee + " ");
        }

        System.out.println();

        history[0] = 999;

        double[] history2 = r.getLateFeeHistory();

        for (double fee : history2) {
            System.out.print(fee + " ");
        }
    }
}