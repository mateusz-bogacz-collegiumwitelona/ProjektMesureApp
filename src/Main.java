import GUI.MainFrame;

import javax.swing.*;


public class Main extends MainFrame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}