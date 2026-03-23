import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter board size n for an n x n board: ");
        int boardSize = scanner.nextInt();

        System.out.print("Enter number of players: ");
        int playerCount = scanner.nextInt();

        System.out.print("Enter game version (EASY or DIFFICULT): ");
        String versionInput = scanner.next().trim().toUpperCase();
        GameVersion version = GameVersion.EASY;
        if (versionInput.equals("DIFFICULT")) {
            version = GameVersion.DIFFICULT;
        }

        Game game = GameFactory.createGame(boardSize, playerCount, version);

        while (!game.isFinished()) {
            game.makeTurn();
        }

        scanner.close();
    }
}
