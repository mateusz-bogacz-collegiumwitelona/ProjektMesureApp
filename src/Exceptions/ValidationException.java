package Exceptions;

public class ValidationException extends MeasurementException {
    private final int systolic;
    private final int diastolic;
    private final int pulse;

    public ValidationException(String message, int systolic, int diastolic, int pulse) {
        super(message);
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    public int getSystolic() { return systolic; }
    public int getDiastolic() { return diastolic; }
    public int getPulse() { return pulse; }
}