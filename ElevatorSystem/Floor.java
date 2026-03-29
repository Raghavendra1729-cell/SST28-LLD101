public class Floor {
    private final int number;
    private final OutButtons hallPanel;

    public Floor(int number, OutButtons hallPanel) {
        this.number = number;
        this.hallPanel = hallPanel;
    }

    int getFloorNumber() {
        return number;
    }

    OutButtons getOutButtons() {
        return hallPanel;
    }
}
