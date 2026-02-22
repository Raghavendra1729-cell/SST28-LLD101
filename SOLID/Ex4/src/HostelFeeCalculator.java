import java.util.*;

public class HostelFeeCalculator {
    private final PersistenceDb repo;
    private final Map<Integer, Rooms> roomPrices;
    private final Map<AddOn, ExtraPricingForAddons> addOnPrices;

    public HostelFeeCalculator(PersistenceDb repo, 
                               Map<Integer, Rooms> roomPrices, 
                               Map<AddOn, ExtraPricingForAddons> addOnPrices) { 
        this.repo = repo; 
        this.roomPrices = roomPrices;
        this.addOnPrices = addOnPrices;
    }

    public void process(BookingRequest req) {
        Money monthly = calculateMonthly(req);
        Money deposit = new Money(5000.00);

        ReceiptPrinter.print(req, monthly, deposit);

        String bookingId = "H-" + (7000 + new Random(1).nextInt(1000)); 
        repo.save(bookingId, req, monthly, deposit);
    }

    private Money calculateMonthly(BookingRequest req) {
        Rooms room = roomPrices.getOrDefault(req.roomType, new DeluxeRoom());
        double base = room.getPrice();

        double add = 0.0;
        for (AddOn a : req.addOns) {
            ExtraPricingForAddons addonPricing = addOnPrices.get(a);
            add += addonPricing.price();
        }

        return new Money(base + add);
    }
}