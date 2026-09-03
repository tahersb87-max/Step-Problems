import java.util.Scanner;

public class step22 {

    public static void parseStudentRecord(String csvLine) {

        String[] fields = csvLine.split(",");

        if (fields.length != 3) {
            System.out.println("Invalid Record");
            return;
        }

        System.out.println(
                "Name: " + fields[0].trim() +
                        " | Roll No: " + fields[1].trim() +
                        " | Dept: " + fields[2].trim());
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter CSV record: ");
        String csvLine = scanner.nextLine();

        parseStudentRecord(csvLine);

        scanner.close();
    }
}
