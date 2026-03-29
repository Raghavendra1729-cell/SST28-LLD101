public class OutButtons {
    private boolean upActive;
    private boolean downActive;

    void pressUp() {
        upActive = true;
    }

    void pressDown() {
        downActive = true;
    }

    boolean isUpRequested() {
        return upActive;
    }

    boolean isDownRequested() {
        return downActive;
    }

    void resetUp() {
        upActive = false;
    }

    void resetDown() {
        downActive = false;
    }
}
