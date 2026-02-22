import java.util.List;

public class CafeteriaSystem {
    PersistenceDb db;
    InvoiceService is;
    PrinterService ps;
    private final FileStore store = new FileStore();

    public CafeteriaSystem(PersistenceDb db) {
        this.db = db;
        is = new InvoiceService(1000);
        ps = new PrinterService();
    }

    public void addToMenu(MenuItem i) {
        db.save(i);
    }

    public void checkout(String customerType, List<OrderLine> lines) {
        taxRules t;
        DiscountRule d;
        if (customerType.equalsIgnoreCase("Student")) {
            t = new StudentTaxRules();
            d = new StudentDiscountRules();
        } else if (customerType.equalsIgnoreCase("Staff")) {
            t = new StaffTaxRules();
            d = new StaffDiscountRules();
        } else {
            t = new OtherTaxRules();
            d = new OtherDiscountRules();
        }

        is.generateInvoiceId(ps);

        double subtotal = is.Generatesubtotal(ps, lines, db);
        double taxPct = TaxRule.taxPercent(t);
        double tax = TaxRule.calculateTax(subtotal, t);
        double discount = DiscountRules.discountAmount(d, subtotal, lines.size());
        double total = subtotal + tax - discount;

        ps.out.append(String.format("Subtotal: %.2f\n", subtotal));
        ps.out.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
        ps.out.append(String.format("Discount: -%.2f\n", discount));
        ps.out.append(String.format("TOTAL: %.2f\n", total));

        String printable = InvoiceFormatter.identityFormat(ps.out.toString());
        System.out.print(printable);

        store.save(is.invId, printable);
        System.out.println("Saved invoice: " + is.invId + " (lines=" + store.countLines(is.invId) + ")");
        
        ps.out = new StringBuilder(); // Reset for next checkout
    }
}