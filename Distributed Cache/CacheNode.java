

import java.util.HashMap;
import java.util.Map;

public class CacheNode {
    private final String nodeId;
    private final int capacity;
    private final Map<String, String> cache = new HashMap<>();
    private final EvictionPolicy evictionPolicy;

    public CacheNode(String nodeId, int capacity, EvictionPolicy evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.evictionPolicy = evictionPolicy;
    }

    public String get(String key) {
        if (cache.containsKey(key)) {
            evictionPolicy.keyAccessed(key);
            return cache.get(key);
        }

        return null;
    }

    public void put(String key, String value) {
        if (!cache.containsKey(key) && cache.size() >= capacity) {
            String keyToRemove = evictionPolicy.getKeyToEvict();
            if (keyToRemove != null) {
                cache.remove(keyToRemove);
                evictionPolicy.removeKey(keyToRemove);
            }
        }

        cache.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    public String getNodeId() {
        return nodeId;
    }
}
