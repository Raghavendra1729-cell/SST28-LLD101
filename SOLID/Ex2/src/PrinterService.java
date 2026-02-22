public class PrinterService {
    public StringBuilder out;

    public PrinterService() {
        out = new StringBuilder();
    }

    public void appendTotals(double subtotal, double taxPct, double tax, double discount, double total) {
        out.append(String.format("Subtotal: %.2f\n", subtotal));
        out.append(String.format("Tax(%.0f%%): %.2f\n", taxPct, tax));
        out.append(String.format("Discount: -%.2f\n", discount));
        out.append(String.format("TOTAL: %.2f\n", total));
    }

    public String printAndGetReceipt() {
        String printable = out.toString();
        System.out.print(printable);
        return printable;
    }

    public void reset() {
        out = new StringBuilder();
    }
}