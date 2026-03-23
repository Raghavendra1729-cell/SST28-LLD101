public abstract class Pen {
    private String inkColor;
    private boolean started;
    private final StartingMechanism startingMechanism;

    public Pen(String inkColor, StartingMechanism startingMechanism) {
        this.inkColor = inkColor;
        this.startingMechanism = startingMechanism;
        this.started = false;
    }

    public abstract void write();

    public void refill(String newColor) {
        inkColor = newColor;
        System.out.println("Pen refilled. New ink color is " + inkColor + ".");
    }

    public void start() {
        startingMechanism.start(this);
    }

    public void close() {
        startingMechanism.close(this);
    }

    public void checkIfStarted() {
        if (!started) {
            throw new IllegalStateException("Cannot write before starting the pen.");
        }
    }

    public String getInkColor() {
        return inkColor;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }
}
