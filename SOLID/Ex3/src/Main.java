
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Placement Eligibility ===");
        StudentProfile s = new StudentProfile("23BCS1001", "Ayaan", 8.10, 72, 18, LegacyFlags.NONE);
        PersistenceDb db = new FakeEligibilityStore();
        EligibilityRule attendence = new AttendanceRule();
        EligibilityRule cgr = new CgrRule();
        EligibilityRule  Credits = new CreditsRule();
        EligibilityRule Disciplinary  = new DisciplinaryRule();

        ArrayList<EligibilityRule> list = new ArrayList<>(Arrays.asList(attendence,cgr,Credits,Disciplinary));

        EligibilityEngine engine = new EligibilityEngine(db,list);

        engine.runAndPrint(s);
    }
}
