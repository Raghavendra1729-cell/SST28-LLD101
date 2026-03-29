interface Refund {
    boolean refund(double amount);
}

class UpiRefund implements Refund {
    public boolean refund(double amount) {
        return true;
    }
}

class CardRefund implements Refund {
    public boolean refund(double amount) {
        return true;
    }
}
