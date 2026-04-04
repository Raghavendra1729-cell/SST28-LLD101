

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements Database {
    private final Map<String, String> storage = new HashMap<>();

    @Override
    public String get(String key) {
        return storage.get(key);
    }

    @Override
    public void put(String key, String value) {
        storage.put(key, value);
    }
}
