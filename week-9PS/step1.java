public class step1 {

    static String pairSumSorted(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                return "(" + nums[left] + ", " + nums[right] + ")";
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }

        return "Not Found";
    }

    public static void main(String[] args) {
        int[] nums = { -4, -1, 0, 3, 5, 9 };

        System.out.println(pairSumSorted(nums, 4));
        System.out.println(pairSumSorted(new int[] { 1, 2, 3 }, 100));
    }
}