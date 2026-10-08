public class step5 {

    static int maxSumSubarray(int[] sales, int k) {

        int windowSum = 0;

        // Calculate first window
        for (int i = 0; i < k; i++) {
            windowSum += sales[i];
        }

        int maxSum = windowSum;

        // Slide the window
        for (int i = k; i < sales.length; i++) {
            windowSum += sales[i];
            windowSum -= sales[i - k];

            maxSum = Math.max(maxSum, windowSum);
        }

        return maxSum;
    }

    public static void main(String[] args) {

        int[] sales = { 2, 1, 5, 1, 3, 2 };
        int k = 3;

        System.out.println("Maximum Sum = " + maxSumSubarray(sales, k));
    }
}