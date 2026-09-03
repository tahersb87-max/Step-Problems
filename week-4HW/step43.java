class Canteen {

    private String canteenCode;
    private String canteenName;
    private int trustScore;

    public Canteen(String canteenCode, String canteenName, int trustScore) {
        this.canteenCode = canteenCode;
        this.canteenName = canteenName;
        this.trustScore = trustScore;
    }

    public Canteen(String canteenCode, String canteenName) {
        this(canteenCode, canteenName, 3);
    }

    public int compareTo(Canteen other) {

        if (this.trustScore != other.trustScore) {
            return other.trustScore - this.trustScore;
        }

        int codeComparison = this.canteenCode.compareToIgnoreCase(other.canteenCode);

        if (codeComparison != 0) {
            return codeComparison;
        }

        if (this.canteenName.length() != other.canteenName.length()) {
            return this.canteenName.length() - other.canteenName.length();
        }

        return this.canteenName.compareToIgnoreCase(other.canteenName);
    }

    public String getCanteenCode() {
        return canteenCode;
    }

    public static Canteen[] rankCanteens(Canteen[] canteens) {

        Canteen[] ranked = new Canteen[canteens.length];

        for (int i = 0; i < canteens.length; i++) {
            ranked[i] = canteens[i];
        }

        for (int i = 0; i < ranked.length - 1; i++) {

            for (int j = 0; j < ranked.length - i - 1; j++) {

                if (ranked[j].compareTo(ranked[j + 1]) > 0) {

                    Canteen temp = ranked[j];
                    ranked[j] = ranked[j + 1];
                    ranked[j + 1] = temp;
                }
            }
        }

        return ranked;
    }
}

public class step43 {

    public static void main(String[] args) {

        Canteen[] canteens = {
                new Canteen("HB3-C", "Spice Junction", 3),
                new Canteen("hb1-c", "Grand Mess", 5),
                new Canteen("HB2-C", "Southern Treats")
        };

        Canteen[] ranked = Canteen.rankCanteens(canteens);

        for (Canteen canteen : ranked) {
            System.out.println(canteen.getCanteenCode());
        }
    }
}