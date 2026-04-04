

public interface DistributionStrategy {
    int selectNode(String key, int numberOfNodes);
}
