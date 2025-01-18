package FileOperation;

import Exceptions.ValidationException;
import java.util.HashMap;
import java.util.Map;

public class MeasurementValidator {
    public static void validate(int systolic, int diastolic, int pulse) throws ValidationException {
        Map<String, String> invalidFields = new HashMap<>();

        if (systolic <= 0) {
            invalidFields.put("ciśnienie górne", String.valueOf(systolic));
        }
        if (diastolic <= 0) {
            invalidFields.put("ciśnienie dolne", String.valueOf(diastolic));
        }
        if (pulse <= 0) {
            invalidFields.put("puls", String.valueOf(pulse));
        }

        if (!invalidFields.isEmpty()) {
            String message = "Następujące wartości są nieprawidłowe (muszą być dodatnie):\n" +
                    formatInvalidFields(invalidFields);
            throw new ValidationException(invalidFields, message);
        }
    }

    private static String formatInvalidFields(Map<String, String> invalidFields) {
        StringBuilder sb = new StringBuilder();
        invalidFields.forEach((field, value) ->
                sb.append(field).append(": ").append(value).append("\n"));
        return sb.toString().trim();
    }
}