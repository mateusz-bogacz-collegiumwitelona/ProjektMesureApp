package GUI;

import Measure.Measurement;
import FileOperation.CVEStorage;
import FileOperation.MeasurementStorage;
import Exceptions.*;

import javax.swing.*;
import java.awt.*;

public class AddMeasurementFrame extends AbstractFrame {
    private final JTextField systolicField;
    private final JTextField diastolicField;
    private final JTextField pulseField;
    private final MeasurementStorage storage;
    private JButton saveButton;
    private JButton closeButton;

    public AddMeasurementFrame() {
        super("Dodaj pomiar");
        this.storage = new CVEStorage();
        this.systolicField = new JTextField(10);
        this.diastolicField = new JTextField(10);
        this.pulseField = new JTextField(10);
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        addLabelAndField("Górne:", systolicField, gbc, 0);
        addLabelAndField("Dolne:", diastolicField, gbc, 1);
        addLabelAndField("Puls:", pulseField, gbc, 2);

        saveButton = new JButton("Zapisz");
        closeButton = new JButton("Zamknij");

        addComponent(saveButton, gbc, 0, 3, 1);
        addComponent(closeButton, gbc, 1, 3, 1);
    }

    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        addComponent(label, gbc, 0, row, 1);
        addComponent(field, gbc, 1, row, 1);
    }

    @Override
    protected void setupListeners() {
        saveButton.addActionListener(e -> saveMeasurement());
        closeButton.addActionListener(e -> close());

        systolicField.addActionListener(e -> diastolicField.requestFocus());
        diastolicField.addActionListener(e -> pulseField.requestFocus());
        pulseField.addActionListener(e -> saveMeasurement());
    }

    private void saveMeasurement() {
        try {
            validateFields();
            Measurement measurement = createMeasurement();
            storage.save(measurement);
            showSuccess("Pomiar zapisany!");
            clearFields();
            systolicField.requestFocus();
        } catch (MeasurementException e) {
            showError("Błąd", e.getMessage());
        }
    }

    private void validateFields() throws EmptyFieldException {
        if (systolicField.getText().trim().isEmpty()) {
            throw new EmptyFieldException("ciśnienie górne");
        }
        if (diastolicField.getText().trim().isEmpty()) {
            throw new EmptyFieldException("ciśnienie dolne");
        }
        if (pulseField.getText().trim().isEmpty()) {
            throw new EmptyFieldException("puls");
        }
    }

    private Measurement createMeasurement() throws ValidationException {
        return new Measurement.Builder()
                .withSystolic(Integer.parseInt(systolicField.getText().trim()))
                .withDiastolic(Integer.parseInt(diastolicField.getText().trim()))
                .withPulse(Integer.parseInt(pulseField.getText().trim()))
                .build();
    }

    private void clearFields() {
        systolicField.setText("");
        diastolicField.setText("");
        pulseField.setText("");
    }
}