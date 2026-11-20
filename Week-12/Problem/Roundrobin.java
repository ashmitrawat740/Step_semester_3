import java.util.*;

public class LabScheduler {
    static class Job {
        final String name;
        int remainingTime;

        Job(String name, int remainingTime) {
            this.name = name;
            this.remainingTime = remainingTime;
        }
    }

    public static void schedule(int quantum, List<Job> initialJobs) {
        Queue<Job> queue = new ArrayDeque<>(initialJobs);
        List<String> slices = new ArrayList<>();
        List<String> finishes = new ArrayList<>();

        int currentTime = 0;

        while (!queue.isEmpty()) {
            Job current = queue.poll();
            int runTime = Math.min(quantum, current.remainingTime);
            int start = currentTime;
            currentTime += runTime;
            current.remainingTime -= runTime;

            slices.add(current.name + "(" + start + "-" + currentTime + ")");

            if (current.remainingTime > 0) {
                queue.offer(current);
            } else {
                finishes.add(current.name + " " + currentTime);
            }
        }

        System.out.println(String.join(" ", slices));
        System.out.println("finish " + String.join(", ", finishes));
    }

    public static void main(String[] args) {
        List<Job> jobs = Arrays.asList(
            new Job("P1", 5),
            new Job("P2", 3),
            new Job("P3", 7)
        );
        schedule(3, jobs);
    }
}