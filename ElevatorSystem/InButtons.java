import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InButtons {
    private final Set<Floor> allowedFloors;
    private ElevatorCar attachedCar;

    public InButtons(List<Floor> floors) {
        this.allowedFloors = new HashSet<>(floors);
    }

    void attachTo(ElevatorCar car) {
        this.attachedCar = car;
    }

    void press(Floor floor) {
        if (!allowedFloors.contains(floor)) {
            throw new IllegalArgumentException("Invalid floor selected");
        }
        if (attachedCar == null) {
            throw new IllegalStateException("Buttons are not attached to an elevator");
        }
        attachedCar.addDestination(floor);
    }

    void pressEmergency() {
        Emergency.emergency();
    }
}
