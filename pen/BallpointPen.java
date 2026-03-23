public class BallpointPen extends Pen {
    public BallpointPen(String inkColor, StartingMechanism startingMechanism) {
        super(inkColor, startingMechanism);
    }

    @Override
    public void write() {
        checkIfStarted();
        System.out.println("Ballpoint pen writes  with " + getInkColor() + " ink.");
    }
}
