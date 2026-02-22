public class AttendanceRule  extends RuleInput implements EligibilityRule{
    @Override
    public String evaluate(StudentProfile s) {
        if (s.attendancePct < super.minAttendance) {
            return "attendance below " + super.minAttendance;
        }
        return null;
    }
}