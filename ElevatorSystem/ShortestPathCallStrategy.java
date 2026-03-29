import java.util.List;

public class ShortestPathCallStrategy implements CallStrategy {
    private final List<ElevatorCar> fleet;

    ShortestPathCallStrategy(List<ElevatorCar> fleet) {
        this.fleet = fleet;
    }

    public ElevatorCar assignCar(Floor requestedFloor, State requestedDirection) {
        ElevatorCar nearest = null;
        int smallestGap = Integer.MAX_VALUE;

        for (ElevatorCar car : fleet) {
            if (!car.isAvailable()) {
                continue;
            }

            int gap = Math.abs(car.getAtFloor().getFloorNumber() - requestedFloor.getFloorNumber());
            if (gap < smallestGap) {
                nearest = car;
                smallestGap = gap;
            }
        }

        if (nearest == null) {
            throw new IllegalStateException("No elevator is available");
        }
        return nearest;
    }
}
