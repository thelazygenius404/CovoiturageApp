package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashboardView extends JFrame {
    private JTable ridesTable;
    private JButton searchButton;
    private JButton createRideButton;
    private JButton viewBookingsButton;
    private JButton profileButton;
    private JButton logoutButton;

    public DashboardView() {
        setTitle("Tableau de bord CarPool");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Conteneur principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Barre latérale
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(ColorConstants.PRIMARY_COLOR);
        sidebar.setPreferredSize(new Dimension(250, getHeight()));

        // Éléments de la barre latérale
        JLabel logo = new JLabel("CarPool");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        logo.setForeground(Color.WHITE);
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 40, 20));

        String[] menuItems = {"Rechercher trajets", "Créer trajet", "Mes réservations", "Profil"};
        for (String item : menuItems) {
            JButton btn = new JButton(item);
            styleSidebarButton(btn);
            sidebar.add(btn);
            sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        sidebar.add(Box.createVerticalGlue());
        JButton logoutBtn = new JButton("Déconnexion");
        styleSidebarButton(logoutBtn);
        sidebar.add(logoutBtn);

        // Contenu principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // Tableau des trajets
        String[] columns = {"Date", "Départ", "Destination", "Places", "Prix"};
        Object[][] data = {}; // Données réelles à ajouter
        ridesTable = new JTable(data, columns);
        ridesTable.setFillsViewportHeight(true);
        ridesTable.setRowHeight(35);
        ridesTable.setShowGrid(false);
        ridesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(ridesTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Ajout des composants
        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);
    }

    private void styleSidebarButton(JButton btn) {
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setForeground(Color.WHITE);
        btn.setBackground(ColorConstants.PRIMARY_COLOR);
        btn.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setContentAreaFilled(false);
        btn.setOpaque(true);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(ColorConstants.PRIMARY_COLOR.darker());
            }
            public void mouseExited(MouseEvent e) {
                btn.setBackground(ColorConstants.PRIMARY_COLOR);
            }
        });
    }

    public JButton getCreateRideButton() {
        return createRideButton;
    }

    public JButton getViewBookingsButton() {
        return viewBookingsButton;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JButton getProfileButton() {
        return profileButton;
    }
}