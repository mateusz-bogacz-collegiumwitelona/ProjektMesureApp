package GUI;

import FileOperation.MeasureFileOperations;
import Measure.MeasureOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class GetMeasureFrame extends FrameOption {
    private final MeasureOperations operations = new MeasureFileOperations();
    private DefaultTableModel model;

    public GetMeasureFrame() {
        super("Lista pomiarów");
        init();
    }

    @Override
    public void init() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        model = new DefaultTableModel(new String[]{"Data", "Górne", "Dolne", "Puls"}, 0);
        JTable table = new JTable(model);

        try {
            for (String[] measure : operations.loadMeasures()) {
                model.addRow(measure);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd odczytu: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        frame.add(scrollPane, gbc);

        JButton sortButton = new JButton("Sortuj");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(sortButton, gbc);

        JComboBox<String> sortOptions = new JComboBox<>(new String[]{"Górne", "Dolne", "Puls"});
        gbc.gridx = 1;
        frame.add(sortOptions, gbc);

        JButton avgButton = new JButton("Średnie");
        gbc.gridx = 2;
        frame.add(avgButton, gbc);

        JButton closeButton = new JButton("Zamknij");
        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(closeButton, gbc);

        sortButton.addActionListener(e -> sortTable(sortOptions.getSelectedItem().toString()));
        avgButton.addActionListener(e -> calculateAverages());
        closeButton.addActionListener(e -> frame.dispose());

        frame.pack();
        frame.setVisible(true);
    }

    private void sortTable(String criteria) {
        int columnIndex;
        switch (criteria) {
            case "Górne":
                columnIndex = 1;
                break;
            case "Dolne":
                columnIndex = 2;
                break;
            case "Puls":
                columnIndex = 3;
                break;
            default:
                return;
        }

        List<String[]> data = new ArrayList<>();
        for (int i = 0; i < model.getRowCount(); i++) {
            String[] row = new String[model.getColumnCount()];
            for (int j = 0; j < model.getColumnCount(); j++) {
                row[j] = model.getValueAt(i, j).toString();
            }
            data.add(row);
        }

        data.sort(Comparator.comparingInt(o -> Integer.parseInt(o[columnIndex])));

        model.setRowCount(0);
        for (String[] row : data) {
            model.addRow(row);
        }
    }

    private void calculateAverages() {
        int systolicSum = 0, diastolicSum = 0, pulseSum = 0;
        int count = model.getRowCount();

        if (count == 0) {
            JOptionPane.showMessageDialog(frame, "Brak danych do obliczenia średnich", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        for (int i = 0; i < count; i++) {
            systolicSum += Integer.parseInt(model.getValueAt(i, 1).toString());
            diastolicSum += Integer.parseInt(model.getValueAt(i, 2).toString());
            pulseSum += Integer.parseInt(model.getValueAt(i, 3).toString());
        }

        double systolicAvg = (double) systolicSum / count;
        double diastolicAvg = (double) diastolicSum / count;
        double pulseAvg = (double) pulseSum / count;

        JOptionPane.showMessageDialog(frame,
                String.format("Średnie wartości:\nGórne: %.2f\nDolne: %.2f\nPuls: %.2f",
                        systolicAvg, diastolicAvg, pulseAvg),
                "Średnie", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void show() {
        frame.setVisible(true);
    }
}