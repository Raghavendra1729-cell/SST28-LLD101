public class StudentDiscountRules implements DiscountRule {
    @Override
    public double discount(double subtotal, int distinctLines) {
        if (subtotal >= 180.0) return 10.0;
        return 0.0;
    }
}