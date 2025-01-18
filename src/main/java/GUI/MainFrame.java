package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends AbstractFrame {
    private JButton addButton;
    private JButton viewButton;
    private JButton saveOptionsButton;
    private JButton closeButton;
    private JPanel contentPanel; // Panel na zmienne widoki
    private CardLayout cardLayout; // Układ zarządzający widokami

    public MainFrame() {
        super("Bieda Ciśnienie");
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        frame.setSize(700, 900); // Ustawienie początkowego rozmiaru okna
        frame.setMinimumSize(new Dimension(700, 900)); // Ustawienie minimalnego rozmiaru okna
        frame.setLayout(new BorderLayout()); // Użycie BorderLayout do rozmieszczenia komponentów

        JLabel titleLabel = new JLabel("Bieda Ciśnienie", JLabel.CENTER);
        frame.add(titleLabel, BorderLayout.NORTH); // Tytuł na górze

        // Panel z przyciskami
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel z przyciskami na dole
        addButton = new JButton("Dodaj ciśnienie");
        viewButton = new JButton("Wczytaj ciśnienie");
        saveOptionsButton = new JButton("Opcje zapisu");
        closeButton = new JButton("Zamknij");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(saveOptionsButton);
        buttonPanel.add(closeButton);

        frame.add(buttonPanel, BorderLayout.SOUTH); // Przyciski na dole okna

        // Panel na zmieniające się widoki
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        // Dodajemy poszczególne widoki do CardLayout
        contentPanel.add(new JLabel("Witaj w aplikacji Bieda Ciśnienie!"), "HOME");
        contentPanel.add(new AddMeasurementFrame().frame.getContentPane(), "ADD_MEASUREMENT");
        contentPanel.add(new ViewMeasurementsFrame().frame.getContentPane(), "VIEW_MEASUREMENTS");
        contentPanel.add(new SaveOptionsFrame().frame.getContentPane(), "SAVE_OPTIONS");

        // Dodanie contentPanel na środku
        frame.add(contentPanel, BorderLayout.CENTER);
    }

    @Override
    protected void setupListeners() {
        addButton.addActionListener(e -> cardLayout.show(contentPanel, "ADD_MEASUREMENT"));
        viewButton.addActionListener(e -> cardLayout.show(contentPanel, "VIEW_MEASUREMENTS"));
        saveOptionsButton.addActionListener(e -> cardLayout.show(contentPanel, "SAVE_OPTIONS"));
        closeButton.addActionListener(e -> close());
    }
}
