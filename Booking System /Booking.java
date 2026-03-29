import java.util.ArrayList;
import java.util.List;

class Booking {
    int id;
    Customer customer;
    Show show;
    List<ShowSeat> bookedSeats = new ArrayList<>();
    double amount;
    BookingStatus status = BookingStatus.CREATED;

    Booking(int id, Customer customer, Show show) {
        this.id = id;
        this.customer = customer;
        this.show = show;
    }
}
