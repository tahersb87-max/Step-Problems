import java.util.Scanner;

public class step25 {

    public static String normalizeReference(String raw) {

        String reference = raw.trim();

        if (reference.length() < 3) {
            return reference.toUpperCase();
        }

        return reference.substring(0, 3).toUpperCase()
                + reference.substring(3);
    }

    public static String validateAndFormat(String reference) {

        if (reference.length() != 14) {
            return "Invalid: wrong length";
        }

        for (int i = 0; i < 3; i++) {
            if (!Character.isLetter(reference.charAt(i))) {
                return "Invalid: bank code must be 3 letters";
            }
        }

        for (int i = 3; i < reference.length(); i++) {
            if (!Character.isDigit(reference.charAt(i))) {
                return "Invalid: body must contain only digits";
            }
        }

        String bankCode = reference.substring(0, 3);
        String date = reference.substring(3, 9);
        String sequence = reference.substring(9, 14);

        StringBuilder formatted = new StringBuilder();

        formatted.append("[");
        formatted.append(bankCode);
        formatted.append("] DATE: ");
        formatted.append(date.substring(0, 2));
        formatted.append("/");
        formatted.append(date.substring(2, 4));
        formatted.append("/");
        formatted.append(date.substring(4, 6));
        formatted.append(" | SEQ: ");
        formatted.append(sequence);

        return formatted.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter transaction reference: ");

        String raw = scanner.nextLine();

        String normalized = normalizeReference(raw);

        System.out.println(
                validateAndFormat(normalized));

        scanner.close();
    }
}
