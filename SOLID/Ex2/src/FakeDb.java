public class FakeDb implements PersistenceDb {
    StorageFake s;

    public FakeDb(StorageFake s) {
        this.s = s;
    }

    @Override
    public void save(MenuItem m) {
        s.save(m);
    }

    @Override
    public MenuItem getItem(String id) {
        return s.getItem(id);
    }
}