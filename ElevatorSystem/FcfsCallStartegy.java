import java.util.List;

public class FcfsCallStartegy implements CallStrategy {
    private final List<ElevatorCar> fleet;

    FcfsCallStartegy(List<ElevatorCar> fleet) {
        this.fleet = fleet;
    }

    public ElevatorCar assignCar(Floor requestedFloor, State requestedDirection) {
        ElevatorCar fallback = null;
        for (ElevatorCar car : fleet) {
            if (!car.isAvailable()) {
                continue;
            }
            if (car.getState() == State.IDLE) {
                return car;
            }
            if (fallback == null) {
                fallback = car;
            }
        }
        if (fallback != null) {
            return fallback;
        }
        throw new IllegalStateException("No elevator is available");
    }
}
