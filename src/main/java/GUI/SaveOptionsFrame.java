package GUI;

import FileOperation.CSVStorage;
import Interfaces.MeasurementStorage;
import FileOperation.CSVExporter;
import FileOperation.TXTExporter;
import Exceptions.FileOperationException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveOptionsFrame extends AbstractFrame {
    private final MeasurementStorage storage;
    private final JFileChooser fileChooser;
    private JButton csvButton;
    private JButton txtButton;
    private JButton closeButton;

    public SaveOptionsFrame() {
        super("Opcje zapisu");
        this.storage = new CSVStorage();
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        csvButton = createButton("Zapisz do CSV");
        txtButton = createButton("Zapisz do TXT");

        addComponent(csvButton, gbc, 0, 0, 1);
        addComponent(txtButton, gbc, 0, 1, 1);
    }

    @Override
    protected void setupListeners() {
        csvButton.addActionListener(e -> saveToCSV());
        txtButton.addActionListener(e -> saveToTXT());
    }

    private void saveToCSV() {
        fileChooser.setSelectedFile(new File("measure.csv"));
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = ensureExtension(fileChooser.getSelectedFile(), ".csv");
            try {
                storage.exportTo(file, new CSVExporter());
                showSuccess("Dane zostały zapisane do pliku CSV!");
            } catch (FileOperationException ex) {
                showError("Błąd zapisu", ex.getMessage());
            }
        }
    }

    private void saveToTXT() {
        fileChooser.setSelectedFile(new File("pomiary.txt"));
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = ensureExtension(fileChooser.getSelectedFile(), ".txt");
            try {
                storage.exportTo(file, new TXTExporter());
                showSuccess("Dane zostały zapisane do pliku TXT!");
            } catch (FileOperationException ex) {
                showError("Błąd zapisu", ex.getMessage());
            }
        }
    }

    private File ensureExtension(File file, String extension) {
        if (!file.getName().toLowerCase().endsWith(extension)) {
            return new File(file.getAbsolutePath() + extension);
        }
        return file;
    }
}