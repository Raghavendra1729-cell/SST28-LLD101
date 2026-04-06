import java.util.List;

public interface RateLimiterAlgorithm {

    boolean isAllowed(String key, List<RateLimitRule> rules, int currentTime);
}
