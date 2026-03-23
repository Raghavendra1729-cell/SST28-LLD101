public class InkPen extends Pen {
    public InkPen(String inkColor, StartingMechanism startingMechanism) {
        super(inkColor, startingMechanism);
    }

    @Override
    public void write() {
        checkIfStarted();
        System.out.println("Ink pen writes  with  " + getInkColor() + " ink.");
    }
}
