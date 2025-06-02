package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RideCreationView extends JPanel {
    private JTextField fromField;
    private JTextField toField;
    private JSpinner seatsSpinner;
    private JSpinner priceSpinner;
    private JButton createButton;
    private JButton cancelButton;

    public RideCreationView() {
        setBackground(ColorConstants.BACKGROUND_COLOR);
        setLayout(new BorderLayout());

        JLabel header = new JLabel("Proposer un trajet");
        header.setFont(new Font("Segoe UI", Font.BOLD, 24));
        header.setForeground(ColorConstants.PRIMARY_COLOR);
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        header.setHorizontalAlignment(SwingConstants.CENTER);
        add(header, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridLayout(5, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(30, 50, 30, 50)
        ));
        formPanel.setBackground(Color.WHITE);

        addFormField(formPanel, "Lieu de départ :", fromField = createStyledTextField());
        addFormField(formPanel, "Destination :", toField = createStyledTextField());

        seatsSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));
        seatsSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "Places disponibles :", seatsSpinner);

        priceSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 1000.0, 0.5));
        priceSpinner.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        addFormField(formPanel, "Prix par place (€) :", priceSpinner);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        createButton = createPrimaryButton("Publier le trajet");
        cancelButton = createSecondaryButton("Annuler");

        buttonPanel.add(createButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(cancelButton);

        add(buttonPanel, BorderLayout.SOUTH);
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
}
