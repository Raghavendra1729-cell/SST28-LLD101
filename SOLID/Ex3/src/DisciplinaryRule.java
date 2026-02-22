public class DisciplinaryRule extends RuleInput implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.disciplinaryFlag != super.disciplinaryFlag) {
            return "disciplinary flag present";
        }
        return null;
    }
}

