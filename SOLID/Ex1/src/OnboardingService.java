import java.util.*;
public class OnboardingService {
    private final PersistenceDb db;
    private InputService is;
    private ValidationService vs;
    private PrintingService ps;
    public OnboardingService(PersistenceDb db) {
        this.db  = db;
        is = new InputService();
        vs = new ValidationService();
        ps = new PrintingService();
    }

    public void registerFromRawInput(String raw) {
        String[] inputFormatted = is.extract(raw);
        StudentRecord st = vs.validate(db,inputFormatted[0],inputFormatted[1],inputFormatted[2],inputFormatted[3]);

        if(st == null){
            return;
        }

        db.save(st);
        ps.print(db, st);
    }
}
