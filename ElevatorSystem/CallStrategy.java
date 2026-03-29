public interface CallStrategy {
    ElevatorCar assignCar(Floor requestedFloor, State requestedDirection);
}
