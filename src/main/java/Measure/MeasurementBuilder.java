package Measure;

import Exceptions.ValidationException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MeasurementBuilder {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int systolic;
    private int diastolic;
    private int pulse;

    public MeasurementBuilder withTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public MeasurementBuilder withSystolic(int systolic) {
        this.systolic = systolic;
        return this;
    }

    public MeasurementBuilder withDiastolic(int diastolic) {
        this.diastolic = diastolic;
        return this;
    }

    public MeasurementBuilder withPulse(int pulse) {
        this.pulse = pulse;
        return this;
    }

    public Measurement build() throws ValidationException {
        validate();
        return new Measurement(this);
    }

    private void validate() throws ValidationException {
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
            throw new ValidationException(invalidFields, "Wszystkie wartości muszą być dodatnie");
        }
    }

    LocalDateTime getTimestamp() { return timestamp; }
    int getSystolic() { return systolic; }
    int getDiastolic() { return diastolic; }
    int getPulse() { return pulse; }
}