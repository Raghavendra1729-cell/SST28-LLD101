interface PersistenceDb {
    void save(MenuItem m);
    MenuItem getItem(String id);
}