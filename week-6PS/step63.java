class EventTicket {
    protected double basePrice;
    protected double amountPaid;

    private double[] lateFeeHistory;
    private int lateFeeCount;

    public EventTicket(double basePrice) {
        this.basePrice = basePrice;
        this.amountPaid = 0;
        this.lateFeeHistory = new double[10];
        this.lateFeeCount = 0;
    }

    public void pay(double amount) {
        if (amount > 0) {
            amountPaid += amount;

            if (amountPaid > basePrice) {
                amountPaid = basePrice;
            }
        }
    }

    public double getBalanceDue() {
        return basePrice - amountPaid;
    }

    protected void applyLateFee(double amount) {
        basePrice += amount;

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

class WorkshopTicket extends EventTicket {

    public WorkshopTicket(double basePrice) {
        super(basePrice);
    }

    @Override
    protected void applyLateFee(double amount) {
        super.applyLateFee(amount * 2);
    }
}

public class step63 {
    public static void main(String[] args) {
        WorkshopTicket w = new WorkshopTicket(1200);

        w.pay(1200);
        w.applyLateFee(100);

        System.out.println(w.getBalanceDue());

        double[] history = w.getLateFeeHistory();

        for (double fee : history) {
            System.out.println(fee);
        }

        history[0] = 999;

        double[] updatedHistory = w.getLateFeeHistory();

        for (double fee : updatedHistory) {
            System.out.println(fee);
        }
    }
}