import java.util.*;
public class ValidationService{
    public StudentRecord validate(PersistenceDb db,String name,String email,String phone,String program) {
        List<String> errors = new ArrayList<>();
        if (name.isBlank()) errors.add("name is required");
        if (email.isBlank() || !email.contains("@")) errors.add("email is invalid");
        if (phone.isBlank() || !phone.chars().allMatch(Character::isDigit)) errors.add("phone is invalid");
        if (!(program.equals("CSE") || program.equals("AI") || program.equals("SWE"))) errors.add("program is invalid");

        if (!errors.isEmpty()) {
            System.out.println("ERROR: cannot register");
            for (String e : errors) System.out.println("- " + e);
            return null;
        }
        String id = IdUtil.nextStudentId(db.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);
        return rec;
    }
}