public class step14 {

    public static void analyzeInventory(int[] sectionA, int[] sectionB) {

        if (sectionA.length != sectionB.length) {
            System.out.println("Both sections must have equal number of items.");
            return;
        }

        int totalA = 0;
        int totalB = 0;

        int highestQuantity = sectionA[0];
        String highestSection = "Section A";
        int highestIndex = 0;

        for (int i = 0; i < sectionA.length; i++) {

            totalA += sectionA[i];
            totalB += sectionB[i];

            if (sectionA[i] > highestQuantity) {
                highestQuantity = sectionA[i];
                highestSection = "Section A";
                highestIndex = i;
            }

            if (sectionB[i] > highestQuantity) {
                highestQuantity = sectionB[i];
                highestSection = "Section B";
                highestIndex = i;
            }
        }

        String status;

        if (totalA == totalB) {
            status = "Balanced";
        } else {
            status = "Not Balanced";
        }

        System.out.println("Section A Total: " + totalA);
        System.out.println("Section B Total: " + totalB);
        System.out.println("Status: " + status);

        System.out.println(
                "Highest Quantity: " + highestQuantity +
                        " (" + highestSection +
                        ", Item " + (highestIndex + 1) + ")");
    }

    public static void main(String[] args) {

        int[] sectionA = { 20, 15, 30 };
        int[] sectionB = { 25, 10, 30 };

        analyzeInventory(sectionA, sectionB);
    }
}