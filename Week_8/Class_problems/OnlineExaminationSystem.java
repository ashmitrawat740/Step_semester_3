import java.util.*;

// Abstract Question class supporting polymorphism for evaluation
abstract class Question {
    private final int questionId;
    private final String prompt;

    public Question(int questionId, String prompt) {
        this.questionId = questionId;
        this.prompt = prompt;
    }

    public int getQuestionId() {
        return questionId;
    }

    public String getPrompt() {
        return prompt;
    }

    public abstract boolean evaluate(String studentAnswer);
}

// Concrete Question: Multiple Choice Question
class MultipleChoiceQuestion extends Question {
    private final String correctAnswer;

    public MultipleChoiceQuestion(int questionId, String prompt, String correctAnswer) {
        super(questionId, prompt);
        this.correctAnswer = correctAnswer;
    }

    @Override
    public boolean evaluate(String studentAnswer) {
        return correctAnswer.equalsIgnoreCase(studentAnswer);
    }
}

// Student Entity
class Student {
    private final String studentId;
    private final String name;

    public Student(String studentId, String name) {
        this.studentId = studentId;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

// Examination Entity
class Examination {
    private final String title;
    private final List<Question> questions;

    public Examination(String title) {
        this.title = title;
        this.questions = new ArrayList<>();
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public String getTitle() {
        return title;
    }

    public List<Question> getQuestions() {
        return questions;
    }
}

// Attempt Class representing a single, state-managed examination submission
class Attempt {
    private final Student student;
    private final Examination examination;
    private final Map<Integer, String> answers;
    private boolean isSubmitted;

    public Attempt(Student student, Examination examination) {
        this.student = student;
        this.examination = examination;
        this.answers = new HashMap<>();
        this.isSubmitted = false;
        System.out.println("Examination '" + examination.getTitle() + "' started by " + student.getName() + ".");
    }

    public void answerQuestion(int questionId, String answer) {
        if (isSubmitted) {
            System.out.println("Cannot modify answers after submission.");
            return;
        }
        answers.put(questionId, answer);
        System.out.println("Question " + questionId + " answered with '" + answer + "'.");
    }

    public void submit() {
        if (isSubmitted) {
            System.out.println("Attempt already submitted.");
            return;
        }
        isSubmitted = true;
        System.out.println("Examination '" + examination.getTitle() + "' submitted successfully.");
        evaluateResult();
    }

    private void evaluateResult() {
        int correctCount = 0;
        List<Question> questions = examination.getQuestions();
        for (Question q : questions) {
            String ans = answers.get(q.getQuestionId());
            if (ans != null && q.evaluate(ans)) {
                correctCount++;
            }
        }
        System.out.println("Result for '" + examination.getTitle() + "' attempt: " 
                + correctCount + "/" + questions.size() + " correct.");
    }
}

public class OnlineExaminationSystem {
    public static void main(String[] args) {
        Examination mathQuiz = new Examination("Math Quiz");
        mathQuiz.addQuestion(new MultipleChoiceQuestion(1, "What is 2+2?", "A"));
        mathQuiz.addQuestion(new MultipleChoiceQuestion(2, "What is 3*3?", "B"));

        Student student = new Student("S101", "Student");

        Attempt attempt = new Attempt(student, mathQuiz);
        attempt.answerQuestion(1, "A");
        attempt.answerQuestion(2, "C");
        attempt.submit();
    }
}