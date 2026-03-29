class User {
    int id;
    String name;

    User(int id, String name) {
        this.id = id;
        this.name = name;
    }
}

class Customer extends User {
    Customer(int id, String name) {
        super(id, name);
    }
}

class Admin extends User {
    Admin(int id, String name) {
        super(id, name);
    }
}
