package GUI;

import FileOperation.MeasureFileOperations;
import Measure.MeasureOperations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

class GetMeasureFrame extends FrameOption {
    private final MeasureOperations operations = new MeasureFileOperations();

    public GetMeasureFrame() {
        super("Lista pomiarów");
        init();
    }

    @Override
    public void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Data", "Górne", "Dolne", "Puls"}, 0);
        table.setModel(model);

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

        JButton closeButton = new JButton("Zamknij");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(closeButton, gbc);

        closeButton.addActionListener(e -> frame.dispose());

        frame.pack();
        frame.setVisible(true);
    }
}