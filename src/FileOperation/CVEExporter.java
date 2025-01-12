package FileOperation;

import Measure.Measurement;
import Exceptions.FileOperationException;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CVEExporter implements FileExporter {
    private static final String DELIMITER = ";";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public void export(List<Measurement> measurements, File file) throws FileOperationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Measurement m : measurements) {
                writer.write(String.format("%s%s%d%s%d%s%d%n",
                        m.getTimestamp().format(DATE_FORMATTER),
                        DELIMITER,
                        m.getSystolic(),
                        DELIMITER,
                        m.getDiastolic(),
                        DELIMITER,
                        m.getPulse()));
            }
        } catch (IOException e) {
            throw new FileOperationException(file.getPath(),
                    FileOperationException.OperationType.WRITE,
                    "Błąd podczas zapisu do pliku CVE", e);
        }
    }
}
