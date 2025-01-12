package FileOperation;

import Measure.Measurement;
import Exceptions.FileOperationException;
import java.io.File;
import java.util.List;

public interface FileExporter {
    void export(List<Measurement> measurements, File file) throws FileOperationException;
}
