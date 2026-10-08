import java.util.*;

interface CreditPolicy {
    int getCreditLimit();

    String getTypeName();
}

class RegularPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 24;
    }

    public String getTypeName() {
        return "Regular";
    }
}

class HonorsPolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 28;
    }

    public String getTypeName() {
        return "Honors";
    }
}

class ExchangePolicy implements CreditPolicy {
    public int getCreditLimit() {
        return 20;
    }

    public String getTypeName() {
        return "Exchange";
    }
}

class Student {
    private String name;
    private int currentCredits;
    private CreditPolicy policy;

    public Student(String name, int currentCredits,
            CreditPolicy policy) {
        this.name = name;
        this.currentCredits = currentCredits;
        this.policy = policy;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCredits() {
        return currentCredits;
    }

    public int getCreditLimit() {
        return policy.getCreditLimit();
    }

    public String getTypeName() {
        return policy.getTypeName();
    }

    public boolean canAddCredits(int credits) {
        return currentCredits + credits <= getCreditLimit();
    }

    public void addCredits(int credits) {
        currentCredits += credits;
    }

    public void removeCredits(int credits) {
        currentCredits -= credits;
    }
}

class Enrollment {
    private Student student;
    private Elective elective;

    public Enrollment(Student student, Elective elective) {
        this.student = student;
        this.elective = elective;
    }

    public Student getStudent() {
        return student;
    }

    public Elective getElective() {
        return elective;
    }
}

class Elective {
    private String name;
    private int credits;
    private int capacity;

    private List<Enrollment> enrollments;
    private Queue<Student> waitlist;

    public Elective(String name, int credits, int capacity) {
        this.name = name;
        this.credits = credits;
        this.capacity = capacity;
        enrollments = new ArrayList<>();
        waitlist = new LinkedList<>();
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public boolean isEnrolled(Student student) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent() == student) {
                return true;
            }
        }

        return false;
    }

    public boolean isWaiting(Student student) {
        return waitlist.contains(student);
    }

    public boolean enroll(Student student) {
        if (isEnrolled(student) || isWaiting(student)) {
            return false;
        }

        if (!student.canAddCredits(credits)) {
            System.out.println("Enrollment failed: "
                    + student.getName()
                    + " would exceed the "
                    + student.getTypeName()
                    + " credit limit ("
                    + (student.getCurrentCredits() + credits)
                    + "/"
                    + student.getCreditLimit()
                    + ").");

            return false;
        }

        if (enrollments.size() >= capacity) {
            System.out.println(name + " is full.");

            waitlist.add(student);

            System.out.println(student.getName()
                    + " added to waitlist (position "
                    + waitlist.size() + ").");

            return false;
        }

        Enrollment enrollment = new Enrollment(student, this);

        enrollments.add(enrollment);
        student.addCredits(credits);

        System.out.println(student.getName()
                + " enrolled in " + name
                + " (credits: "
                + student.getCurrentCredits()
                + "/"
                + student.getCreditLimit()
                + ").");

        return true;
    }

    public boolean drop(Student student) {
        Enrollment found = null;

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent() == student) {
                found = enrollment;
                break;
            }
        }

        if (found == null) {
            return false;
        }

        enrollments.remove(found);
        student.removeCredits(credits);

        System.out.println(student.getName()
                + " dropped " + name
                + " (credits: "
                + student.getCurrentCredits()
                + "/"
                + student.getCreditLimit()
                + ").");

        promoteNext();

        return true;
    }

    private void promoteNext() {
        while (!waitlist.isEmpty()
                && enrollments.size() < capacity) {

            Student student = waitlist.poll();

            if (student.canAddCredits(credits)) {
                Enrollment enrollment = new Enrollment(student, this);

                enrollments.add(enrollment);
                student.addCredits(credits);

                System.out.println(student.getName()
                        + " promoted from waitlist and enrolled in "
                        + name
                        + " (credits: "
                        + student.getCurrentCredits()
                        + "/"
                        + student.getCreditLimit()
                        + ").");

                return;
            }
        }
    }
}

class EnrollmentService {
    public void enroll(Student student, Elective elective) {
        elective.enroll(student);
    }

    public void drop(Student student, Elective elective) {
        elective.drop(student);
    }
}

public class step4 {
    public static void main(String[] args) {
        Elective cloud = new Elective("Cloud Computing", 4, 2);

        Student asha = new Student("Asha", 20, new RegularPolicy());

        Student ravi = new Student("Ravi", 22, new HonorsPolicy());

        Student neha = new Student("Neha", 12, new ExchangePolicy());

        Student kiran = new Student("Kiran", 22, new RegularPolicy());

        EnrollmentService service = new EnrollmentService();

        service.enroll(asha, cloud);
        service.enroll(ravi, cloud);
        service.enroll(neha, cloud);
        service.enroll(kiran, cloud);

        service.drop(asha, cloud);
    }
}