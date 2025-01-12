package FileOperation;

import Exceptions.ValidationException;

public class MeasurementValidator {
    public static void validate(int systolic, int diastolic, int pulse) throws ValidationException {
        StringBuilder errors = new StringBuilder();

        // Sprawdzamy tylko czy wartości są dodatnie
        if (systolic <= 0) {
            errors.append("Ciśnienie górne musi być dodatnie\n");
        }
        if (diastolic <= 0) {
            errors.append("Ciśnienie dolne musi być dodatnie\n");
        }
        if (pulse <= 0) {
            errors.append("Puls musi być dodatni\n");
        }

        if (errors.length() > 0) {
            throw new ValidationException(errors.toString().trim(), systolic, diastolic, pulse);
        }
    }
}