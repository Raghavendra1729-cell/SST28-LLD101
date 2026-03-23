import java.util.ArrayList;
import java.util.List;

public class Level {
    String id;
    List<ParkingSlot> slots = new ArrayList<>();

    public Level(String id) {
        this.id = id;
    }

    public Level addSlot(ParkingSlot slot) {
        slots.add(slot);
        return this;
    }
}
