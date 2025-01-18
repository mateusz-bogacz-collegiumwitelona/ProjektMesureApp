package Exceptions;

public class MeasurementException extends Exception {
    public MeasurementException(String message) {
        super(message);
    }

    public MeasurementException(String message, Throwable cause) {
        super(message, cause);
    }
}