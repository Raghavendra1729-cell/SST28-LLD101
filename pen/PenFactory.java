public class PenFactory {
    public static Pen createPen(String penType, String inkColor, String mechanismType) {
        StartingMechanism mechanism = StartingMechanismFactory.createMechanism(mechanismType);

        if (penType.equalsIgnoreCase("ballpoint") ) {
            return new BallpointPen(inkColor, mechanism);
        }
        if (penType.equalsIgnoreCase("gel")) {
            return new GelPen(inkColor, mechanism);
        }
        if (penType.equalsIgnoreCase("ink")) {
            return new InkPen(inkColor, mechanism);
        }

        throw new IllegalArgumentException("Unsupported pen type: " + penType);
    }
}
