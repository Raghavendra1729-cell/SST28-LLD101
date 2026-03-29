import java.util.HashMap;
import java.util.Map;

class ShowSeat {
    Seat seat;
    SeatStatus status = SeatStatus.AVAILABLE;

    ShowSeat(Seat seat) {
        this.seat = seat;
    }
}

class Show {
    int id;
    Movie movie;
    Theater theater;
    Screen screen;
    String start;
    String end;
    Map<Integer, ShowSeat> seats = new HashMap<>();

    Show(int id, Movie movie, Theater theater, Screen screen, String start, String end) {
        this.id = id;
        this.movie = movie;
        this.theater = theater;
        this.screen = screen;
        this.start = start;
        this.end = end;

        for (Seat seat : screen.seats) {
            seats.put(seat.id, new ShowSeat(seat));
        }
    }
}
