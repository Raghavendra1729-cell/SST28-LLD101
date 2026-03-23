import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    List<Level> levels = new ArrayList<>();
    List<String> gates = new ArrayList<>();
    Map<String, Integer> rates = new HashMap<>();
    Map<String, Ticket> activeTickets = new HashMap<>();
    int ticketCounter = 1;

    public ParkingLot() {
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void addGate(String gateId) {
        gates.add(gateId);
    }

    public void setRate(String slotType, int rate) {
        rates.put(slotType, rate);
    }

    public Ticket park(Vehicle vehicle, String entryGateId) {
        if (!gates.contains(entryGateId)) {
            throw new RuntimeException("Unknown gate: " + entryGateId);
        }

        String neededType = getSlotType(vehicle.type);
        ParkingSlot bestSlot = null;
        int bestDistance = Integer.MAX_VALUE;

        for (Level level : levels) {
            for (ParkingSlot slot : level.slots) {
                if (slot.occupied) {
                    continue;
                }
                if (!slot.type.equals(neededType)) {
                    continue;
                }

                int distance = slot.getDistance(entryGateId);
                if (bestSlot == null || distance < bestDistance) {
                    bestSlot = slot;
                    bestDistance = distance;
                }
            }
        }

        if (bestSlot == null) {
            throw new RuntimeException("No slot available for " + vehicle.type);
        }

        bestSlot.occupied = true;
        Ticket ticket = new Ticket(
                "T" + ticketCounter++,
                vehicle,
                bestSlot,
                entryGateId
        );
        activeTickets.put(ticket.ticketId, ticket);
        return ticket;
    }

    public int exit(Ticket ticket) {
        Ticket savedTicket = activeTickets.remove(ticket.ticketId);
        if (savedTicket == null) {
            throw new RuntimeException("Invalid ticket");
        }

        savedTicket.slot.occupied = false;
        return savedTicket.hours * rates.get(savedTicket.slot.type);
    }

    public Map<String, Integer> getStatus() {
        Map<String, Integer> result = new HashMap<>();
        result.put("SMALL", 0);
        result.put("MEDIUM", 0);
        result.put("LARGE", 0);

        for (Level level : levels) {
            for (ParkingSlot slot : level.slots) {
                if (!slot.occupied) {
                    result.put(slot.type, result.get(slot.type) + 1);
                }
            }
        }
        return result;
    }

    public String getSlotType(String vehicleType) {
        if (vehicleType.equals("TWO_WHEELER")) {
            return "SMALL";
        }
        if (vehicleType.equals("CAR")) {
            return "MEDIUM";
        }
        return "LARGE";
    }
}
