public class CreditsRule extends RuleInput implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s) {
        if (s.earnedCredits < super.minCredits) {
            return "credits below " + super.minCredits;
        }
        return null;
    }
}