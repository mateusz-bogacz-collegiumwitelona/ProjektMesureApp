package FileOperation;

import Exceptions.EmptyFieldException;
import Exceptions.FileOperationException;
import Exceptions.ValidationException;
import Interfaces.MeasureOperations;
import Measure.Measurement;
import Measure.MeasurementBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MeasureFileOperations implements MeasureOperations {
    private final String cveFilePath;
    private static final String DEFAULT_FILE_PATH = "measure.cve";
    private static final String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";
    private static final String CSV_DELIMITER = ";";
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public MeasureFileOperations() {
        this(DEFAULT_FILE_PATH);
    }

    public MeasureFileOperations(String filePath) {
        this.cveFilePath = filePath;
    }

    @Override
    public synchronized void saveMeasure(String systolic, String diastolic, String pulse)
            throws EmptyFieldException, ValidationException,
            ValidationException, FileOperationException {
        if (systolic == null || systolic.trim().isEmpty()) {
            throw new EmptyFieldException("ciśnienie górne");
        }
        if (diastolic == null || diastolic.trim().isEmpty()) {
            throw new EmptyFieldException("ciśnienie dolne");
        }
        if (pulse == null || pulse.trim().isEmpty()) {
            throw new EmptyFieldException("puls");
        }

        try {
            Integer.parseInt(systolic.trim());
            Integer.parseInt(diastolic.trim());
            Integer.parseInt(pulse.trim());
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "wartość",
                    systolic + "," + diastolic + "," + pulse,
                    "Wszystkie wartości muszą być liczbami"
            );
        }

        try {
            saveToCVE(systolic, diastolic, pulse);
        } catch (IOException e) {
            throw new FileOperationException(cveFilePath,
                    FileOperationException.OperationType.WRITE,
                    "Nie udało się zapisać pomiaru", e);
        }
    }

    private void saveToCVE(String systolic, String diastolic, String pulse)
            throws IOException, FileOperationException {
        File file = new File(cveFilePath);
        boolean isNewFile = !file.exists();

        if (isNewFile) {
            createDirectory(file);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String timestamp = LocalDateTime.now().format(dateFormatter);
            String measureLine = String.format("%s%s%s%s%s%s%s%n",
                    timestamp, CSV_DELIMITER,
                    systolic, CSV_DELIMITER,
                    diastolic, CSV_DELIMITER,
                    pulse);
            writer.write(measureLine);
        }
    }

    private void createDirectory(File file) throws FileOperationException {
        File directory = file.getParentFile();
        if (directory != null && !directory.exists()) {
            if (!directory.mkdirs()) {
                throw new FileOperationException(
                        directory.getPath(),
                        FileOperationException.OperationType.CREATE,
                        "Nie można utworzyć katalogu dla pliku"
                );
            }
        }
    }

    @Override
    public synchronized List<String[]> loadMeasures() throws FileOperationException {
        List<String[]> measures = new ArrayList<>();
        if (!Files.exists(Paths.get(cveFilePath))) {
            return measures;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(cveFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                measures.add(line.split(CSV_DELIMITER));
            }
            return measures;
        } catch (IOException e) {
            throw new FileOperationException(
                    cveFilePath,
                    FileOperationException.OperationType.READ,
                    "Błąd podczas odczytu pliku z pomiarami",
                    e
            );
        }
    }

    public String getFilePath() {
        return cveFilePath;
    }

    @Override
    public synchronized void saveToCustomCVE(File file) throws FileOperationException {
        List<String[]> measures;
        try {
            measures = loadMeasures();
        } catch (FileOperationException e) {
            throw new FileOperationException(file.getPath(),
                    FileOperationException.OperationType.READ,
                    "Nie udało się odczytać danych do eksportu", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] measure : measures) {
                writer.write(String.join(CSV_DELIMITER, measure) + "\n");
            }
        } catch (IOException e) {
            throw new FileOperationException(file.getPath(),
                    FileOperationException.OperationType.WRITE,
                    "Błąd podczas zapisu do pliku CVE", e);
        }
    }

    @Override
    public synchronized void saveToCustomTXT(File file) throws FileOperationException {
        // Wczytaj surowe dane
        List<String[]> rawMeasures = loadMeasures();
        // Przekonwertuj na obiekty Measurement
        List<Measurement> measurements = convertToMeasurements(rawMeasures);
        // Użyj TXTExporter do zapisu
        TXTExporter exporter = new TXTExporter();
        exporter.export(measurements, file);
    }

    private List<Measurement> convertToMeasurements(List<String[]> rawMeasures) throws FileOperationException {
        List<Measurement> measurements = new ArrayList<>();
        for (String[] measure : rawMeasures) {
            try {
                Measurement measurement = new MeasurementBuilder()
                        .withTimestamp(LocalDateTime.parse(measure[0], dateFormatter))
                        .withSystolic(Integer.parseInt(measure[1]))
                        .withDiastolic(Integer.parseInt(measure[2]))
                        .withPulse(Integer.parseInt(measure[3]))
                        .build();
                measurements.add(measurement);
            } catch (Exception e) {
                throw new FileOperationException(cveFilePath,
                        FileOperationException.OperationType.READ,
                        "Błąd podczas konwersji danych: " + e.getMessage(), e);
            }
        }
        return measurements;
    }
}