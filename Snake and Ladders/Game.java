import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final GameVersion version;
    private final List<Player> winners;
    private int currentPlayerIndex;

    public Game(Board board, List<Player> players, Dice dice, GameVersion version) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.version = version;
        this.winners = new ArrayList<>();
    }

    public void makeTurn() {
        if (isFinished()) {
            return;
        }

        currentPlayerIndex = getNextActivePlayerIndex(currentPlayerIndex);
        Player player = players.get(currentPlayerIndex);
        int consecutiveSixes = 0;
        boolean extraTurn = true;

        while (extraTurn) {
            extraTurn = false;
            int roll = dice.roll();
            if (roll == 6) {
                consecutiveSixes++;
            } else {
                consecutiveSixes = 0;
            }

            int currentPosition = player.getPosition();
            int tentativePosition = currentPosition + roll;

            if (tentativePosition <= board.getFinalSquare()) {
                player.setPosition(tentativePosition);
                BoardEntity entity = board.getEntityAt(player.getPosition());
                if (entity != null) {
                    player.setPosition(player.getPosition() + entity.getJump());
                }
            }

            System.out.println(player.getName() + " rolled " + roll);
            System.out.println("position " + player.getPosition());

            if (player.getPosition() == board.getFinalSquare()) {
                winners.add(player);
                System.out.println(player.getName() + " wins");
                if (!isFinished()) {
                    currentPlayerIndex = getNextActivePlayerIndex((currentPlayerIndex + 1) % players.size());
                }
                return;
            }

            if (roll == 6) {
                if (version == GameVersion.EASY) {
                    extraTurn = true;
                } else if (consecutiveSixes < 3) {
                    extraTurn = true;
                }
            }

            if (roll == 6 && version == GameVersion.DIFFICULT && !extraTurn) {
                System.out.println("turn over");
            }
        }

        currentPlayerIndex = getNextActivePlayerIndex((currentPlayerIndex + 1) % players.size());
    }

    public Player getWinner() {
        return winners.isEmpty() ? null : winners.get(0);
    }

    public List<Player> getWinners() {
        return Collections.unmodifiableList(winners);
    }

    public boolean isFinished() {
        return players.size() - winners.size() < 2;
    }

    public Board getBoard() {
        return board;
    }

    private int getNextActivePlayerIndex(int startIndex) {
        for (int i = 0; i < players.size(); i++) {
            int candidateIndex = (startIndex + i) % players.size();
            if (!winners.contains(players.get(candidateIndex))) {
                return candidateIndex;
            }
        }
        return startIndex;
    }
}
