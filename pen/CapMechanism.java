public class CapMechanism implements StartingMechanism {
    @Override
    public void start(Pen pen) {
        pen.setStarted(true);
        System.out.println("Cap opened. Pen is ready to write.");
    }

    @Override
    public void close(Pen pen) {
        pen.setStarted(false);
        System.out.println("Cap closed. Pen is safely covered.");
    }
}
