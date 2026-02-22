import java.util.*;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Hostel Fee Calculator ===");
        BookingRequest req = new BookingRequest(LegacyRoomTypes.DOUBLE, List.of(AddOn.LAUNDRY, AddOn.MESS));
        
        Map<Integer, Rooms> roomPrices = new HashMap<>();
        roomPrices.put(LegacyRoomTypes.SINGLE, new SingleRoom());
        roomPrices.put(LegacyRoomTypes.DOUBLE, new DoubleRoom());
        roomPrices.put(LegacyRoomTypes.TRIPLE, new TripleRoom());
        roomPrices.put(LegacyRoomTypes.DELUXE, new DeluxeRoom());

        Map<AddOn, ExtraPricingForAddons> addOnPrices = new HashMap<>();
        addOnPrices.put(AddOn.MESS, new MessAddOn());
        addOnPrices.put(AddOn.LAUNDRY, new LaundryAddOn());
        addOnPrices.put(AddOn.GYM, new GymAddOn());

        PersistenceDb db = new FakeBookingRepo();

        HostelFeeCalculator calc = new HostelFeeCalculator(db, roomPrices, addOnPrices);
        calc.process(req);
    }
}