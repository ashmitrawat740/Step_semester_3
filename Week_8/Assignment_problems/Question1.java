import java.util.*;

// Strategy interface for scoring rule extensibility
interface ScoringRule {
    double calculateFinalScore(double idea, double execution, double presentation);
}

class InnovationScoringRule implements ScoringRule {
    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea * 0.50) + (execution * 0.30) + (presentation * 0.20);
    }
}

class OpenScoringRule implements ScoringRule {
    @Override
    public double calculateFinalScore(double idea, double execution, double presentation) {
        return (idea + execution + presentation) / 3.0;
    }
}

class Student {
    private final String name;

    public Student(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Project {
    private final String title;
    private Double ideaScore;
    private Double executionScore;
    private Double presentationScore;

    public Project(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setScores(double idea, double execution, double presentation) {
        this.ideaScore = idea;
        this.executionScore = execution;
        this.presentationScore = presentation;
    }

    public boolean isScored() {
        return ideaScore != null && executionScore != null && presentationScore != null;
    }

    public double getIdeaScore() { return ideaScore != null ? ideaScore : 0; }
    public double getExecutionScore() { return executionScore != null ? executionScore : 0; }
    public double getPresentationScore() { return presentationScore != null ? presentationScore : 0; }
}

class Team {
    private final String name;
    private final List<Student> members;
    private final String trackName;
    private final ScoringRule scoringRule;
    private Project project;

    public Team(String name, List<Student> members, String trackName, ScoringRule scoringRule) {
        this.name = name;
        this.members = members;
        this.trackName = trackName;
        this.scoringRule = scoringRule;
    }

    public String getName() { return name; }
    public List<Student> getMembers() { return members; }
    public String getTrackName() { return trackName; }
    public Project getProject() { return project; }

    public void setProject(Project project) {
        this.project = project;
    }

    public double calculateFinalScore() {
        if (project == null || !project.isScored()) return 0.0;
        return scoringRule.calculateFinalScore(
            project.getIdeaScore(),
            project.getExecutionScore(),
            project.getPresentationScore()
        );
    }
}

enum HackathonState {
    OPEN, JUDGING, PUBLISHED
}

class Hackathon {
    private final Set<String> registeredStudents = new HashSet<>();
    private final List<Team> teams = new ArrayList<>();
    private HackathonState state = HackathonState.OPEN;

    public boolean registerTeam(String teamName, List<Student> members, String trackName, ScoringRule scoringRule) {
        if (members.size() < 2 || members.size() > 4) {
            System.out.println("Registration failed: A team must have 2 to 4 members.");
            return false;
        }

        for (Student s : members) {
            if (registeredStudents.contains(s.getName())) {
                System.out.println("Registration failed: Student " + s.getName() + " is already in a team.");
                return false;
            }
        }

        for (Student s : members) {
            registeredStudents.add(s.getName());
        }

        Team team = new Team(teamName, members, trackName, scoringRule);
        teams.add(team);
        System.out.println("Team " + teamName + " registered (" + members.size() + " members, " + trackName + " track).");
        return true;
    }

    public void submitProject(String teamName, String projectTitle) {
        Team team = findTeam(teamName);
        if (team == null) return;
        
        Project project = new Project(projectTitle);
        team.setProject(project);
        System.out.println("Project '" + projectTitle + "' submitted by " + teamName + ".");
    }

    public void scoreProject(String teamName, double idea, double execution, double presentation) {
        if (state == HackathonState.PUBLISHED) {
            System.out.println("Rescore rejected: Results have already been published.");
            return;
        }

        Team team = findTeam(teamName);
        if (team != null && team.getProject() != null) {
            team.getProject().setScores(idea, execution, presentation);
            System.out.println("Score recorded for '" + team.getProject().getTitle() + "'. Final score: " 
                + String.format("%.2f", team.calculateFinalScore()) + ".");
        }
    }

    public void publishResults() {
        this.state = HackathonState.PUBLISHED;
        System.out.println("Results published.");
    }

    private Team findTeam(String name) {
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(name)) return t;
        }
        return null;
    }
}

public class Question1 {
    public static void main(String[] args) {
        Hackathon hackathon = new Hackathon();

        // 1. Team 'ByteBusters' registers with 3 members in Innovation track
        List<Student> byteBustersMembers = Arrays.asList(
            new Student("Asha"), new Student("Ravi"), new Student("Neha")
        );
        hackathon.registerTeam("ByteBusters", byteBustersMembers, "Innovation", new InnovationScoringRule());

        // 2. Team 'SoloCoder' registers with 1 member (fails rule)
        List<Student> soloMembers = Collections.singletonList(new Student("Kiran"));
        hackathon.registerTeam("SoloCoder", soloMembers, "Open", new OpenScoringRule());

        // 3. ByteBusters submits project 'SmartAttend'
        hackathon.submitProject("ByteBusters", "SmartAttend");

        // 4. Judge scores 'SmartAttend'
        hackathon.scoreProject("ByteBusters", 8, 7, 9);

        // 5. Organiser publishes results
        hackathon.publishResults();

        // 6. Judge attempts to rescore
        hackathon.scoreProject("ByteBusters", 10, 7, 9);
    }
}