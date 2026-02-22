public class PrintingService{
    public void print(PersistenceDb db,StudentRecord st){
        System.out.println("OK: created student " + st.id);
        System.out.println("Saved. Total students: " + db.count());
        System.out.println("CONFIRMATION:");
        System.out.println(st);
    }
}