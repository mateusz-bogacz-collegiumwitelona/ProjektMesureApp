package GUI;

public class FrameFactory {
    public static AbstractFrame createFrame(FrameType type) {
        switch (type) {
            case MAIN:
                return new MainFrame();
            case ADD_MEASUREMENT:
                return new AddMeasurementFrame();
            case VIEW_MEASUREMENTS:
                return new ViewMeasurementsFrame();
            case SAVE_OPTIONS:
                return new SaveOptionsFrame();
            default:
                throw new IllegalArgumentException("Unknown frame type: " + type);
        }
    }
}