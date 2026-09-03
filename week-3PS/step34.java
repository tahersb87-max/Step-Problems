class BrokenSrmStudent {

    static String name;
    static String regNo;
    static int attendance;

    public BrokenSrmStudent(String name, String regNo, int attendance) {
        BrokenSrmStudent.name = name;
        BrokenSrmStudent.regNo = regNo;
        BrokenSrmStudent.attendance = attendance;
    }

    public void printStudent() {
        System.out.println(name);
    }
}

class SrmStudent {

    String name;
    String regNo;
    int attendance;

    static String university = "SRM University";
    static int admissionCount = 0;

    public SrmStudent(String name, int attendance) {

        this.name = name;
        this.attendance = attendance;

        admissionCount++;

        this.regNo = "RA2311003010" + (10 + admissionCount);
    }

    public void printIdCard() {
        System.out.println(name + " | " + regNo);
    }

    public static void printTotalAdmissions() {
        System.out.println("Students admitted so far: " + admissionCount);
    }
}

public class step34 {

    public static void main(String[] args) {

        System.out.println("Broken version:");

        BrokenSrmStudent student1 = new BrokenSrmStudent("Ravi", "RA231100301011", 82);

        BrokenSrmStudent student2 = new BrokenSrmStudent("Meera", "RA231100301012", 90);

        student1.printStudent();
        student2.printStudent();

        System.out.println("\nFixed version:");

        SrmStudent fixedStudent1 = new SrmStudent("Ravi", 82);
        SrmStudent fixedStudent2 = new SrmStudent("Meera", 90);

        fixedStudent1.printIdCard();
        fixedStudent2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}