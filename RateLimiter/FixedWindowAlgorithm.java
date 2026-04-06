import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FixedWindowAlgorithm implements RateLimiterAlgorithm {

    private final ConcurrentHashMap<String, WindowData> store = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, Object> locks = new ConcurrentHashMap<>();

    @Override
    public boolean isAllowed(String key, List<RateLimitRule> rules, int currentTime) {
        synchronized (getLock(key)) {
            for (RateLimitRule rule : rules) {
                WindowData data = getData(key, rule);
                int currentWindowStart = (currentTime / rule.getWindowSize()) * rule.getWindowSize();

                if (data.windowStart != currentWindowStart) {
                    data.windowStart = currentWindowStart;
                    data.count = 0;
                }

                if (data.count >= rule.getMaxRequests()) {
                    return false;
                }
            }

            for (RateLimitRule rule : rules) {
                WindowData data = getData(key, rule);
                data.count++;
            }

            return true;
        }
    }

    private Object getLock(String key) {
        return locks.computeIfAbsent(key, k -> new Object());
    }

    private WindowData getData(String key, RateLimitRule rule) {
        String mapKey = key + ":" + rule.getName();
        return store.computeIfAbsent(mapKey, k -> new WindowData());
    }

    private static class WindowData {
        private int windowStart;
        private int count;
    }
}
