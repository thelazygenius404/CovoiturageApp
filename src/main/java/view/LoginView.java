package view;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton registerButton;

    public LoginView() {
        setBackground(ColorConstants.BACKGROUND_COLOR);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // En-tête
        JLabel header = new JLabel("Bienvenue sur CarPool");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(ColorConstants.PRIMARY_COLOR);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(header, gbc);

        // Panel du formulaire
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        formPanel.setBackground(Color.WHITE);
        formPanel.setPreferredSize(new Dimension(400, 200));

        // Champ email
        JLabel emailLabel = createFormLabel("Email :");
        formPanel.add(emailLabel);
        emailField = createStyledTextField();
        formPanel.add(emailField);

        // Champ mot de passe
        JLabel passwordLabel = createFormLabel("Mot de passe :");
        formPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        formPanel.add(passwordField);

        gbc.gridy = 1;
        add(formPanel, gbc);

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        loginButton = createPrimaryButton("Se connecter");
        registerButton = createSecondaryButton("Créer un compte");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 2;
        add(buttonPanel, gbc);
    }
    private JLabel createFormLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(ColorConstants.TEXT_COLOR);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        return field;
    }
    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(ColorConstants.PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ColorConstants.PRIMARY_COLOR.darker());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
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
                BorderFactory.createLineBorder(ColorConstants.PRIMARY_COLOR),
                BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return button;
    }
    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }
}
