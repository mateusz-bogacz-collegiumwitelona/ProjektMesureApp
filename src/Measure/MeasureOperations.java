package Measure;

import java.io.File;
import java.io.IOException;
import java.util.List;
import Exceptions.*;

public interface MeasureOperations {
    void saveMeasure(String systolic, String diastolic, String pulse) throws MeasurementException;
    List<String[]> loadMeasures() throws FileOperationException;
    void saveToCustomCVE(File file) throws FileOperationException;
    void saveToCustomTXT(File file) throws FileOperationException;
}