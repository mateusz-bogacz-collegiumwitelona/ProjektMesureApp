// Measurement.java
package Measure;

import java.time.LocalDateTime;

public class Measurement {
    private final LocalDateTime timestamp;
    private final int systolic;
    private final int diastolic;
    private final int pulse;

    Measurement(MeasurementBuilder builder) {
        this.timestamp = builder.getTimestamp();
        this.systolic = builder.getSystolic();
        this.diastolic = builder.getDiastolic();
        this.pulse = builder.getPulse();
    }

    public LocalDateTime getTimestamp() { return timestamp; }
    public int getSystolic() { return systolic; }
    public int getDiastolic() { return diastolic; }
    public int getPulse() { return pulse; }

    @Override
    public String toString() {
        return String.format("Pomiar [data=%s, górne=%d, dolne=%d, puls=%d]",
                timestamp, systolic, diastolic, pulse);
    }
}