public class StartingMechanismFactory {
    public static StartingMechanism createMechanism(String mechanismType) {
        if (mechanismType.equalsIgnoreCase("cap")) {
            return new CapMechanism();
        }
        if (mechanismType.equalsIgnoreCase("click")) {
            return new ClickMechanism();
        }

        throw new IllegalArgumentException("Unsupported starting mechanism: " + mechanismType);
    }
}
