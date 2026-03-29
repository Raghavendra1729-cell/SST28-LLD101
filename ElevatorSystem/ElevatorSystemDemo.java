import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ElevatorSystemDemo {
    public static void main(String[] args) throws Exception {
        ElevatorFactory factory = new ElevatorFactory();
        ElevatorSystem system = factory.getSystem(new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6)), "shortestPath", 2);

        List<Floor> floors = system.getFloors();
        Floor groundFloor = floorByNumber(floors, 0);
        Floor secondFloor = floorByNumber(floors, 2);
        Floor fifthFloor = floorByNumber(floors, 5);
        Floor sixthFloor = floorByNumber(floors, 6);

        System.out.println("Requesting elevator to floor 5");
        ElevatorCar firstCar = system.requestElevator(fifthFloor, State.UP);
        System.out.println("Assigned elevator: " + firstCar.getCarId());
        firstCar.move();

        System.out.println("Passenger selects floor 2 from inside elevator " + firstCar.getCarId());
        firstCar.addDestination(secondFloor);
        firstCar.move();

        System.out.println("Requesting another elevator to floor 6");
        ElevatorCar secondCar = system.requestElevator(sixthFloor, State.DOWN);
        System.out.println("Assigned elevator: " + secondCar.getCarId());
        secondCar.move();

        System.out.println("Passenger selects ground floor from inside elevator " + secondCar.getCarId());
        secondCar.addDestination(groundFloor);
        secondCar.move();

        System.out.println("Final elevator positions:");
        for (ElevatorCar car : system.getCars()) {
            System.out.println("Elevator " + car.getCarId() + " is at floor " + car.getAtFloor().getFloorNumber() + " with state " + car.getState());
        }
    }

    private static Floor floorByNumber(List<Floor> floors, int floorNumber) {
        for (Floor floor : floors) {
            if (floor.getFloorNumber() == floorNumber) {
                return floor;
            }
        }
        throw new IllegalArgumentException("Floor not found: " + floorNumber);
    }
}
