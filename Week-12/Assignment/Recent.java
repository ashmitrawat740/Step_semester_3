import java.util.*;

public class RecentCache {
    private static class Node {
        int key;
        String page;
        Node prev;
        Node next;

        Node(int key, String page) {
            this.key = key;
            this.page = page;
        }
    }

    private final int capacity;
    private final Map<Integer, Node> map;
    private final Node head; // Most recently used sentinel
    private final Node tail; // Least recently used sentinel

    public RecentCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.head = new Node(-1, "");
        this.tail = new Node(-1, "");
        head.next = tail;
        tail.prev = head;
    }

    public String get(int key) {
        Node node = map.get(key);
        if (node == null) {
            System.out.println("get(" + key + ") -> -1");
            return null;
        }
        moveToFront(node);
        System.out.println("get(" + key + ") -> " + node.page);
        return node.page;
    }

    public void put(int key, String page) {
        Node existing = map.get(key);
        if (existing != null) {
            existing.page = page;
            moveToFront(existing);
            System.out.println("put(" + key + "," + page + ") updated");
            return;
        }

        Node newNode = new Node(key, page);
        map.put(key, newNode);
        addAfterHead(newNode);

        if (map.size() > capacity) {
            Node lru = tail.prev;
            removeNode(lru);
            map.remove(lru.key);
            System.out.println("put(" + key + "," + page + ") evicted " + lru.key);
        } else {
            System.out.println("put(" + key + "," + page + ")");
        }
    }

    public void printCache() {
        List<String> entries = new ArrayList<>();
        Node curr = head.next;
        while (curr != tail) {
            entries.add(curr.key + "=" + curr.page);
            curr = curr.next;
        }
        System.out.println("Cache (most recent first): " + String.join(", ", entries));
    }

    private void addAfterHead(Node node) {
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
        node.prev = null;
        node.next = null;
    }

    private void moveToFront(Node node) {
        removeNode(node);
        addAfterHead(node);
    }

    public static void main(String[] args) {
        RecentCache cache = new RecentCache(2);
        cache.put(1, "Home");
        cache.put(2, "Notes");
        cache.get(1);
        cache.put(3, "Quiz");
        cache.get(2);
        cache.put(1, "Home2");
        cache.put(4, "Mail");
        cache.get(3);
        cache.get(1);
        cache.printCache();
    }
}   