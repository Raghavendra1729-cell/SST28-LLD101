import java.util.List;

public class ElevatorSystem {
    private final List<Floor> floors;
    private final CallStrategy dispatcher;
    private final List<ElevatorCar> fleet;

    public ElevatorSystem(List<Floor> floors, CallStrategy dispatcher, List<ElevatorCar> fleet) {
        this.floors = floors;
        this.dispatcher = dispatcher;
        this.fleet = fleet;
        Emergency.setCars(fleet);
    }

    ElevatorCar requestElevator(Floor floor, State requestedDirection) {
        ElevatorCar chosenCar = dispatcher.assignCar(floor, requestedDirection);
        chosenCar.addDestination(floor);
        return chosenCar;
    }

    List<Floor> getFloors() {
        return floors;
    }

    List<ElevatorCar> getCars() {
        return fleet;
    }
}
