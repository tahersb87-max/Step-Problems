class SrmStudent {

    String name;
    String regNo;
    int attendance;

    public SrmStudent(String name, String regNo, int attendance) {
        this.name = name;
        this.regNo = regNo;
        this.attendance = attendance;
    }

    public void addAttendanceUpdate(int newAttendance) {
        attendance = newAttendance;
    }

    public boolean isEligible() {
        return attendance >= 75;
    }

    public static double classAverage(SrmStudent[] students) {

        int total = 0;

        for (SrmStudent student : students) {
            total += student.attendance;
        }

        return (double) total / students.length;
    }
}

public class step31 {

    public static void main(String[] args) {

        SrmStudent[] students = {
                new SrmStudent("Ravi", "REG101", 82),
                new SrmStudent("Anitha", "REG102", 68),
                new SrmStudent("Karthik", "REG103", 91),
                new SrmStudent("Meera", "REG104", 74),
                new SrmStudent("Suresh", "REG105", 60)
        };

        for (SrmStudent student : students) {

            String status;

            if (student.isEligible()) {
                status = "Eligible";
            } else {
                status = "Detained";
            }

            System.out.println(
                    student.name + " - " +
                            student.attendance + "% - " +
                            status);
        }

        double average = SrmStudent.classAverage(students);

        System.out.printf("Class average: %.1f%%%n", average);
    }
}