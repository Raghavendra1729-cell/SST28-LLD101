public abstract class BoardEntity {
    private final int jump;

    protected BoardEntity(int jump) {
        this.jump = jump;
    }

    public int getJump() {
        return jump;
    }
}
