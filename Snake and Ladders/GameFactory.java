import java.util.ArrayList;
import java.util.List;

public class GameFactory {
    public static Game createGame(int boardSize, int playerCount, GameVersion version) {
        Board board = new Board(boardSize);
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= playerCount; i++) {
            players.add(new Player(i, "Player-" + i));
        }

        return new Game(board, players, new StandardDice(), version);
    }
}
