
public class CgrRule extends RuleInput implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.cgr < super.minCgr) {
            return "CGR below " + super.minCgr;
        }
        return null;
    }
}