package GUI;

import FileOperation.MeasureFileOperations;
import javax.swing.*;
import java.awt.*;
import java.io.*;

class SaveOptionsFrame extends FrameOption {
    private final MeasureFileOperations operations = new MeasureFileOperations();
    private final JFileChooser fileChooser = new JFileChooser();

    public SaveOptionsFrame() {
        super("Opcje zapisu");
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        init();
    }

    @Override
    public void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JButton csvButton = new JButton("Zapisz do CVE");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(csvButton, gbc);

        JButton txtButton = new JButton("Zapisz do TXT");
        gbc.gridx = 1;
        gbc.gridy = 0;
        frame.add(txtButton, gbc);

        JButton closeButton = new JButton("Zamknij");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        frame.add(closeButton, gbc);

        csvButton.addActionListener(e -> saveToCVE());
        txtButton.addActionListener(e -> saveToTXT());
        closeButton.addActionListener(e -> frame.dispose());

        frame.pack();
    }

    private void saveToCVE() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("measure.cve"));

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".cve")) {
                file = new File(file.getAbsolutePath() + ".cve");
            }

            try {
                operations.saveToCustomCVE(file);
                JOptionPane.showMessageDialog(frame, "Dane zostały zapisane do pliku CVE!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Błąd zapisu do pliku CVE: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveToTXT() {
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setSelectedFile(new File("pomiary.txt"));

        if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            if (!file.getName().toLowerCase().endsWith(".txt")) {
                file = new File(file.getAbsolutePath() + ".txt");
            }

            try {
                operations.saveToCustomTXT(file);
                JOptionPane.showMessageDialog(frame, "Dane zostały zapisane do pliku TXT!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(frame, "Błąd zapisu do pliku TXT: " + ex.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void show() {
        frame.setVisible(true);
    }
}