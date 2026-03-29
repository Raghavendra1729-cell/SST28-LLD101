abstract class Payment {
    abstract boolean makePayment(double amount);
}

class UpiPayment extends Payment {
    boolean makePayment(double amount) {
        return true;
    }
}

class CardPayment extends Payment {
    boolean makePayment(double amount) {
        return true;
    }
}
