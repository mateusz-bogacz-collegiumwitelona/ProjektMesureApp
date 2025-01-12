package Exceptions;

public class InvalidMeasurementValueException extends MeasurementException {
    private final String fieldName;
    private final String value;

    public InvalidMeasurementValueException(String fieldName, String value, String message) {
        super(message);
        this.fieldName = fieldName;
        this.value = value;
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }
}