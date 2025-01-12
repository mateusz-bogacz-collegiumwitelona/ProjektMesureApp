package FileOperation;

import Measure.Measurement;
import Exceptions.FileOperationException;

import java.io.File;
import java.util.List;

public interface MeasurementStorage {
    void save(Measurement measurement) throws FileOperationException;
    List<Measurement> loadAll() throws FileOperationException;
    void exportTo(File file, FileExporter exporter) throws FileOperationException;
}