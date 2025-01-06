package FileOperation;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import Measure.*;

public class MeasureFileOperations implements MeasureOperations {
    private final String filePath = "measure.csv";

    @Override
    public synchronized void saveMeasure(String systolic, String diastolic, String pulse) throws IOException {
        File file = new File(filePath);
        boolean isNewFile = file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.write("Date,Systolic,Diastolic,Pulse\n");
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            writer.write(String.format("%s,%s,%s,%s\n", timestamp, systolic, diastolic, pulse));
        }
    }

    @Override
    public synchronized List<String[]> loadMeasures() throws IOException {
        List<String[]> measures = new ArrayList<>();
        if (!Files.exists(Paths.get(filePath))) {
            return measures;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                measures.add(line.split(","));
            }
        }
        return measures;
    }
}