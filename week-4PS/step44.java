public final class step44 {

    private final double minimumPenaltyPercent;

    public step44(double minimumPenaltyPercent) {
        this.minimumPenaltyPercent = minimumPenaltyPercent;
    }

    public final double calculatePenalty(double ticketFare, int minutesLate) {

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
            int thirdTier = minutesLate - 15;
            penaltyPercent += thirdTier * 2.0;
        }

        double tieredPenalty = ticketFare * penaltyPercent / 100;

        double minimumPenalty = ticketFare * minimumPenaltyPercent / 100;

        return Math.max(tieredPenalty, minimumPenalty);
    }

    public static void main(String[] args) {

        step44 calculator = new step44(1.0);

        System.out.println(
                "Rs " + calculator.calculatePenalty(1000, 0));

        System.out.println(
                "Rs " + calculator.calculatePenalty(1000, 1));

        System.out.println(
                "Rs " + calculator.calculatePenalty(1000, 16));
    }
}