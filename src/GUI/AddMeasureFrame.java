package GUI;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import FileOperation.*;
import Measure.MeasureOperations;

class AddMeasureFrame extends FrameOption {
    private JTextField systolicField;
    private JTextField diastolicField;
    private JTextField pulseField;

    private final MeasureOperations operations = new MeasureFileOperations();

    public AddMeasureFrame() {
        super("Dodaj pomiar");
        init();
    }

    @Override
    public void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel systolicLabel = new JLabel("Górne:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(systolicLabel, gbc);

        systolicField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(systolicField, gbc);

        JLabel diastolicLabel = new JLabel("Dolne:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(diastolicLabel, gbc);

        diastolicField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(diastolicField, gbc);

        JLabel pulseLabel = new JLabel("Puls:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(pulseLabel, gbc);

        pulseField = new JTextField(10);
        gbc.gridx = 1;
        frame.add(pulseField, gbc);

        JButton saveButton = new JButton("Zapisz");
        gbc.gridx = 0;
        gbc.gridy = 3;
        frame.add(saveButton, gbc);

        JButton closeButton = new JButton("Zamknij");
        gbc.gridx = 1;
        frame.add(closeButton, gbc);

        saveButton.addActionListener(e -> saveMeasure());
        closeButton.addActionListener(e -> frame.dispose());

        frame.pack();
        frame.setVisible(true);
    }

    private void saveMeasure() {
        try {
            operations.saveMeasure(systolicField.getText(), diastolicField.getText(), pulseField.getText());
            JOptionPane.showMessageDialog(frame, "Pomiar zapisany!", "Sukces", JOptionPane.INFORMATION_MESSAGE);
            systolicField.setText("");
            diastolicField.setText("");
            pulseField.setText("");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Błąd zapisu: " + e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }
    }
}