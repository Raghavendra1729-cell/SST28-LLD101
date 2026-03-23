public class Main {
    public static void main(String[] args) {
        Pen pen = PenFactory.createPen("gel pen", "blue", "click");

        try {
            pen.write();
        } catch (IllegalStateException exception) {
            System.out.println("Error: " + exception.getMessage());
        }

        pen.start();
        pen.write();
        pen.refill("black");
        pen.write();
        pen.close();
    }
}
