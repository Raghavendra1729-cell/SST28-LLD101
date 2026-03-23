import java.util.List;

public class Game {
    private final Board board;
    private final List<Player> players;
    private final Dice dice;
    private final GameVersion version;
    private int currentPlayerIndex;
    private Player winner;

    public Game(Board board, List<Player> players, Dice dice, GameVersion version) {
        this.board = board;
        this.players = players;
        this.dice = dice;
        this.version = version;
    }

    public void makeTurn() {
        if (winner != null) {
            return;
        }

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
                winner = player;
                System.out.println(player.getName() + " wins");
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

        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public Player getWinner() {
        return winner;
    }

    public boolean isFinished() {
        return winner != null;
    }

    public Board getBoard() {
        return board;
    }
}
