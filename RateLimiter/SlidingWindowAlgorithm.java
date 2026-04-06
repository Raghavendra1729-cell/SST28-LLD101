import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowAlgorithm implements RateLimiterAlgorithm {

    private final ConcurrentHashMap<String, SlidingData> store = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String key, List<RateLimitRule> rules, int currentTime) {
        synchronized (getLock(key)) {
            for (RateLimitRule rule : rules) {
                SlidingData data = getData(key, rule);
                int windowSize = rule.getWindowSize();
                int currentWindowStart = (currentTime / windowSize) * windowSize;

                if (data.currentWindowStart != currentWindowStart) {
                    if (data.currentWindowStart + windowSize == currentWindowStart) {
                        data.previousCount = data.currentCount;
                    } else {
                        data.previousCount = 0;
                    }
                    data.currentWindowStart = currentWindowStart;
                    data.currentCount = 0;
                }

                int timePassed = currentTime - currentWindowStart;
                double previousWeight = (double) (windowSize - timePassed) / windowSize;
                double totalCount = data.currentCount + (data.previousCount * previousWeight);

                if (totalCount + 1 > rule.getMaxRequests()) {
                    return false;
                }
            }

            for (RateLimitRule rule : rules) {
                SlidingData data = getData(key, rule);
                data.currentCount++;
            }

            return true;
        }
    }

    private Object getLock(String key) {
        return locks.computeIfAbsent(key, k -> new Object());
    }

    private SlidingData getData(String key, RateLimitRule rule) {
        String mapKey = key + ":" + rule.getName();
        return store.computeIfAbsent(mapKey, k -> new SlidingData());
    }

    private static class SlidingData {
        private int currentWindowStart;
        private int previousCount;
        private int currentCount;
    }
}
