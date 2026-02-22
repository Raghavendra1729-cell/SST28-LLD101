public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    public boolean usesSubject() { 
        return false;
    }

    @Override
    protected void doSend(Notification n, String finalBody) {
        System.out.println("SMS -> to=" + n.phone + " body=" + finalBody);
        audit.add("sms sent");
    }
}