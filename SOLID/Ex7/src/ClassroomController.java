public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) { this.reg = reg; }

    public void startClass() {
        SmartClassroomDevice pj = reg.getFirstOfType("Projector");
        ((Power)pj).powerOn();
        ((ConnectInput)(pj)).connectInput("HDMI-1");

        SmartClassroomDevice lights = reg.getFirstOfType("LightsPanel");
        ((Brightness)lights).setBrightness(60);

        SmartClassroomDevice ac = reg.getFirstOfType("AirConditioner");
        ((AirConditioner)ac).setTemperatureC(24);

        SmartClassroomDevice scan = reg.getFirstOfType("AttendanceScanner");
        System.out.println("Attendance scanned: present=" + ((Attendance)scan).scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        System.out.println("Shutdown sequence:");
        // FIX: Cast these to (Power) so the compiler knows they have a powerOff() method
        ((Power) reg.getFirstOfType("Projector")).powerOff();
        ((Power) reg.getFirstOfType("LightsPanel")).powerOff();
        ((Power) reg.getFirstOfType("AirConditioner")).powerOff();
    }
}
