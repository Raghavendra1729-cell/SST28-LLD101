import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Admin admin = new Admin(1, "admin");
        Customer customer = new Customer(2, "amit");

        Movie movie = new Movie(1, "MovieA", 8);
        Theater theater = new Theater(1, "PVR", new SimplePricing());
        Screen screen = new Screen(1, "Screen1");
        screen.addSeat(new Seat(1, "A1", SeatType.REGULAR, 200));
        screen.addSeat(new Seat(2, "A2", SeatType.PREMIUM, 200));
        theater.addScreen(screen);

        AdminService adminService = new AdminService();
        adminService.createMovie(movie);
        adminService.createTheater(theater);

        Show show = new Show(1, movie, theater, screen, "10:00", "13:00");
        boolean showAdded = adminService.createShow(show);
        System.out.println(showAdded);

        BookingService bookingService = new BookingService();
        Booking booking = bookingService.bookSeat(customer, show, Arrays.asList(1, 2));
        if (booking != null) {
            boolean paid = bookingService.makePayment(booking.id, new UpiPayment());
            System.out.println(booking.amount);
            System.out.println(booking.status);
            System.out.println(paid);
        }

        SearchService searchService = new SearchService(adminService.shows);
        System.out.println(searchService.getTheatersByMovie(movie).size());
        System.out.println(searchService.getMoviesByTheater(theater).size());

        bookingService.cancelBooking(1);
        System.out.println(new CardRefund().refund(booking.amount));
        System.out.println(admin.name);
    }
}
