public class Player {
    private final int id;
    private final String name;
    private int position;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.position = 0;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
