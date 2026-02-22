public interface DiscountRule {
    double discount(double subtotal, int distinctLines);
}