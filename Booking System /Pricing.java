interface PricingStrategy {
    double getPrice(Seat seat, Show show);
}

class SimplePricing implements PricingStrategy {
    public double getPrice(Seat seat, Show show) {
        double price = seat.basePrice;
        if (seat.type == SeatType.PREMIUM) {
            price += 100;
        }
        if (show.movie.popularity > 7) {
            price += 50;
        }
        return price;
    }
}
