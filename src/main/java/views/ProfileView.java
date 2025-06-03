package views;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProfileView extends JPanel {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JTextField phoneField;
    private JCheckBox driverCheckbox;
    private JTextField carModelField;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel errorLabel;
    private JLabel successLabel;

    public ProfileView() {
        setBackground(new Color(248, 250, 252));
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void initializeComponents() {
        nameField = createModernTextField("Nom complet");
        emailField = createModernTextField("Adresse email");
        emailField.setEditable(false); // Email is not editable
        passwordField = createModernPasswordField("Nouveau mot de passe (optionnel)");
        phoneField = createModernTextField("Numéro de téléphone");
        carModelField = createModernTextField("Modèle de voiture");

        driverCheckbox = new JCheckBox("Statut conducteur");
        driverCheckbox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        driverCheckbox.setForeground(new Color(55, 65, 81));
        driverCheckbox.setBackground(Color.WHITE);
        driverCheckbox.setFocusPainted(false);
        driverCheckbox.setEnabled(false); // Driver status can't be changed in profile

        saveButton = createPrimaryButton("Enregistrer les modifications");
        cancelButton = createSecondaryButton("Annuler");

        errorLabel = new JLabel();
        errorLabel.setForeground(new Color(239, 68, 68));
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setVisible(false);

        successLabel = new JLabel();
        successLabel.setForeground(new Color(34, 197, 94));
        successLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        successLabel.setVisible(false);

        updateCarModelFieldStyle();
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

        JLabel titleLabel = new JLabel("Mon Profil");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(55, 65, 81));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Gérez vos informations personnelles");
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

        // Profile form panel
        JPanel formPanel = createProfileFormPanel();
        mainContentPanel.add(formPanel, gbc);

        return mainContentPanel;
    }

    private JPanel createProfileFormPanel() {
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(40, 40, 40, 40)
        ));
        formPanel.setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        // Personal Information Section
        JPanel personalSection = createSectionPanel("Informations personnelles");
        addFormField(personalSection, "Nom complet", nameField);
        addFormField(personalSection, "Adresse email", emailField);
        addFormField(personalSection, "Téléphone", phoneField);
        addFormField(personalSection, "Nouveau mot de passe", passwordField);

        // Driver Information Section
        JPanel driverSection = createSectionPanel("Informations conducteur");

        JPanel driverStatusPanel = new JPanel();
        driverStatusPanel.setLayout(new BoxLayout(driverStatusPanel, BoxLayout.Y_AXIS));
        driverStatusPanel.setBackground(Color.WHITE);
        driverStatusPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel statusLabel = createFieldLabel("Statut");
        driverStatusPanel.add(statusLabel);
        driverStatusPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        JPanel checkboxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        checkboxPanel.setBackground(Color.WHITE);
        checkboxPanel.add(driverCheckbox);
        driverStatusPanel.add(checkboxPanel);

        driverSection.add(driverStatusPanel);
        driverSection.add(Box.createRigidArea(new Dimension(0, 16)));

        addFormField(driverSection, "Modèle de voiture", carModelField);

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

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        buttonPanel.add(cancelButton);

        // Add all sections to form
        formPanel.add(personalSection);
        formPanel.add(Box.createRigidArea(new Dimension(0, 32)));
        formPanel.add(driverSection);
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
                if (getText().isEmpty() && !hasFocus() && isEnabled()) {
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
        field.setPreferredSize(new Dimension(480, 44));
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
        } else {
            carModelField.setBackground(new Color(249, 250, 251));
            carModelField.setForeground(new Color(156, 163, 175));
        }
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 44));
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
        button.setPreferredSize(new Dimension(120, 44));
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
        saveButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                saveButton.setBackground(new Color(37, 99, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                saveButton.setBackground(new Color(59, 130, 246));
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
        addFocusEffects(passwordField);
        addFocusEffects(phoneField);
        addFocusEffects(carModelField);

        // Email field has special styling since it's disabled
        emailField.setBackground(new Color(249, 250, 251));
        emailField.setForeground(new Color(107, 114, 128));
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
                }
                field.repaint();
            }
        });
    }

    // Public methods for controller access
    public String getName() {
        return nameField.getText().trim();
    }

    public String getNewPassword() {
        return new String(passwordField.getPassword());
    }

    public String getPhone() {
        return phoneField.getText().trim();
    }

    public String getCarModel() {
        return carModelField.getText().trim();
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setUserData(String name, String email, String phone, boolean isDriver, String carModel) {
        nameField.setText(name);
        emailField.setText(email);
        phoneField.setText(phone);
        driverCheckbox.setSelected(isDriver);

        if (isDriver) {
            carModelField.setText(carModel);
            carModelField.setEnabled(true);
        } else {
            carModelField.setText("");
            carModelField.setEnabled(false);
        }

        updateCarModelFieldStyle();
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
}