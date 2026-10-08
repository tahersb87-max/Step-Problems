import java.util.*;

interface ScoringRule {
    double calculate(double idea, double execution, double presentation);

    String getTrackName();
}

class InnovationScoringRule implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return idea * 0.50 + execution * 0.30 + presentation * 0.20;
    }

    public String getTrackName() {
        return "Innovation";
    }
}

class OpenScoringRule implements ScoringRule {
    public double calculate(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }

    public String getTrackName() {
        return "Open";
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

class Judge {
    private String name;

    public Judge(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Score {
    private double idea;
    private double execution;
    private double presentation;

    public Score(double idea, double execution, double presentation) {
        this.idea = idea;
        this.execution = execution;
        this.presentation = presentation;
    }

    public double calculate(ScoringRule rule) {
        return rule.calculate(idea, execution, presentation);
    }

    public void setIdea(double idea) {
        this.idea = idea;
    }
}

class Project {
    private String name;
    private Team team;
    private Score score;

    public Project(String name, Team team) {
        this.name = name;
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public Team getTeam() {
        return team;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Score getScore() {
        return score;
    }
}

class Team {
    private String name;
    private List<Student> members;
    private ScoringRule scoringRule;
    private Project project;

    public Team(String name, List<Student> members, ScoringRule scoringRule) {
        this.name = name;
        this.members = members;
        this.scoringRule = scoringRule;
    }

    public String getName() {
        return name;
    }

    public List<Student> getMembers() {
        return members;
    }

    public ScoringRule getScoringRule() {
        return scoringRule;
    }

    public Project submitProject(String projectName) {
        if (project != null) {
            return null;
        }

        project = new Project(projectName, this);
        return project;
    }

    public Project getProject() {
        return project;
    }
}

class Hackathon {
    private String name;
    private List<Team> teams;
    private Set<Student> registeredStudents;
    private String state;

    public Hackathon(String name) {
        this.name = name;
        teams = new ArrayList<>();
        registeredStudents = new HashSet<>();
        state = "OPEN";
    }

    public boolean registerTeam(Team team) {
        if (!state.equals("OPEN")) {
            System.out.println("Registration failed: Registration is closed.");
            return false;
        }

        int size = team.getMembers().size();

        if (size < 2 || size > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student student : team.getMembers()) {
            if (registeredStudents.contains(student)) {
                System.out.println("Registration failed: A student can belong to only one team.");
                return false;
            }
        }

        teams.add(team);
        registeredStudents.addAll(team.getMembers());

        System.out.println("Team " + team.getName() + " registered ("
                + size + " members, "
                + team.getScoringRule().getTrackName() + " track).");

        return true;
    }

    public Project submitProject(Team team, String projectName) {
        Project project = team.submitProject(projectName);

        if (project == null) {
            System.out.println("Submission failed: A team can submit only one project.");
            return null;
        }

        System.out.println("Project '" + projectName + "' submitted by "
                + team.getName() + ".");

        return project;
    }

    public boolean recordScore(Project project, Judge judge,
            double idea, double execution,
            double presentation) {
        if (state.equals("PUBLISHED")) {
            System.out.println("Rescore rejected: Results have already been published.");
            return false;
        }

        Score score = new Score(idea, execution, presentation);
        project.setScore(score);
        state = "JUDGING";

        System.out.println("Score recorded for '" + project.getName() + "'.");
        return true;
    }

    public void publishResults() {
        state = "PUBLISHED";

        for (Team team : teams) {
            Project project = team.getProject();

            if (project != null && project.getScore() != null) {
                double finalScore = project.getScore().calculate(
                        team.getScoringRule());

                System.out.printf("Final score: %.2f%n", finalScore);
            }
        }

        System.out.println("Results published.");
    }
}

public class step1 {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon("Code Sprint");

        Student asha = new Student("Asha");
        Student ravi = new Student("Ravi");
        Student neha = new Student("Neha");
        Student kiran = new Student("Kiran");

        Team byteBusters = new Team(
                "ByteBusters",
                Arrays.asList(asha, ravi, neha),
                new InnovationScoringRule());

        Team soloCoder = new Team(
                "SoloCoder",
                Arrays.asList(kiran),
                new OpenScoringRule());

        hackathon.registerTeam(byteBusters);
        hackathon.registerTeam(soloCoder);

        Project project = hackathon.submitProject(byteBusters, "SmartAttend");

        Judge judge = new Judge("Judge 1");

        hackathon.recordScore(
                project,
                judge,
                8,
                7,
                9);

        hackathon.publishResults();

        hackathon.recordScore(
                project,
                judge,
                10,
                7,
                9);
    }
}