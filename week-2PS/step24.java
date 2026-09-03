import java.util.Scanner;

public class step24 {

    public static String maskPhoneNumber(String phone) {

        if (phone == null || !phone.matches("\\d{10}")) {
            return "Invalid phone number";
        }

        String lastFour = phone.substring(6);

        StringBuilder masked = new StringBuilder("XXXXXX" + lastFour);

        masked.insert(6, "-");

        return masked.toString();
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter phone number: ");
        String phone = scanner.nextLine();

        System.out.println(maskPhoneNumber(phone));

        scanner.close();
    }
}
