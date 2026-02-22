public class FakeEligibilityStore implements PersistenceDb{
    public void save(String roll, String status) {
        System.out.println("Saved evaluation for roll=" + roll);
    }
}
