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
    private final String cveFilePath = "measure.cve";

    @Override
    public synchronized void saveMeasure(String systolic, String diastolic, String pulse) throws IOException {
        saveToCVE(systolic, diastolic, pulse);
    }

    private void saveToCVE(String systolic, String diastolic, String pulse) throws IOException {
        File file = new File(cveFilePath);
        boolean isNewFile = !file.exists();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            writer.write(String.format("%s;%s;%s;%s\n", timestamp, systolic, diastolic, pulse));
        }
    }

    public synchronized void saveToCustomCVE(File file) throws IOException {
        List<String[]> measures = loadMeasures();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String[] measure : measures) {
                writer.write(String.format("%s;%s;%s;%s\n",
                        measure[0], // data
                        measure[1], // systolic
                        measure[2], // diastolic
                        measure[3]  // pulse
                ));
            }
        }
    }

    public synchronized void saveToCustomTXT(File file) throws IOException {
        List<String[]> measures = loadMeasures();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("Pomiary ciśnienia\n");
            writer.write("=================\n\n");
            for (String[] measure : measures) {
                writer.write(String.format("Data: %s\nCiśnienie górne: %s\nCiśnienie dolne: %s\nPuls: %s\n",
                        measure[0], // data
                        measure[1], // systolic
                        measure[2], // diastolic
                        measure[3]  // pulse
                ));
                writer.write("-------------------------\n");
            }
        }
    }

    @Override
    public synchronized List<String[]> loadMeasures() throws IOException {
        List<String[]> measures = new ArrayList<>();
        if (!Files.exists(Paths.get(cveFilePath))) {
            return measures;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(cveFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                measures.add(line.split(";"));
            }
        }
        return measures;
    }
}