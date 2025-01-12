package GUI;

import FileOperation.CVEStorage;
import FileOperation.MeasurementStorage;
import FileOperation.CVEExporter;
import FileOperation.TXTExporter;
import Exceptions.FileOperationException;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SaveOptionsFrame extends AbstractFrame {
    private final MeasurementStorage storage;
    private final JFileChooser fileChooser;

    public SaveOptionsFrame() {
        super("Opcje zapisu");
        this.storage = new CVEStorage();
        this.fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton cveButton = new JButton("Zapisz do CVE");
        JButton txtButton = new JButton("Zapisz do TXT");
        JButton closeButton = new JButton("Zamknij");

        addComponent(cveButton, gbc, 0, 0, 1);
        addComponent(txtButton, gbc, 1, 0, 1);
        addComponent(closeButton, gbc, 0, 1, 2);

        cveButton.addActionListener(e -> saveToCVE());
        txtButton.addActionListener(e -> saveToTXT());
        closeButton.addActionListener(e -> close());
    }

    @Override
    protected void setupListeners() {
        // All listeners are set up in initComponents for this frame
    }

    private void saveToCVE() {
        fileChooser.setSelectedFile(new File("measure.cve"));
        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = ensureExtension(fileChooser.getSelectedFile(), ".cve");
            try {
                storage.exportTo(file, new CVEExporter());
                showSuccess("Dane zostały zapisane do pliku CVE!");
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