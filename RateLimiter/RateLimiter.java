import java.util.ArrayList;
import java.util.List;

public class RateLimiter {

    private final RateLimiterAlgorithm algorithm;
    private final List<RateLimitRule> rules;

    public RateLimiter(RateLimiterAlgorithm algorithm, List<RateLimitRule> rules) {
        this.algorithm = algorithm;
        this.rules = new ArrayList<>(rules);
    }

    public boolean isAllowed(String key, int currentTime) {
        return algorithm.isAllowed(key, rules, currentTime);
    }
}
