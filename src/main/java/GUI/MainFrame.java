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
    private ViewMeasurementsFrame viewMeasurementsFrame;

    public MainFrame() {
        super("Bieda Ciśnienie");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        buttonPanel.setBackground(new Color(30, 30, 30));

        addButton = createButton("Dodaj ciśnienie");
        viewButton = createButton("Wczytaj ciśnienie");
        saveOptionsButton = createButton("Opcje zapisu");
        closeButton = createButton("Zamknij");

        buttonPanel.add(addButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(viewButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(saveOptionsButton);
        buttonPanel.add(Box.createVerticalStrut(30));
        buttonPanel.add(closeButton);

        // Panel na przyciski wyśrodkowany po lewej stronie
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new GridBagLayout());
        leftPanel.setBackground(new Color(30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        leftPanel.add(buttonPanel, gbc);

        // Panel na dynamiczne widoki
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);

        contentPanel.add(new JLabel("Witaj w aplikacji Bieda Ciśnienie!"), "HOME");
        viewMeasurementsFrame = new ViewMeasurementsFrame();
        contentPanel.add(new AddMeasurementFrame(viewMeasurementsFrame).frame.getContentPane(), "ADD_MEASUREMENT");
        contentPanel.add(viewMeasurementsFrame.frame.getContentPane(), "VIEW_MEASUREMENTS");
        contentPanel.add(new SaveOptionsFrame().frame.getContentPane(), "SAVE_OPTIONS");

        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(contentPanel, BorderLayout.CENTER);
    }

    protected JButton createButton(String text) {
        JButton button = new JButton(text);

        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorderPainted(false);

        int buttonWidth = 200;
        int buttonHeight = 50;
        button.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
        button.setMaximumSize(new Dimension(buttonWidth, buttonHeight));
        button.setMinimumSize(new Dimension(buttonWidth, buttonHeight));

        button.setBackground(new Color(30, 144, 255));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(65, 105, 225));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 144, 255));
            }
        });

        return button;
    }

    public static JComboBox<String> createBJComboBox(String text) {
        JComboBox<String> comboBox = new JComboBox<>(new String[]{text, "Opcja 1", "Opcja 2", "Opcja 3"});

        comboBox.setPreferredSize(new Dimension(200, 40));
        comboBox.setBackground(new Color(30, 144, 255));
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(new Font("SansSerif", Font.BOLD, 16));
        comboBox.setOpaque(true);

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (isSelected) {
                    label.setBackground(new Color(65, 105, 225));
                    label.setForeground(Color.WHITE);
                } else {
                    label.setBackground(new Color(30, 144, 255));
                    label.setForeground(Color.WHITE);
                }
                label.setOpaque(true);
                return label;
            }
        });

        comboBox.setUI(new javax.swing.plaf.basic.BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = super.createArrowButton();
                arrowButton.setBackground(new Color(30, 144, 255));
                arrowButton.setBorder(BorderFactory.createEmptyBorder());
                return arrowButton;
            }

            @Override
            protected void installDefaults() {
                super.installDefaults();
                comboBox.setBackground(new Color(30, 144, 255));
                comboBox.setForeground(Color.WHITE);
            }
        });

        comboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(new Color(65, 105, 225));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                comboBox.setBackground(new Color(30, 144, 255));
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