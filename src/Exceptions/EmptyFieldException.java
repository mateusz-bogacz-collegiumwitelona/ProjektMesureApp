package Exceptions;

public class EmptyFieldException extends MeasurementException {
    private final String fieldName;

    public EmptyFieldException(String fieldName) {
        super("Pole " + fieldName + " nie może być puste");
        this.fieldName = fieldName;
    }

    public String getFieldName() { return fieldName; }
}