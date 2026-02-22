import java.util.List;

public class InvoiceService {
    private int invoiceSeq;
    public String invId;

    public InvoiceService(int invoiceSeq) {
        this.invoiceSeq = invoiceSeq;
    }

    public void generateInvoiceId(PrinterService ps) {
        invId = "INV-" + (++invoiceSeq);
        ps.out.append("Invoice# ").append(invId).append("\n");
    }

    public double Generatesubtotal(PrinterService ps, List<OrderLine> lines, PersistenceDb db) {
        double subtotal = 0.0;
        for (OrderLine l : lines) {
            MenuItem item = db.getItem(l.itemId);
            double lineTotal = item.price * l.qty;
            subtotal += lineTotal;
            ps.out.append(String.format("- %s x%d = %.2f\n", item.name, l.qty, lineTotal));
        }
        return subtotal;
    }
}