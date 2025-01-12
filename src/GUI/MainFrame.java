package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends AbstractFrame {
    private JButton addButton;
    private JButton viewButton;
    private JButton saveOptionsButton;
    private JButton closeButton;

    public MainFrame() {
        super("Bieda Ciśnienie");
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Bieda Ciśnienie");
        addComponent(titleLabel, gbc, 0, 0, 4);

        addButton = new JButton("Dodaj ciśnienie");
        viewButton = new JButton("Wczytaj ciśnienie");
        saveOptionsButton = new JButton("Opcje zapisu");
        closeButton = new JButton("Zamknij");

        addComponent(addButton, gbc, 0, 1, 1);
        addComponent(viewButton, gbc, 1, 1, 1);
        addComponent(saveOptionsButton, gbc, 2, 1, 1);
        addComponent(closeButton, gbc, 3, 1, 1);
    }

    @Override
    protected void setupListeners() {
        addButton.addActionListener(e -> FrameFactory.createFrame(FrameType.ADD_MEASUREMENT).show());
        viewButton.addActionListener(e -> FrameFactory.createFrame(FrameType.VIEW_MEASUREMENTS).show());
        saveOptionsButton.addActionListener(e -> FrameFactory.createFrame(FrameType.SAVE_OPTIONS).show());
        closeButton.addActionListener(e -> close());
    }
}
