class HashMapDb implements PersistenceDb {
    FakeDb db;

    HashMapDb(FakeDb db) {
        this.db = db;
    }

    @Override
    public void save(StudentRecord r) {
        db.save(r);
    }

    @Override
    public int count() {
        return db.count();
    }
}