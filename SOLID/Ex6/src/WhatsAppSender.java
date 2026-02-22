public class WhatsAppSender extends NotificationSender {
    public WhatsAppSender(AuditLog audit) { super(audit); }

    @Override
    public boolean requiresPlusInPhone() { 
        return true;
    }

    @Override
    public boolean usesSubject() { 
        return false; 
    }

    @Override
    protected void doSend(Notification n, String finalBody) {
        System.out.println("WA -> to=" + n.phone + " body=" + finalBody);
        audit.add("wa sent");
    }
}