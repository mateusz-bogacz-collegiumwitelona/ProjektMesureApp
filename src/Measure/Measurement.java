package Measure;

import Exceptions.ValidationException;
import java.time.LocalDateTime;

public class Measurement {
    private final LocalDateTime timestamp;
    private final int systolic;
    private final int diastolic;
    private final int pulse;

    private Measurement(Builder builder) {
        this.timestamp = builder.timestamp;
        this.systolic = builder.systolic;
        this.diastolic = builder.diastolic;
        this.pulse = builder.pulse;
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getSystolic() { return systolic; }
    public int getDiastolic() { return diastolic; }
    public int getPulse() { return pulse; }

    public static class Builder {
        private LocalDateTime timestamp = LocalDateTime.now();
        private int systolic;
        private int diastolic;
        private int pulse;

        public Builder withTimestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Builder withSystolic(int systolic) {
            this.systolic = systolic;
            return this;
        }

        public Builder withDiastolic(int diastolic) {
            this.diastolic = diastolic;
            return this;
        }

        public Builder withPulse(int pulse) {
            this.pulse = pulse;
            return this;
        }

        public Measurement build() throws ValidationException {
            validate();
            return new Measurement(this);
        }

        private void validate() throws ValidationException {
            if (systolic <= 0 || diastolic <= 0 || pulse <= 0) {
                throw new ValidationException("Wszystkie wartości muszą być dodatnie",
                        systolic, diastolic, pulse);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Pomiar [data=%s, górne=%d, dolne=%d, puls=%d]",
                timestamp, systolic, diastolic, pulse);
    }
}