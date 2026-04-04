import java.util.HashMap;
import java.util.Map;

public class LRUEvictionPolicy implements EvictionPolicy {
    private static class Node {
        String key;
        Node prev;
        Node next;

        Node(String key) {
            this.key = key;
        }
    }

    private final Map<String, Node> map = new HashMap<>();
    private final Node head;
    private final Node tail;

    public LRUEvictionPolicy() {
        head = new Node("-1");
        tail = new Node("-1");
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void keyAccessed(String key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            remove(node);
            addAfterHead(node);
            return;
        }

        Node node = new Node(key);
        map.put(key, node);
        addAfterHead(node);
    }

    @Override
    public String getKeyToEvict() {
        if (tail.prev == head) {
            return null;
        }
        return tail.prev.key;
    }

    @Override
    public void removeKey(String key) {
        Node node = map.remove(key);
        if (node != null) {
            remove(node);
        }
    }

    private void addAfterHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }
}
