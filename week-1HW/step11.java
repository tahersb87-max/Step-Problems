import java.util.Random;
import java.util.Scanner;

public class step11 {

    public static String playRound(String playerMove, String computerMove) {

        if (playerMove.equals(computerMove)) {
            return "Draw";
        }

        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
                (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
                (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {

            return "Player Wins";
        }

        return "Computer Wins";
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        String[] moves = { "Rock", "Paper", "Scissors" };

        int rounds = 5;

        String[] playerMoves = new String[rounds];
        String[] computerMoves = new String[rounds];
        String[] results = new String[rounds];

        int wins = 0;
        int losses = 0;
        int draws = 0;

        System.out.println("=== ROCK PAPER SCISSORS GAME ===");

        for (int i = 0; i < rounds; i++) {

            System.out.println("\nRound " + (i + 1));
            System.out.print("Enter Rock, Paper, or Scissors: ");

            String playerMove = scanner.nextLine().trim();

            playerMove = playerMove.substring(0, 1).toUpperCase()
                    + playerMove.substring(1).toLowerCase();

            while (!playerMove.equals("Rock") &&
                    !playerMove.equals("Paper") &&
                    !playerMove.equals("Scissors")) {

                System.out.print("Invalid move! Enter Rock, Paper, or Scissors: ");

                playerMove = scanner.nextLine().trim();

                playerMove = playerMove.substring(0, 1).toUpperCase()
                        + playerMove.substring(1).toLowerCase();
            }

            String computerMove = moves[random.nextInt(3)];

            String result = playRound(playerMove, computerMove);

            playerMoves[i] = playerMove;
            computerMoves[i] = computerMove;
            results[i] = result;

            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }

            System.out.println("Player: " + playerMove);
            System.out.println("Computer: " + computerMove);
            System.out.println("Result: " + result);
        }

        double winPercentage = ((double) wins / rounds) * 100;

        System.out.println("\n========== FINAL SUMMARY ==========");

        System.out.printf("%-10s %-15s %-18s %-20s%n",
                "Round", "Player Move", "Computer Move", "Result");

        System.out.println("--------------------------------------------------------------");

        for (int i = 0; i < rounds; i++) {
            System.out.printf("%-10d %-15s %-18s %-20s%n",
                    (i + 1),
                    playerMoves[i],
                    computerMoves[i],
                    results[i]);
        }

        System.out.println("\nTotal Wins: " + wins);
        System.out.println("Total Losses: " + losses);
        System.out.println("Total Draws: " + draws);
        System.out.printf("Player Win Percentage: %.1f%%%n", winPercentage);

        scanner.close();
    }
}
