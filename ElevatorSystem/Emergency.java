import java.util.Collections;
import java.util.List;

public class Emergency {
    private static List<ElevatorCar> registeredCars = Collections.emptyList();

    static void setCars(List<ElevatorCar> cars) {
        registeredCars = cars;
    }

    static void emergency() {
        System.out.println("all cars are being shut down");
        for (ElevatorCar car : registeredCars) {
            car.stop();
            car.setState(State.EMERGENCYSTOP);
        }
    }
}
