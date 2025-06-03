package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RideCreationView extends JPanel {
    private JTextField fromField;
    private JTextField toField;
    private JSpinner dateSpinner;
    private JSpinner timeSpinner;
    private JSpinner seatsSpinner;
    private JSpinner priceSpinner;
    private JTextArea descriptionArea;
    private JButton createButton;
    private JButton cancelButton;
    private JLabel errorLabel;
    private JLabel successLabel;

    public RideCreationView() {
        setBackground(new Color(248, 250, 252));
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void initializeComponents() {
        fromField = createModernTextField("Ville de départ");
        toField = createModernTextField("Ville d'arrivée");

        // Date and time spinners
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        styleSpinner(dateSpinner);

        timeSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm");
        timeSpinner.setEditor(timeEditor);
        styleSpinner(timeSpinner);

        // Number spinners
        seatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 8, 1));
        styleSpinner(seatsSpinner);

        priceSpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0, 500.0, 5.0));
        styleSpinner(priceSpinner);

        // Description area
        descriptionArea = new JTextArea(4, 30);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBackground(Color.WHITE);
        descriptionArea.setForeground(new Color(55, 65, 81));
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(12, 14, 12, 14)
        ));
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        createButton = createPrimaryButton("Publier le trajet");
        cancelButton = createSecondaryButton("Annuler");

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(239, 68, 68));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setVisible(false);

        successLabel = new JLabel();
        successLabel.setForeground(new Color(34, 197, 94));
        successLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        successLabel.setVisible(false);
    }

    private void layoutComponents() {
        // Header panel
        JPanel headerPanel = createHeaderPanel();

        // Main content panel
        JPanel mainContentPanel = createMainContentPanel();

        add(headerPanel, BorderLayout.NORTH);
        add(mainContentPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBackground(new Color(248, 250, 252));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(32, 40, 24, 40));

        JLabel titleLabel = new JLabel("Proposer un trajet");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(55, 65, 81));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Partagez votre trajet avec la communauté");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(107, 114, 128));
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        headerPanel.add(subtitleLabel);

        return headerPanel;
    }

    private JPanel createMainContentPanel() {
        JPanel mainContentPanel = new JPanel(new GridBagLayout());
        mainContentPanel.setBackground(new Color(248, 250, 252));
        mainContentPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Form panel
        JPanel formPanel = createRideFormPanel();
        mainContentPanel.add(formPanel, gbc);

        return mainContentPanel;
    }

    private JPanel createRideFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        formPanel.setMaximumSize(new Dimension(700, Integer.MAX_VALUE));

        // Journey Section
        JPanel journeySection = createSectionPanel("Itinéraire");
        addFormField(journeySection, "Lieu de départ", fromField);
        addFormField(journeySection, "Destination", toField);

        // Date and Time Section
        JPanel dateTimeSection = createSectionPanel("Date et heure");

        JPanel dateTimeRow = new JPanel();
        dateTimeRow.setLayout(new BoxLayout(dateTimeRow, BoxLayout.X_AXIS));
        dateTimeRow.setBackground(Color.WHITE);
        dateTimeRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBackground(Color.WHITE);

        JLabel dateLabel = createFieldLabel("Date de départ");
        datePanel.add(dateLabel);
        datePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        datePanel.add(dateSpinner);

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.setBackground(Color.WHITE);

        JLabel timeLabel = createFieldLabel("Heure de départ");
        timePanel.add(timeLabel);
        timePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        timePanel.add(timeSpinner);

        dateTimeRow.add(datePanel);
        dateTimeRow.add(Box.createRigidArea(new Dimension(20, 0)));
        dateTimeRow.add(timePanel);
        dateTimeRow.add(Box.createHorizontalGlue());

        dateTimeSection.add(dateTimeRow);
        dateTimeSection.add(Box.createRigidArea(new Dimension(0, 20)));

        // Details Section
        JPanel detailsSection = createSectionPanel("Détails du trajet");

        JPanel detailsRow = new JPanel();
        detailsRow.setLayout(new BoxLayout(detailsRow, BoxLayout.X_AXIS));
        detailsRow.setBackground(Color.WHITE);
        detailsRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel seatsPanel = new JPanel();
        seatsPanel.setLayout(new BoxLayout(seatsPanel, BoxLayout.Y_AXIS));
        seatsPanel.setBackground(Color.WHITE);

        JLabel seatsLabel = createFieldLabel("Places disponibles");
        seatsPanel.add(seatsLabel);
        seatsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        seatsPanel.add(seatsSpinner);

        JPanel pricePanel = new JPanel();
        pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));
        pricePanel.setBackground(Color.WHITE);

        JLabel priceLabel = createFieldLabel("Prix par place (€)");
        pricePanel.add(priceLabel);
        pricePanel.add(Box.createRigidArea(new Dimension(0, 8)));
        pricePanel.add(priceSpinner);

        detailsRow.add(seatsPanel);
        detailsRow.add(Box.createRigidArea(new Dimension(20, 0)));
        detailsRow.add(pricePanel);
        detailsRow.add(Box.createHorizontalGlue());

        detailsSection.add(detailsRow);
        detailsSection.add(Box.createRigidArea(new Dimension(0, 20)));

        // Description Section
        JPanel descriptionSection = createSectionPanel("Description (optionnel)");
        JLabel descLabel = createFieldLabel("Informations complémentaires");
        descriptionSection.add(descLabel);
        descriptionSection.add(Box.createRigidArea(new Dimension(0, 8)));

        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBorder(BorderFactory.createEmptyBorder());
        descScrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        descScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        descriptionSection.add(descScrollPane);
        descriptionSection.add(Box.createRigidArea(new Dimension(0, 20)));

        // Messages
        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setBackground(Color.WHITE);
        messagePanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        successLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        messagePanel.add(errorLabel);
        messagePanel.add(successLabel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        buttonPanel.add(createButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        buttonPanel.add(cancelButton);

        // Add all sections to form
        formPanel.add(journeySection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        formPanel.add(dateTimeSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        formPanel.add(detailsSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        formPanel.add(descriptionSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 24)));
        formPanel.add(messagePanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        formPanel.add(buttonPanel);

        return formPanel;
    }

    private JPanel createSectionPanel(String title) {
        JPanel sectionPanel = new JPanel();
        sectionPanel.setLayout(new BoxLayout(sectionPanel, BoxLayout.Y_AXIS));
        sectionPanel.setBackground(Color.WHITE);
        sectionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel sectionTitle = new JLabel(title);
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        sectionTitle.setForeground(new Color(55, 65, 81));
        sectionTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

        sectionPanel.add(sectionTitle);
        sectionPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        return sectionPanel;
    }

    private void addFormField(JPanel parent, String labelText, JComponent field) {
        JLabel label = createFieldLabel(labelText);

        parent.add(label);
        parent.add(Box.createRigidArea(new Dimension(0, 8)));
        parent.add(field);
        parent.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(55, 65, 81));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createModernTextField(String placeholder) {
        JTextField field = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty() && !hasFocus()) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(new Color(156, 163, 175));
                    g2.setFont(getFont());
                    FontMetrics fm = g2.getFontMetrics();
                    int x = getInsets().left;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
                    g2.drawString(placeholder, x, y);
                    g2.dispose();
                }
            }
        };

        field.setPreferredSize(new Dimension(300, 44));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(55, 65, 81));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));

        return field;
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setPreferredSize(new Dimension(150, 44));
        spinner.setMaximumSize(new Dimension(150, 44));
        spinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        spinner.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(2, 2, 2, 2)
        ));

        JComponent editor = spinner.getEditor();
        if (editor instanceof JSpinner.DefaultEditor) {
            JTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
            textField.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        }
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 48));
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(59, 130, 246));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 48));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(107, 114, 128));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void addEventListeners() {
        // Hover effects
        createButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                createButton.setBackground(new Color(37, 99, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                createButton.setBackground(new Color(59, 130, 246));
            }
        });

        cancelButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                cancelButton.setBackground(new Color(249, 250, 251));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                cancelButton.setBackground(Color.WHITE);
            }
        });

        // Focus effects
        addFocusEffects(fromField);
        addFocusEffects(toField);

        // Description area focus effects
        descriptionArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246), 2),
                        BorderFactory.createEmptyBorder(11, 13, 11, 13)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                descriptionArea.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                        BorderFactory.createEmptyBorder(12, 14, 12, 14)
                ));
            }
        });
    }

    private void addFocusEffects(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(59, 130, 246), 2),
                        BorderFactory.createEmptyBorder(9, 13, 9, 13)
                ));
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                        BorderFactory.createEmptyBorder(10, 14, 10, 14)
                ));
                field.repaint();
            }
        });
    }

    // Public methods for controller access
    public String getFromLocation() {
        return fromField.getText().trim();
    }

    public String getToLocation() {
        return toField.getText().trim();
    }

    public Date getDepartureDate() {
        return (Date) dateSpinner.getValue();
    }

    public Date getDepartureTime() {
        return (Date) timeSpinner.getValue();
    }

    public int getAvailableSeats() {
        return (Integer) seatsSpinner.getValue();
    }

    public double getPricePerSeat() {
        return (Double) priceSpinner.getValue();
    }

    public String getDescription() {
        return descriptionArea.getText().trim();
    }

    public JButton getCreateButton() {
        return createButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void showError(String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        successLabel.setVisible(false);
        revalidate();
        repaint();
    }

    public void showSuccess(String message) {
        successLabel.setText(message);
        successLabel.setVisible(true);
        errorLabel.setVisible(false);
        revalidate();
        repaint();
    }

    public void hideMessages() {
        errorLabel.setVisible(false);
        successLabel.setVisible(false);
        revalidate();
        repaint();
    }

    public void clearFields() {
        fromField.setText("");
        toField.setText("");
        dateSpinner.setValue(new Date());
        timeSpinner.setValue(new Date());
        seatsSpinner.setValue(1);
        priceSpinner.setValue(10.0);
        descriptionArea.setText("");
        hideMessages();
    }
}