public class OtherDiscountRules implements DiscountRule {
    @Override
    public double discount(double subtotal, int distinctLines) {
        return 0.0;
    }
}