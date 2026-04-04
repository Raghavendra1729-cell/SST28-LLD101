

public class ModuloDistributionStrategy implements DistributionStrategy {
    @Override
    public int selectNode(String key, int numberOfNodes) {
        if (numberOfNodes <= 0) {
            throw new IllegalArgumentException("Number of nodes must be greater than 0");
        }

        return key.hashCode() % numberOfNodes;
    }
}
