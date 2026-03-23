public class Ticket {
    String ticketId;
    Vehicle vehicle;
    ParkingSlot slot;
    String gateId;
    int hours;

    public Ticket(String ticketId, Vehicle vehicle, ParkingSlot slot, String gateId) {
        this.ticketId = ticketId;
        this.vehicle = vehicle;
        this.slot = slot;
        this.gateId = gateId;
        this.hours = 1;
    }

    @Override
    public String toString() {
        return "Ticket{id='" + ticketId + "', vehicle=" + vehicle + ", slot=" + slot.id
                + ", level=" + slot.levelId + ", gate=" + gateId + "}";
    }
}
