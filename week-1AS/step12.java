import java.util.Scanner;

public class step12 {

    public static void checkTypingAccuracy(String original, String typed) {

        int matched = 0;
        int firstMismatch = -1;

        for (int i = 0; i < original.length(); i++) {

            if (original.charAt(i) == typed.charAt(i)) {
                matched++;
            } else if (firstMismatch == -1) {
                firstMismatch = i;
            }
        }

        double accuracy = ((double) matched / original.length()) * 100;

        System.out.println("\nMatched: " + matched + "/" + original.length());
        System.out.printf("Accuracy: %.2f%%%n", accuracy);

        if (firstMismatch == -1) {
            System.out.println("No Mismatches");
        } else {
            System.out.println(
                    "First Mismatch at position " + (firstMismatch + 1) +
                            " ('" + original.charAt(firstMismatch) +
                            "' vs '" + typed.charAt(firstMismatch) + "')");
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the original text: ");
        String original = scanner.nextLine();

        System.out.print("Enter the typed text: ");
        String typed = scanner.nextLine();

        if (original.length() != typed.length()) {
            System.out.println("Both strings must have equal length.");
        } else {
            checkTypingAccuracy(original, typed);
        }

        scanner.close();
    }
}