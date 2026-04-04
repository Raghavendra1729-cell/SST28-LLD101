

public class Main {
    public static void main(String[] args) {
        Database database = new InMemoryDatabase();
        database.put("A", "Apple");
        database.put("B", "Ball");

        DistributedCache cache = new DistributedCache(
                3,
                2,
                new ModuloDistributionStrategy(),
                database
        );

        cache.put("C", "Cat");

        System.out.println(cache.get("A"));
        System.out.println(cache.get("B"));
        System.out.println(cache.get("C"));
        System.out.println(cache.getNodeIdForKey("A"));
    }
}
