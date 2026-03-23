public class GelPen extends Pen {
    public GelPen(String inkColor, StartingMechanism startingMechanism) {
        super(inkColor, startingMechanism);
    }

    @Override
    public void write() {
        checkIfStarted();
        System.out.println("Gel pen writes  with " + getInkColor() + " ink.");
    }
}
