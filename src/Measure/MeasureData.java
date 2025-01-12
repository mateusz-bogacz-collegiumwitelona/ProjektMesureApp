package Measure;

public class MeasureData {
    private final String date;
    private final int systolic;
    private final int diastolic;
    private final int pulse;

    public MeasureData(String date, int systolic, int diastolic, int pulse) {
        this.date = date;
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.pulse = pulse;
    }

    public String getDate() { return date; }
    public int getSystolic() { return systolic; }
    public int getDiastolic() { return diastolic; }
    public int getPulse() { return pulse; }

    public String[] toArray() {
        return new String[]{date, String.valueOf(systolic),
                String.valueOf(diastolic), String.valueOf(pulse)};
    }
}