package Measure;

import java.io.IOException;
import java.util.List;

public interface MeasureOperations {
    void saveMeasure(String systolic, String diastolic, String pulse) throws IOException;
    List<String[]> loadMeasures() throws IOException;
}