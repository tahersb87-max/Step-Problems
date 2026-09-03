import java.util.Arrays;

public class step42 {

    private String tripId;
    private double totalFare;
    private int passengerCount;

    public step42(String tripId, double totalFare, int passengerCount) {

        if (totalFare < 0) {
            throw new IllegalArgumentException("Fare cannot be negative.");
        }

        if (passengerCount <= 0) {
            throw new IllegalArgumentException(
                    "Passenger count must be greater than zero.");
        }

        this.tripId = tripId;
        this.totalFare = totalFare;
        this.passengerCount = passengerCount;
    }

    public step42(String tripId, double totalFare) {
        this(tripId, totalFare, 2);
    }

    public step42(String tripId) {
        this(tripId, 0.0);
    }

    public double[] fareBreakdown() {

        double[] shares = new double[passengerCount];

        long totalPaisa = Math.round(totalFare * 100);

        long baseShare = totalPaisa / passengerCount;
        long remainder = totalPaisa % passengerCount;

        for (int i = 0; i < passengerCount; i++) {

            long share = baseShare;

            if (i >= passengerCount - remainder) {
                share++;
            }

            shares[i] = share / 100.0;
        }

        return shares;
    }

    public boolean isConfirmationOverdue(int confirmed, int expected) {
        return confirmed < expected;
    }

    public static void main(String[] args) {

        step42 splitter1 = new step42("TRIP001", 100000, 3);

        System.out.println(
                Arrays.toString(splitter1.fareBreakdown()));

        step42 splitter2 = new step42("TRIP003");

        System.out.println(
                Arrays.toString(splitter2.fareBreakdown()));
    }
}
