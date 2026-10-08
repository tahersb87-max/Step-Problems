public class step2 {

    static int[] longestStreak(int[] costs, long budget) {
        int left = 0;
        long sum = 0;

        int bestLength = 0;
        int bestStart = -1;

        for (int right = 0; right < costs.length; right++) {
            sum += costs[right];

            while (sum > budget && left <= right) {
                sum -= costs[left];
                left++;
            }

            int currentLength = right - left + 1;

            if (currentLength > bestLength) {
                bestLength = currentLength;
                bestStart = left;
            }
        }

        return new int[] { bestLength, bestStart };
    }

    public static void main(String[] args) {
        int[] costs = { 4, 2, 1, 7, 3, 1, 2, 1, 5 };

        int[] result = longestStreak(costs, 8);

        System.out.println("(" + result[0] + ", " + result[1] + ")");
    }
}
