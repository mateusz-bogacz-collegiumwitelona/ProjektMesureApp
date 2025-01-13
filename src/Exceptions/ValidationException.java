package Exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends MeasurementException {
    private final Map<String, String> invalidFields;

    public ValidationException(String fieldName, String value, String message) {
        super(message);
        this.invalidFields = new HashMap<>();
        this.invalidFields.put(fieldName, value);
    }

    public ValidationException(Map<String, String> invalidFields, String message) {
        super(message);
        this.invalidFields = new HashMap<>(invalidFields);
    }

    public Map<String, String> getInvalidFields() {
        return new HashMap<>(invalidFields);
    }
}