public class InternalService {

    private final RateLimiter rateLimiter;

    public InternalService(RateLimiter rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    public void handleRequest(String tenantId, boolean externalCallNeeded, String payload, int currentTime) {
        System.out.println("Processing request for tenant: " + tenantId);

        if (!externalCallNeeded) {
            System.out.println("External call not needed. No rate limit check.");
            return;
        }

        String key = "tenant:" + tenantId;
        boolean allowed = rateLimiter.isAllowed(key, currentTime);

        if (!allowed) {
            System.out.println("Request denied by rate limiter.");
            return;
        }

        System.out.println("Request allowed.");
        callExternalResource(payload);
    }

    private void callExternalResource(String payload) {
        System.out.println("External resource called with payload: " + payload);
    }
}
