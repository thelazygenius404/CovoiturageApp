package view;

import javax.swing.*;
import java.awt.*;
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

    public ProfileView() {
        setBackground(ColorConstants.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        // En-tête
        JLabel header = new JLabel("Mon Profil");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(ColorConstants.PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        // Panel principal
        JPanel formPanel = new JPanel(new GridLayout(0, 2, 20, 15));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        formPanel.setBackground(Color.WHITE);

        // Champs non modifiables
        addFormField(formPanel, "Nom complet :", nameField = createStyledTextField());
        addFormField(formPanel, "Email :", emailField = createStyledTextField());
        emailField.setEditable(false);

        // Champs modifiables
        addFormField(formPanel, "Nouveau mot de passe :", passwordField = createStyledPasswordField());
        addFormField(formPanel, "Téléphone :", phoneField = createStyledTextField());

        // Section conducteur
        JPanel driverPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        driverPanel.setBackground(Color.WHITE);
        driverCheckbox = new JCheckBox();
        driverCheckbox.setEnabled(false);
        driverPanel.add(driverCheckbox);
        formPanel.add(new JLabel("Statut conducteur :"));
        formPanel.add(driverPanel);

        addFormField(formPanel, "Modèle de voiture :", carModelField = createStyledTextField());
        carModelField.setEnabled(false);

        add(formPanel, BorderLayout.CENTER);

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        saveButton = createPrimaryButton("Enregistrer les modifications");
        cancelButton = createSecondaryButton("Annuler");

        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Méthodes helper (similaires à RegistrationView)
    private void addFormField(JPanel panel, String label, JComponent field) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(jLabel);
        panel.add(field);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        return field;
    }

    // Getters
    public JButton getSaveButton() { return saveButton; }
    public JButton getCancelButton() { return cancelButton; }
    public String getName() { return nameField.getText(); }
    public String getNewPassword() { return new String(passwordField.getPassword()); }
    public String getPhone() { return phoneField.getText(); }

    // Méthode pour peupler les données
    public void setUserData(String name, String email, String phone, boolean isDriver, String carModel) {
        nameField.setText(name);
        emailField.setText(email);
        phoneField.setText(phone);
        driverCheckbox.setSelected(isDriver);
        carModelField.setText(carModel);
    }
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ColorConstants.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(12, 30, 12, 30));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Effet hover
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ColorConstants.PRIMARY_COLOR.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(ColorConstants.PRIMARY_COLOR);
            }
        });

        return button;
    }
    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(ColorConstants.PRIMARY_COLOR);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ColorConstants.PRIMARY_COLOR, 1),
                BorderFactory.createEmptyBorder(12, 30, 12, 30)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Effet hover inversé
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(ColorConstants.PRIMARY_COLOR);
                button.setForeground(Color.WHITE);
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
                button.setForeground(ColorConstants.PRIMARY_COLOR);
            }
        });

        return button;
    }
}
