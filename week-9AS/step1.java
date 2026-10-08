import java.util.*;

public class step1 {

    static List<Long> footfallReport(int[] visitors, int[][] queries) {
        long[] prefix = new long[visitors.length + 1];

        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        List<Long> result = new ArrayList<>();

        for (int[] query : queries) {
            int start = query[0];
            int end = query[1];

            result.add(prefix[end + 1] - prefix[start]);
        }

        return result;
    }

    public static void main(String[] args) {
        int[] visitors = { 12, 7, 3, 9, 15, 4, 8 };

        int[][] queries = {
                { 0, 2 },
                { 2, 5 },
                { 4, 6 },
                { 3, 3 }
        };

        System.out.println(footfallReport(visitors, queries));
    }
}