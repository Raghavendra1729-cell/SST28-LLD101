interface Rooms {
    double getPrice();
}

class SingleRoom implements Rooms {
    @Override public double getPrice() { return 14000.0; }
}

class DoubleRoom implements Rooms {
    @Override public double getPrice() { return 15000.0; }
}

class TripleRoom implements Rooms {
    @Override public double getPrice() { return 12000.0; }
}

class DeluxeRoom implements Rooms {
    @Override public double getPrice() { return 16000.0; }
}