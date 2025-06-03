package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RegistrationView extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JCheckBox driverCheckbox;
    private JTextField carModelField;
    private JButton registerButton;
    private JButton cancelButton;
    private JLabel errorLabel;
    private JLabel successLabel;

    public RegistrationView() {
        setBackground(new Color(248, 250, 252));
        setLayout(new GridBagLayout());

        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void initializeComponents() {
        nameField = createModernTextField("Nom complet");
        emailField = createModernTextField("Adresse email");
        passwordField = createModernPasswordField("Mot de passe (8+ caractères)");
        phoneField = createModernTextField("Numéro de téléphone");
        carModelField = createModernTextField("Modèle de voiture");

        driverCheckbox = new JCheckBox("Je souhaite être conducteur");
        driverCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        driverCheckbox.setForeground(new Color(55, 65, 81));
        driverCheckbox.setBackground(Color.WHITE);
        driverCheckbox.setFocusPainted(false);

        registerButton = createPrimaryButton("Créer mon compte");
        cancelButton = createSecondaryButton("Retour à la connexion");

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(239, 68, 68));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setVisible(false);

        successLabel = new JLabel();
        successLabel.setForeground(new Color(34, 197, 94));
        successLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        successLabel.setVisible(false);

        // Initially disable car model field
        carModelField.setEnabled(false);
        updateCarModelFieldStyle();
    }

    private void layoutComponents() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Main container panel
        JPanel mainPanel = createMainPanel();

        // Title
        JLabel titleLabel = new JLabel("Créer un compte");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(55, 65, 81));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subtitleLabel = new JLabel("Rejoignez la communauté CarPool");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(107, 114, 128));
        subtitleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        // Add form fields
        addFormField(formPanel, "Nom complet", nameField);
        addFormField(formPanel, "Adresse email", emailField);
        addFormField(formPanel, "Mot de passe", passwordField);
        addFormField(formPanel, "Téléphone", phoneField);

        // Driver section
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel driverPanel = new JPanel();
        driverPanel.setLayout(new BoxLayout(driverPanel, BoxLayout.Y_AXIS));
        driverPanel.setBackground(Color.WHITE);
        driverPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)
        ));
        driverPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        driverPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel driverSectionLabel = new JLabel("Options conducteur");
        driverSectionLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        driverSectionLabel.setForeground(new Color(55, 65, 81));
        driverSectionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        driverCheckbox.setAlignmentX(Component.LEFT_ALIGNMENT);

        driverPanel.add(driverSectionLabel);
        driverPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        driverPanel.add(driverCheckbox);

        formPanel.add(driverPanel);
        addFormField(formPanel, "Modèle de voiture", carModelField);

        // Error label
        formPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        successLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.add(errorLabel);
        formPanel.add(successLabel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 24)));

        // Buttons
        formPanel.add(registerButton);
        formPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        formPanel.add(cancelButton);

        // Add to main panel
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        mainPanel.add(formPanel);

        // Center the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        add(mainPanel, gbc);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(8, 8, 8, 8),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                        BorderFactory.createEmptyBorder(40, 40, 40, 40)
                )
        ));
        panel.setPreferredSize(new Dimension(480, 720));

        return panel;
    }

    private void addFormField(JPanel parent, String labelText, JComponent field) {
        parent.add(Box.createRigidArea(new Dimension(0, 16)));

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(55, 65, 81));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        parent.add(label);
        parent.add(Box.createRigidArea(new Dimension(0, 8)));
        parent.add(field);
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

        styleTextField(field);
        return field;
    }

    private JPasswordField createModernPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getPassword().length == 0 && !hasFocus()) {
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

        styleTextField(field);
        return field;
    }

    private void styleTextField(JTextField field) {
        field.setPreferredSize(new Dimension(400, 44));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(new Color(55, 65, 81));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                BorderFactory.createEmptyBorder(10, 14, 10, 14)
        ));
    }

    private void updateCarModelFieldStyle() {
        if (carModelField.isEnabled()) {
            carModelField.setBackground(Color.WHITE);
            carModelField.setForeground(new Color(55, 65, 81));
            carModelField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));
        } else {
            carModelField.setBackground(new Color(249, 250, 251));
            carModelField.setForeground(new Color(156, 163, 175));
            carModelField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                    BorderFactory.createEmptyBorder(10, 14, 10, 14)
            ));
        }
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(400, 48));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
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
        button.setPreferredSize(new Dimension(400, 48));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(107, 114, 128));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(209, 213, 219), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void addEventListeners() {
        // Driver checkbox logic
        driverCheckbox.addItemListener(e -> {
            boolean isSelected = driverCheckbox.isSelected();
            carModelField.setEnabled(isSelected);
            updateCarModelFieldStyle();
            if (!isSelected) {
                carModelField.setText("");
            }
        });

        // Hover effects
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(37, 99, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(new Color(59, 130, 246));
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
        addFocusEffects(nameField);
        addFocusEffects(emailField);
        addFocusEffects(passwordField);
        addFocusEffects(phoneField);
        addFocusEffects(carModelField);
    }

    private void addFocusEffects(JTextField field) {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.isEnabled()) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(59, 130, 246), 2),
                            BorderFactory.createEmptyBorder(9, 13, 9, 13)
                    ));
                }
                field.repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.isEnabled()) {
                    field.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(209, 213, 219), 1),
                            BorderFactory.createEmptyBorder(10, 14, 10, 14)
                    ));
                } else {
                    updateCarModelFieldStyle();
                }
                field.repaint();
            }
        });
    }

    // Public methods for controller access
    public String getName() {
        return nameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public boolean isDriver() {
        return driverCheckbox.isSelected();
    }

    public String getCarModel() {
        return carModelField.getText().trim();
    }

    public JButton getRegisterButton() {
        return registerButton;
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

    public void hideError() {
        errorLabel.setVisible(false);
        successLabel.setVisible(false);
        revalidate();
        repaint();
    }

    public void clearFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
        phoneField.setText("");
        driverCheckbox.setSelected(false);
        carModelField.setText("");
        carModelField.setEnabled(false);
        updateCarModelFieldStyle();
        hideError();
    }
}