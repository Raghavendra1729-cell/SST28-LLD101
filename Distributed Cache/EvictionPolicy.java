

public interface EvictionPolicy {
    void keyAccessed(String key);

    String getKeyToEvict();

    void removeKey(String key);
}
