import java.util.Random;

public class Board {
    private final int dimension;
    private final int finalSquare;
    private final BoardEntity[] cells;
    private final boolean[] used;
    private final Random random = new Random();

    public Board(int dimension) {
        this.dimension = dimension;
        this.finalSquare = dimension * dimension - 1;
        this.cells = new BoardEntity[dimension * dimension];
        this.used = new boolean[dimension * dimension];
        placeEntities();
    }

    public int getFinalSquare() {
        return finalSquare;
    }

    public BoardEntity getEntityAt(int position) {
        if (position < 0 || position >= cells.length) {
            return null;
        }
        return cells[position];
    }

    private void placeEntities() {
        int snakeCount = Math.max(1, dimension - 1);
        int ladderCount = Math.max(1, dimension - 1);
        placeSnakes(snakeCount);
        placeLadders(ladderCount);
    }

    private void placeSnakes(int count) {
        int placed = 0;
        int attempts = 0;
        int maxAttempts = cells.length * 20;
        while (placed < count && attempts < maxAttempts) {
            attempts++;
            int start = random.nextInt(Math.max(1, finalSquare - 1)) + 1;
            int maxDrop = Math.min(dimension, start);
            if (maxDrop < 2) {
                continue;
            }
            int end = start - (random.nextInt(maxDrop - 1) + 1);
            if (isValidPlacement(start, end, false)) {
                cells[start] = new Snake(start, end);
                reserveSquares(start, end);
                placed++;
            }
        }
    }

    private void placeLadders(int count) {
        int placed = 0;
        int attempts = 0;
        int maxAttempts = cells.length * 20;
        while (placed < count && attempts < maxAttempts) {
            attempts++;
            int start = random.nextInt(Math.max(1, finalSquare - 1)) + 1;
            int maxRise = Math.min(dimension, finalSquare - start);
            if (maxRise < 2) {
                continue;
            }
            int end = start + (random.nextInt(maxRise - 1) + 1);
            if (isValidPlacement(start, end, true)) {
                cells[start] = new Ladder(start, end);
                reserveSquares(start, end);
                placed++;
            }
        }
    }

    private boolean isValidPlacement(int start, int end, boolean ladder) {
        if (start <= 0 || start >= finalSquare || end <= 0 || end >= finalSquare) {
            return false;
        }
        if (used[start] || used[end]) {
            return false;
        }
        if (ladder && end <= start) {
            return false;
        }
        if (!ladder && end >= start) {
            return false;
        }
        return true;
    }

    private void reserveSquares(int start, int end) {
        used[start] = true;
        used[end] = true;
    }
}
