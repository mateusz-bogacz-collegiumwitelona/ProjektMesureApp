package FileOperation;

import Interfaces.FileExporter;
import Measure.Measurement;
import Exceptions.FileOperationException;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TXTExporter implements FileExporter {
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    @Override
    public void export(List<Measurement> measurements, File file) throws FileOperationException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Pomiary ciśnienia\n");
            writer.write("=================\n\n");

            for (Measurement m : measurements) {
                writer.write(String.format("Data: %s%n", m.getTimestamp().format(DATE_FORMATTER)));
                writer.write(String.format("Ciśnienie górne: %d%n", m.getSystolic()));
                writer.write(String.format("Ciśnienie dolne: %d%n", m.getDiastolic()));
                writer.write(String.format("Puls: %d%n", m.getPulse()));
                writer.write("-------------------------\n");
            }
        } catch (IOException e) {
            throw new FileOperationException(file.getPath(),
                    FileOperationException.OperationType.WRITE,
                    "Błąd podczas zapisu do pliku TXT", e);
        }
    }
}