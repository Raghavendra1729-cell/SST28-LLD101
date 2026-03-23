public class ClickMechanism implements StartingMechanism {
    @Override
    public void start(Pen pen) {
        pen.setStarted(true);
        System.out.println("Pen is ready to write.");
    }

    @Override
    public void close(Pen pen) {
        pen.setStarted(false);
        System.out.println("Pen is closed.");
    }
}
