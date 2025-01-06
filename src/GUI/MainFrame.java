package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends FrameOption {
    public MainFrame() {
        super("Bieda Ciśnienie");
        init();
    }

    @Override
    public void init() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Bieda Ciśnienie");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        frame.add(titleLabel, gbc);

        JButton addButton = new JButton("Dodaj ciśnienie");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(addButton, gbc);

        JButton viewButton = new JButton("Wczytaj ciśnienie");
        gbc.gridx = 1;
        frame.add(viewButton, gbc);

        JButton closeButton = new JButton("Zamknij");
        gbc.gridx = 2;
        frame.add(closeButton, gbc);

        // Akcje przycisków z funkcjami anonimowymi
        addButton.addActionListener(e -> new AddMeasureFrame().show());
        viewButton.addActionListener(e -> new GetMeasureFrame().show());
        closeButton.addActionListener(e -> frame.dispose());

        frame.pack();
        frame.setVisible(true);
    }

    @Override
    protected void show() {

    }
}