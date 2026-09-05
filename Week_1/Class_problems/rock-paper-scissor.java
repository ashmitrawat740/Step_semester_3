import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {
    private static final String[] MOVES = {"Rock", "Paper", "Scissors"};

    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equalsIgnoreCase(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equalsIgnoreCase("Rock") && computerMove.equalsIgnoreCase("Scissors")) ||
            (playerMove.equalsIgnoreCase("Paper") && computerMove.equalsIgnoreCase("Rock")) ||
            (playerMove.equalsIgnoreCase("Scissors") && computerMove.equalsIgnoreCase("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int totalRounds = 5;

        // Predefined demo moves or interactive input fallback
        String[] demoMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        
        String[][] history = new String[totalRounds][4];
        int wins = 0, losses = 0, draws = 0;

        for (int i = 0; i < totalRounds; i++) {
            String playerMove = demoMoves[i]; // Switch to scanner.nextLine() for live user input
            String computerMove = MOVES[random.nextInt(3)];
            String result = playRound(playerMove, computerMove);

            if (result.equals("Player Wins")) wins++;
            else if (result.equals("Computer Wins")) losses++;
            else draws++;

            history[i][0] = String.valueOf(i + 1);
            history[i][1] = playerMove;
            history[i][2] = computerMove;
            history[i][3] = result;
        }

        // Summary Table Output
        System.out.printf("%-8s | %-12s | %-14s | %-15s%n", "Round", "Player Move", "Computer Move", "Result");
        System.out.println("----------------------------------------------------------");
        for (String[] row : history) {
            System.out.printf("%-8s | %-12s | %-14s | %-15s%n", row[0], row[1], row[2], row[3]);
        }

        double winPercentage = ((double) wins / totalRounds) * 100.0;
        System.out.println("----------------------------------------------------------");
        System.out.printf("Final Summary: Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%%n",
                wins, losses, draws, winPercentage);
        
        scanner.close();
    }
}