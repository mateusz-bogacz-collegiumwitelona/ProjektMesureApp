package GUI;

import Measure.Measurement;
import FileOperation.CVEStorage;
import Interfaces.MeasurementStorage;
import Exceptions.FileOperationException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class ViewMeasurementsFrame extends AbstractFrame {
    private final MeasurementStorage storage;
    private DefaultTableModel tableModel;
    private List<Measurement> measurements;
    private static final DateTimeFormatter DATE_FORMATTER =
            DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

    private JButton sortButton;
    private JComboBox<String> sortOptions;
    private JButton avgButton;
    private JButton closeButton;

    public ViewMeasurementsFrame() {
        super("Lista pomiarów");
        this.storage = new CVEStorage();
        initComponents();
        setupListeners();
        loadMeasurements();
    }

    @Override
    protected void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        tableModel = new DefaultTableModel(
                new String[]{"Data", "Górne", "Dolne", "Puls"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        addComponent(scrollPane, gbc, 0, 0, 3);

        sortButton = new JButton("Sortuj");
        sortOptions = new JComboBox<>(new String[]{"Górne", "Dolne", "Puls"});
        avgButton = new JButton("Średnie");

        addComponent(sortButton, gbc, 0, 1, 1);
        addComponent(sortOptions, gbc, 1, 1, 1);
        addComponent(avgButton, gbc, 2, 1, 1);
    }

    @Override
    protected void setupListeners() {
        sortButton.addActionListener(e -> sortMeasurements((String)sortOptions.getSelectedItem()));
        avgButton.addActionListener(e -> showAverages());
    }

    private void loadMeasurements() {
        try {
            measurements = storage.loadAll();
            updateTable();
        } catch (FileOperationException e) {
            showError("Błąd odczytu", e.getMessage());
        }
    }

    private void updateTable() {
        tableModel.setRowCount(0);
        measurements.forEach(m -> tableModel.addRow(new Object[]{
                m.getTimestamp().format(DATE_FORMATTER),
                m.getSystolic(),
                m.getDiastolic(),
                m.getPulse()
        }));
    }

    private void sortMeasurements(String criteria) {
        switch (criteria) {
            case "Górne":
                measurements = measurements.stream()
                        .sorted((m1, m2) -> Integer.compare(m1.getSystolic(), m2.getSystolic()))
                        .collect(Collectors.toList());
                break;
            case "Dolne":
                measurements = measurements.stream()
                        .sorted((m1, m2) -> Integer.compare(m1.getDiastolic(), m2.getDiastolic()))
                        .collect(Collectors.toList());
                break;
            case "Puls":
                measurements = measurements.stream()
                        .sorted((m1, m2) -> Integer.compare(m1.getPulse(), m2.getPulse()))
                        .collect(Collectors.toList());
                break;
        }
        updateTable();
    }

    private void showAverages() {
        if (measurements.isEmpty()) {
            showError("Błąd", "Brak danych do obliczenia średnich");
            return;
        }

        double systolicAvg = measurements.stream()
                .mapToInt(Measurement::getSystolic)
                .average()
                .orElse(0);

        double diastolicAvg = measurements.stream()
                .mapToInt(Measurement::getDiastolic)
                .average()
                .orElse(0);

        double pulseAvg = measurements.stream()
                .mapToInt(Measurement::getPulse)
                .average()
                .orElse(0);

        String message = String.format("""
            Średnie wartości:
            Górne: %.2f
            Dolne: %.2f
            Puls: %.2f""",
                systolicAvg, diastolicAvg, pulseAvg);

        showSuccess(message);
    }
}