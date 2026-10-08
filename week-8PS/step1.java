import java.util.*;

interface Question {
    boolean isCorrect(String answer);

    String getQuestionText();
}

class MultipleChoiceQuestion implements Question {
    private String questionText;
    private String correctAnswer;

    public MultipleChoiceQuestion(String questionText, String correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return correctAnswer.equalsIgnoreCase(answer);
    }

    public String getQuestionText() {
        return questionText;
    }
}

class TrueFalseQuestion implements Question {
    private String questionText;
    private boolean correctAnswer;

    public TrueFalseQuestion(String questionText, boolean correctAnswer) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
    }

    public boolean isCorrect(String answer) {
        return Boolean.parseBoolean(answer) == correctAnswer;
    }

    public String getQuestionText() {
        return questionText;
    }
}

class Student {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Attempt {
    private Student student;
    private Examination examination;
    private Map<Integer, String> answers;
    private boolean submitted;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        answers = new HashMap<>();
        submitted = false;
    }

    public void answerQuestion(int questionNumber, String answer) {
        if (submitted) {
            System.out.println("Cannot change answers after submission.");
            return;
        }

        answers.put(questionNumber, answer);
        System.out.println("Question " + questionNumber
                + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (submitted) {
            return;
        }

        submitted = true;

        System.out.println("Examination '" + examination.getName()
                + "' submitted successfully.");

        evaluate();
    }

    private void evaluate() {
        int correct = 0;

        for (int i = 0; i < examination.getQuestions().size(); i++) {
            Question question = examination.getQuestions().get(i);
            String answer = answers.get(i + 1);

            if (answer != null && question.isCorrect(answer)) {
                correct++;
            }
        }

        System.out.println("Result for '" + examination.getName()
                + "' attempt: " + correct + "/"
                + examination.getQuestions().size() + " correct");
    }
}

class Examination {
    private String name;
    private List<Question> questions;
    private Set<Student> submittedStudents;

    public Examination(String name) {
        this.name = name;
        questions = new ArrayList<>();
        submittedStudents = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

    public Attempt startAttempt(Student student) {
        if (submittedStudents.contains(student)) {
            System.out.println("Student already has a submitted attempt.");
            return null;
        }

        System.out.println("Examination '" + name
                + "' started by " + student.getName() + ".");

        return new Attempt(student, this);
    }

    public void markSubmitted(Student student) {
        submittedStudents.add(student);
    }
}

public class step1 {
    public static void main(String[] args) {
        Student student = new Student("Asha");

        Examination exam = new Examination("Math Quiz");

        exam.addQuestion(
                new MultipleChoiceQuestion(
                        "2 + 2 = ?",
                        "A"));

        exam.addQuestion(
                new MultipleChoiceQuestion(
                        "Capital of France?",
                        "B"));

        Attempt attempt = exam.startAttempt(student);

        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");

        attempt.submit();
    }
}