import java.util.*;

class StorageFake {
    Map<String, MenuItem> menu;

    public StorageFake() {
        menu = new LinkedHashMap<>();
    }

    public void save(MenuItem i) {
        menu.put(i.id, i);
    }

    public MenuItem getItem(String id) {
        return menu.getOrDefault(id, null);
    }
}