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
    private final String csvFilePath = "measure.csv";

    @Override
    public synchronized void saveMeasure(String systolic, String diastolic, String pulse) throws IOException {
        saveToCSV(systolic, diastolic, pulse);
    }

    private void saveToCSV(String systolic, String diastolic, String pulse) throws IOException {
        File file = new File(csvFilePath);
        boolean isNewFile = file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            if (isNewFile) {
                writer.write("Date,Systolic,Diastolic,Pulse\n");
            }
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            writer.write(String.format("%s,%s,%s,%s\n", timestamp, systolic, diastolic, pulse));
        }
    }

    public synchronized void synchronizeTxtWithCsv() throws IOException {
        // Sprawdzanie, czy plik CSV istnieje
        if (!Files.exists(Paths.get(csvFilePath))) {
            return;
        }

        // Czyszczenie lub tworzenie pliku TXT
        String txtFilePath = "measure.txt";
        try (BufferedWriter txtWriter = new BufferedWriter(new FileWriter(txtFilePath))) {
            txtWriter.write("Measure\n");
            txtWriter.write("=========================\n");

            // Odczytywanie danych z CSV i zapisywanie do TXT
            try (BufferedReader csvReader = new BufferedReader(new FileReader(csvFilePath))) {
                csvReader.readLine(); // Pominięcie nagłówka CSV
                String line;
                while ((line = csvReader.readLine()) != null) {
                    String[] parts = line.split(",");
                    String timestamp = parts[0];
                    String systolic = parts[1];
                    String diastolic = parts[2];
                    String pulse = parts[3];

                    txtWriter.write(String.format("Date: %s\nSystolic: %s\nDiastolic: %s\nPulse: %s\n",
                            timestamp, systolic, diastolic, pulse));
                    txtWriter.write("-------------------------\n");
                }
            }
        }
    }


    @Override
    public synchronized List<String[]> loadMeasures() throws IOException {
        List<String[]> measures = new ArrayList<>();
        if (!Files.exists(Paths.get(csvFilePath))) {
            return measures;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            reader.readLine(); // Skip header
            while ((line = reader.readLine()) != null) {
                measures.add(line.split(","));
            }
        }
        return measures;
    }
}