import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<RateLimitRule> rules = new ArrayList<>();
        rules.add(new RateLimitRule("5 per minute", 5, 60));
        rules.add(new RateLimitRule("100 per hour", 100, 3600));

        runExample("Fixed Window Counter", new FixedWindowAlgorithm(), rules);
        System.out.println();
        runExample("Sliding Window Counter", new SlidingWindowAlgorithm(), rules);
    }

    private static void runExample(String title, RateLimiterAlgorithm algorithm, List<RateLimitRule> rules) {
        System.out.println("=== " + title + " ===");

        RateLimiter rateLimiter = new RateLimiter(algorithm, rules);
        InternalService service = new InternalService(rateLimiter);

        boolean[] requests = {false, true, true, true, true, true, true};

        int currentTime = 0;
        for (int i = 0; i < requests.length; i++) {
            service.handleRequest("T1", requests[i], "request-" + (i + 1), currentTime);
            currentTime += 9;
        }
    }
}
