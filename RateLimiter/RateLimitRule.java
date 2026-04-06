public class RateLimitRule {

    private final String name;
    private final int maxRequests;
    private final int windowSize;

    public RateLimitRule(String name, int maxRequests, int windowSize) {
        this.name = name;
        this.maxRequests = maxRequests;
        this.windowSize = windowSize;
    }

    public String getName() {
        return name;
    }

    public int getMaxRequests() {
        return maxRequests;
    }

    public int getWindowSize() {
        return windowSize;
    }
}
