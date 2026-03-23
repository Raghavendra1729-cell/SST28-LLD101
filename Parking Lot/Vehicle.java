public class Vehicle {
    String licensePlate;
    String type;

    public Vehicle(String licensePlate, String type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    @Override
    public String toString() {
        return type + " " + licensePlate;
    }
}
