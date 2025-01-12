package FileOperation;

import Measure.Measurement;
import Exceptions.*;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CVEStorage implements MeasurementStorage {
    private final String filePath;
    private static final String DEFAULT_PATH = "measure.cve";
    private static final String DELIMITER = ";";
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    public CVEStorage() {
        this(DEFAULT_PATH);
    }

    public CVEStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void save(Measurement measurement) throws FileOperationException {
        try {
            createDirectoryIfNeeded();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(String.format("%s%s%d%s%d%s%d%n",
                        measurement.getTimestamp().format(DATE_FORMATTER),
                        DELIMITER,
                        measurement.getSystolic(),
                        DELIMITER,
                        measurement.getDiastolic(),
                        DELIMITER,
                        measurement.getPulse()));
            }
        } catch (IOException e) {
            throw new FileOperationException(filePath,
                    FileOperationException.OperationType.WRITE,
                    "Nie udało się zapisać pomiaru", e);
        }
    }

    @Override
    public List<Measurement> loadAll() throws FileOperationException {
        List<Measurement> measurements = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) {
            return measurements;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split(DELIMITER);
                    Measurement measurement = new Measurement.Builder()
                            .withTimestamp(LocalDateTime.parse(parts[0], DATE_FORMATTER))
                            .withSystolic(Integer.parseInt(parts[1]))
                            .withDiastolic(Integer.parseInt(parts[2]))
                            .withPulse(Integer.parseInt(parts[3]))
                            .build();
                    measurements.add(measurement);
                } catch (ValidationException | NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    // Pomijamy nieprawidłowe pomiary bez wyświetlania komunikatów
                    continue;
                }
            }
            return measurements;
        } catch (IOException e) {
            throw new FileOperationException(filePath,
                    FileOperationException.OperationType.READ,
                    "Błąd podczas odczytu pliku z pomiarami", e);
        }
    }

    @Override
    public void exportTo(File file, FileExporter exporter) throws FileOperationException {
        List<Measurement> measurements = loadAll();
        exporter.export(measurements, file);
    }

    private void createDirectoryIfNeeded() throws FileOperationException {
        File file = new File(filePath);
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            if (!directory.mkdirs()) {
                throw new FileOperationException(directory.getPath(),
                        FileOperationException.OperationType.CREATE,
                        "Nie można utworzyć katalogu dla pliku");
            }
        }
    }
}