import java.util.*;

interface BookingInterface {
    Booking bookSeat(Customer customer, Show show, List<Integer> seatIds);
    void cancelBooking(int bookingId);
}

class AdminService {
    List<Movie> movies = new ArrayList<>();
    List<Theater> theaters = new ArrayList<>();
    List<Show> shows = new ArrayList<>();

    void createMovie(Movie movie) {
        movies.add(movie);
    }

    void createTheater(Theater theater) {
        theaters.add(theater);
    }

    boolean createShow(Show show) {
        for (Show old : shows) {
            if (old.screen.id == show.screen.id) {
                if (!(show.end.compareTo(old.start) <= 0 || show.start.compareTo(old.end) >= 0)) {
                    return false;
                }
            }
        }
        shows.add(show);
        return true;
    }
}

class BookingService implements BookingInterface {
    Map<Integer, Booking> bookings = new HashMap<>();
    int bookingId = 1;

    public Booking bookSeat(Customer customer, Show show, List<Integer> seatIds) {
        Booking booking = new Booking(bookingId++, customer, show);

        for (int seatId : seatIds) {
            ShowSeat seat = show.seats.get(seatId);
            if (seat == null) return null;

            synchronized (seat) {
                if (seat.status != SeatStatus.AVAILABLE) {
                    return null;
                }
                seat.status = SeatStatus.IN_PROGRESS;
            }

            booking.bookedSeats.add(seat);
            booking.amount += show.theater.pricingStrategy.getPrice(seat.seat, show);
        }

        bookings.put(booking.id, booking);
        return booking;
    }

    boolean makePayment(int bookingId, Payment payment) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return false;

        boolean ok = payment.makePayment(booking.amount);
        if (ok) {
            for (ShowSeat seat : booking.bookedSeats) {
                seat.status = SeatStatus.BOOKED;
            }
            booking.status = BookingStatus.BOOKED;
        } else {
            for (ShowSeat seat : booking.bookedSeats) {
                seat.status = SeatStatus.AVAILABLE;
            }
        }
        return ok;
    }

    public void cancelBooking(int bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null) return;

        for (ShowSeat seat : booking.bookedSeats) {
            seat.status = SeatStatus.AVAILABLE;
        }
        booking.status = BookingStatus.CANCELLED;
    }
}

class SearchService {
    List<Show> shows;

    SearchService(List<Show> shows) {
        this.shows = shows;
    }

    List<Theater> getTheatersByMovie(Movie movie) {
        List<Theater> list = new ArrayList<>();
        for (Show show : shows) {
            if (show.movie.id == movie.id) {
                list.add(show.theater);
            }
        }
        return list;
    }

    List<Movie> getMoviesByTheater(Theater theater) {
        List<Movie> list = new ArrayList<>();
        for (Show show : shows) {
            if (show.theater.id == theater.id) {
                list.add(show.movie);
            }
        }
        return list;
    }
}
