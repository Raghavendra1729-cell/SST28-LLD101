public abstract class NotificationSender {
    protected final AuditLog audit;
    
    protected NotificationSender(AuditLog audit) { 
        this.audit = audit; 
    }
    
    public final void send(Notification n) {
        if (requiresPlusInPhone() && (n.phone == null || !n.phone.startsWith("+"))) {
            throw new IllegalArgumentException("phone must start with + and country code");
        }
        String finalBody = n.body;
        if (finalBody != null && finalBody.length() > getMaxLen()) {
            finalBody = finalBody.substring(0, getMaxLen());
        }

        doSend(n, finalBody);
    }
    protected abstract void doSend(Notification n, String finalBody);

    public int getMaxLen() { return Integer.MAX_VALUE; }
    public boolean requiresPlusInPhone() { return false; }
    public boolean usesSubject() { return true; }
}