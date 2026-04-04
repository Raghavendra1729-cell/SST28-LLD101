

import java.util.ArrayList;
import java.util.List;

public class DistributedCache {
    private final List<CacheNode> nodes = new ArrayList<>();
    private final DistributionStrategy distributionStrategy;
    private final Database database;

    public DistributedCache(int numberOfNodes, int nodeCapacity, DistributionStrategy distributionStrategy, Database database) {
        this.distributionStrategy = distributionStrategy;
        this.database = database;

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode("node-" + i, nodeCapacity, new LRUEvictionPolicy()));
        }
    }

    public String get(String key) {
        CacheNode node = getNode(key);
        String value = node.get(key);

        if (value != null) {
            return value;
        }

        value = database.get(key);
        if (value != null) {
            node.put(key, value);
        }

        return value;
    }

    public void put(String key, String value) {
        CacheNode node = getNode(key);
        node.put(key, value);

        database.put(key, value);
    }

    public String getNodeIdForKey(String key) {
        return getNode(key).getNodeId();
    }

    private CacheNode getNode(String key) {
        int index = distributionStrategy.selectNode(key, nodes.size());
        return nodes.get(index);
    }
}
