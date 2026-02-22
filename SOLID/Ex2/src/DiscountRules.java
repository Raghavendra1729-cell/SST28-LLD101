public class DiscountRules {
    public static double discountAmount(DiscountRule rule, double subtotal, int distinctLines) {
       return rule.discount(subtotal, distinctLines);
    }
}