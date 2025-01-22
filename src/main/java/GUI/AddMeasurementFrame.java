package GUI;

import Exceptions.EmptyFieldException;
import Exceptions.MeasurementException;
import Exceptions.ValidationException;
import Measure.Measurement;
import Measure.MeasurementBuilder;
import FileOperation.CSVStorage;
import Interfaces.MeasurementStorage;

import javax.swing.*;
import java.awt.*;

public class AddMeasurementFrame extends AbstractFrame {
    private final JTextField systolicField;
    private final JTextField diastolicField;
    private final JTextField pulseField;
    private final MeasurementStorage storage;
    private final ViewMeasurementsFrame viewFrame;
    private JButton saveButton;

    public AddMeasurementFrame(ViewMeasurementsFrame viewFrame) {
        super("Dodaj pomiar");
        this.storage = new CSVStorage();
        this.systolicField = new JTextField(10);
        this.diastolicField = new JTextField(10);
        this.pulseField = new JTextField(10);
        this.viewFrame = viewFrame;
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

        saveButton = createButton("Zapisz");

        addComponent(saveButton, gbc, 0, 3, 1);
    }

    private void addLabelAndField(String labelText, JTextField field, GridBagConstraints gbc, int row) {
        JLabel label = new JLabel(labelText);
        addComponent(label, gbc, 0, row, 1);
        addComponent(field, gbc, 1, row, 1);
    }

    @Override
    protected void setupListeners() {
        saveButton.addActionListener(e -> saveMeasurement());

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
            if (viewFrame != null) {
                viewFrame.refreshData();
            }
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
        try {
            return new MeasurementBuilder()
                    .withSystolic(Integer.parseInt(systolicField.getText().trim()))
                    .withDiastolic(Integer.parseInt(diastolicField.getText().trim()))
                    .withPulse(Integer.parseInt(pulseField.getText().trim()))
                    .build();
        } catch (NumberFormatException e) {
            throw new ValidationException(
                    "wartości",
                    systolicField.getText() + "," + diastolicField.getText() + "," + pulseField.getText(),
                    "Wszystkie wartości muszą być liczbami"
            );
        }
    }

    private void clearFields() {
        systolicField.setText("");
        diastolicField.setText("");
        pulseField.setText("");
    }
}