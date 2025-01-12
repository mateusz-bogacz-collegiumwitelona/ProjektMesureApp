package GUI;


import javax.swing.*;
import java.awt.*;

public abstract class AbstractFrame {
    protected final JFrame frame;

    protected AbstractFrame(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
    }

    protected abstract void initComponents();
    protected abstract void setupListeners();

    protected void showError(String title, String message) {
        JOptionPane.showMessageDialog(frame, message, title, JOptionPane.ERROR_MESSAGE);
    }

    protected void showSuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Sukces", JOptionPane.INFORMATION_MESSAGE);
    }

    protected void addComponent(JComponent component, GridBagConstraints gbc,
                                int gridx, int gridy, int gridwidth) {
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        frame.add(component, gbc);
    }

    public void show() {
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void close() {
        frame.dispose();
    }
}