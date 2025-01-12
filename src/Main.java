import GUI.FrameFactory;
import GUI.FrameType;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            try {
                FrameFactory.createFrame(FrameType.MAIN).show();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Wystąpił błąd podczas uruchamiania aplikacji: " + e.getMessage(),
                        "Błąd",
                        JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                System.exit(1);
            }
        });
    }
}