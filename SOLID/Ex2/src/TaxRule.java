public class TaxRule {
    public static double taxPercent(taxRules rule) {
        return rule.tax();
    }

    public static double calculateTax(double subtotal, taxRules rule) {
        double taxPct = taxPercent(rule);
        return subtotal * (taxPct / 100.0);
    }
}