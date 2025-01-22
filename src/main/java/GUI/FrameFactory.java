package GUI;

public class FrameFactory {
    private static ViewMeasurementsFrame viewMeasurementsFrame;

    public static AbstractFrame createFrame(FrameType type) {
        switch (type) {
            case MAIN:
                return new MainFrame();
            case ADD_MEASUREMENT:
                if (viewMeasurementsFrame == null) {
                    viewMeasurementsFrame = new ViewMeasurementsFrame();
                }
                return new AddMeasurementFrame(viewMeasurementsFrame);
            case VIEW_MEASUREMENTS:
                if (viewMeasurementsFrame == null) {
                    viewMeasurementsFrame = new ViewMeasurementsFrame();
                }
                return viewMeasurementsFrame;
            case SAVE_OPTIONS:
                return new SaveOptionsFrame();
            default:
                throw new IllegalArgumentException("Unknown frame type: " + type);
        }
    }
}