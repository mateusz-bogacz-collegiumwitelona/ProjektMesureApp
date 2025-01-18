package GUI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends AbstractFrame {
    private JButton addButton;
    private JButton viewButton;
    private JButton saveOptionsButton;
    private JButton closeButton;
    private JPanel contentPanel;
    private CardLayout cardLayout;

    public MainFrame() {
        super("Bieda Ciśnienie");
        initComponents();
        setupListeners();
    }

    @Override
    protected void initComponents() {
        // Ustawienia globalne dla aplikacji (ciemnoszare tło, jasny tekst)
        UIManager.put("Button.background", new Color(30, 144, 255));
        UIManager.put("Button.foreground", Color.WHITE);
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 16));
        UIManager.put("Panel.background", new Color(45, 45, 45));
        UIManager.put("Label.foreground", Color.WHITE);
        UIManager.put("Label.font", new Font("SansSerif", Font.BOLD, 20));

        frame.setSize(800, 600);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setLayout(new BorderLayout());

        // Tytuł
        JLabel titleLabel = new JLabel("Bieda Ciśnienie", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Panel na przyciski
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(30, 30, 30)); // Tło

        addButton = createButton("Dodaj ciśnienie");
        viewButton = createButton("Wczytaj ciśnienie");
        saveOptionsButton = createButton("Opcje zapisu");
        closeButton = createButton("Zamknij");

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(30)); // Większy odstęp
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createVerticalStrut(30)); // Większy odstęp
        buttonPanel.add(saveOptionsButton);
        buttonPanel.add(Box.createVerticalStrut(30)); // Większy odstęp
        buttonPanel.add(closeButton);

        // Panel na przyciski wyśrodkowany po lewej stronie
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout()); // Wyśrodkowanie
        leftPanel.setBackground(new Color(30, 30, 30)); // Ciemniejsze tło za przyciskami

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Dodanie odstępów między przyciskami

        leftPanel.add(buttonPanel, gbc);

        // Panel na dynamiczne widoki
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new JLabel("Witaj w aplikacji Bieda Ciśnienie!"), "HOME");
        contentPanel.add(new AddMeasurementFrame().frame.getContentPane(), "ADD_MEASUREMENT");
        contentPanel.add(new ViewMeasurementsFrame().frame.getContentPane(), "VIEW_MEASUREMENTS");
        contentPanel.add(new SaveOptionsFrame().frame.getContentPane(), "SAVE_OPTIONS");

        frame.add(leftPanel, BorderLayout.WEST); // Panel po lewej stronie
        frame.add(contentPanel, BorderLayout.CENTER); // Panel z dynamicznymi widokami
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);

        // Usunięcie domyślnych stylów, które mogą powodować białe tło
        button.setContentAreaFilled(false);
        button.setOpaque(true); // Ustawia kolor tła przycisku
        button.setBorderPainted(false); // Usunięcie domyślnej obwódki

        button.setPreferredSize(new Dimension(200, 40));
        button.setBackground(new Color(30, 144, 255)); // Niebieskie tło
        button.setForeground(Color.WHITE); // Biały tekst
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Dodatkowy margines wewnętrzny

        // Dodanie prostego efektu hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225)); // Ciemniejszy niebieski
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255)); // Powrót do jasnoniebieskiego
            }
        });

        return button;
    }

    // W klasie MainFrame:
    public static JComboBox<String> createBJComboBox(String text) {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{text, "Opcja 1", "Opcja 2", "Opcja 3"});

        comboBox.setPreferredSize(new Dimension(200, 40));
        comboBox.setBackground(new Color(30, 144, 255)); // Niebieskie tło
        comboBox.setForeground(Color.WHITE); // Biały tekst
        comboBox.setFont(new Font("SansSerif", Font.BOLD, 16));
        comboBox.setOpaque(true); // Wymuszenie malowania tła

        // Ustawienie renderera dla wszystkich elementów (łącznie z wyświetlanym na przycisku)
        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Kolory dla elementów listy
                if (isSelected) {
                    label.setBackground(new Color(65, 105, 225)); // Ciemniejszy niebieski
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(30, 144, 255)); // Jasnoniebieski
                    label.setForeground(Color.WHITE);
                }
                label.setOpaque(true);
                return label;
            }
        });

        // Ustawienie wyglądu strzałki i głównego przycisku
        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = super.createArrowButton();
                arrowButton.setBackground(new Color(30, 144, 255)); // Tło strzałki
                arrowButton.setBorder(BorderFactory.createEmptyBorder()); // Usuń obramowanie
                return arrowButton;
            }

            @Override
            protected void installDefaults() {
                super.installDefaults();
                comboBox.setBackground(new Color(30, 144, 255)); // Niebieskie tło głównego przycisku
                comboBox.setForeground(Color.WHITE); // Biały tekst
            }
        });

        // Efekt hover dla głównego przycisku
        comboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(new Color(65, 105, 225)); // Ciemniejszy niebieski
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(new Color(30, 144, 255)); // Jasnoniebieski
            }
        });

        return comboBox;
    }







    @Override
    protected void setupListeners() {
        addButton.addActionListener(e -> cardLayout.show(contentPanel, "ADD_MEASUREMENT"));
        viewButton.addActionListener(e -> cardLayout.show(contentPanel, "VIEW_MEASUREMENTS"));
        saveOptionsButton.addActionListener(e -> cardLayout.show(contentPanel, "SAVE_OPTIONS"));
        closeButton.addActionListener(e -> close());
    }
}
