package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomepageView extends JPanel {
    private JButton loginButton;
    private JButton registerButton;
    private JButton exploreButton;

    public HomepageView() {
        setBackground(new Color(248, 250, 252));
        setLayout(new BorderLayout());

        initializeComponents();
        layoutComponents();
        addEventListeners();
    }

    private void initializeComponents() {
        loginButton = createPrimaryButton("Se connecter");
        registerButton = createSecondaryButton("Créer un compte");
        exploreButton = createOutlineButton("Explorer les trajets");
    }

    private void layoutComponents() {
        // Main scroll pane to handle overflow
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel mainContent = createMainContent();
        scrollPane.setViewportView(mainContent);

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createMainContent() {
        JPanel mainContent = new JPanel();
        mainContent.setLayout(new BoxLayout(mainContent, BoxLayout.Y_AXIS));
        mainContent.setBackground(new Color(248, 250, 252));

        // Hero Section
        JPanel heroSection = createHeroSection();

        // Features Section
        JPanel featuresSection = createFeaturesSection();

        // Statistics Section
        JPanel statsSection = createStatsSection();

        // Call to Action Section
        JPanel ctaSection = createCallToActionSection();

        // Footer
        JPanel footerSection = createFooterSection();

        mainContent.add(heroSection);
        mainContent.add(featuresSection);
        mainContent.add(statsSection);
        mainContent.add(ctaSection);
        mainContent.add(footerSection);

        return mainContent;
    }

    private JPanel createHeroSection() {
        JPanel heroSection = new JPanel();
        heroSection.setLayout(new BoxLayout(heroSection, BoxLayout.Y_AXIS));
        heroSection.setBackground(Color.WHITE);
        heroSection.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));
        heroSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Header with navigation
        JPanel headerPanel = createHeaderPanel();

        // Hero content
        JPanel heroContent = new JPanel();
        heroContent.setLayout(new BoxLayout(heroContent, BoxLayout.Y_AXIS));
        heroContent.setBackground(Color.WHITE);
        heroContent.setAlignmentX(Component.CENTER_ALIGNMENT);
        heroContent.setBorder(BorderFactory.createEmptyBorder(60, 0, 40, 0));

        JLabel mainTitle = new JLabel("Voyagez ensemble,");
        mainTitle.setFont(new Font("Segoe UI", Font.BOLD, 48));
        mainTitle.setForeground(new Color(55, 65, 81));
        mainTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subTitle = new JLabel("économisez plus");
        subTitle.setFont(new Font("Segoe UI", Font.BOLD, 48));
        subTitle.setForeground(new Color(59, 130, 246));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel description = new JLabel("<html><div style='text-align: center; width: 500px;'>" +
                "Découvrez CarPool, la plateforme qui connecte conducteurs et passagers " +
                "pour des trajets économiques et écologiques. Partagez vos frais, " +
                "réduisez votre empreinte carbone et rencontrez de nouvelles personnes." +
                "</div></html>");
        description.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        description.setForeground(new Color(107, 114, 128));
        description.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Action buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(32, 0, 0, 0));

        buttonPanel.add(registerButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(16, 0)));
        buttonPanel.add(exploreButton);

        heroContent.add(mainTitle);
        heroContent.add(Box.createRigidArea(new Dimension(0, 8)));
        heroContent.add(subTitle);
        heroContent.add(Box.createRigidArea(new Dimension(0, 24)));
        heroContent.add(description);
        heroContent.add(buttonPanel);

        heroSection.add(headerPanel);
        heroSection.add(heroContent);

        return heroSection;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Logo
        JLabel logo = new JLabel("CarPool");
        logo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logo.setForeground(new Color(59, 130, 246));

        // Navigation
        JPanel navPanel = new JPanel();
        navPanel.setLayout(new BoxLayout(navPanel, BoxLayout.X_AXIS));
        navPanel.setBackground(Color.WHITE);

        navPanel.add(loginButton);

        headerPanel.add(logo, BorderLayout.WEST);
        headerPanel.add(navPanel, BorderLayout.EAST);

        return headerPanel;
    }

    private JPanel createFeaturesSection() {
        JPanel featuresSection = new JPanel();
        featuresSection.setLayout(new BoxLayout(featuresSection, BoxLayout.Y_AXIS));
        featuresSection.setBackground(new Color(249, 250, 251));
        featuresSection.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        // Section title
        JLabel sectionTitle = new JLabel("Pourquoi choisir CarPool ?");
        sectionTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        sectionTitle.setForeground(new Color(55, 65, 81));
        sectionTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sectionSubtitle = new JLabel("Des fonctionnalités pensées pour simplifier vos trajets");
        sectionSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        sectionSubtitle.setForeground(new Color(107, 114, 128));
        sectionSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Features grid
        JPanel featuresGrid = new JPanel(new GridLayout(2, 2, 40, 40));
        featuresGrid.setBackground(new Color(249, 250, 251));
        featuresGrid.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));

        // Feature cards
        featuresGrid.add(createFeatureCard("💰", "Économique",
                "Partagez les frais de trajet et réduisez vos dépenses de transport jusqu'à 70%"));
        featuresGrid.add(createFeatureCard("🌱", "Écologique",
                "Réduisez votre empreinte carbone en partageant votre véhicule avec d'autres voyageurs"));
        featuresGrid.add(createFeatureCard("🛡️", "Sécurisé",
                "Profils vérifiés, système d'évaluation et paiement sécurisé pour voyager en toute confiance"));
        featuresGrid.add(createFeatureCard("📱", "Simple",
                "Interface intuitive pour réserver ou proposer un trajet en quelques clics"));

        featuresSection.add(sectionTitle);
        featuresSection.add(Box.createRigidArea(new Dimension(0, 16)));
        featuresSection.add(sectionSubtitle);
        featuresSection.add(featuresGrid);

        return featuresSection;
    }

    private JPanel createFeatureCard(String icon, String title, String description) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(229, 231, 235), 1),
                BorderFactory.createEmptyBorder(32, 24, 32, 24)
        ));

        JLabel iconLabel = new JLabel(icon);
        iconLabel.setFont(new Font("Segoe UI", Font.PLAIN, 48));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(55, 65, 81));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel("<html><div style='text-align: center; width: 200px;'>" +
                description + "</div></html>");
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(new Color(107, 114, 128));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 16)));
        card.add(titleLabel);
        card.add(Box.createRigidArea(new Dimension(0, 12)));
        card.add(descLabel);

        return card;
    }

    private JPanel createStatsSection() {
        JPanel statsSection = new JPanel();
        statsSection.setLayout(new BoxLayout(statsSection, BoxLayout.Y_AXIS));
        statsSection.setBackground(new Color(59, 130, 246));
        statsSection.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        JLabel statsTitle = new JLabel("CarPool en chiffres");
        statsTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        statsTitle.setForeground(Color.WHITE);
        statsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Stats grid
        JPanel statsGrid = new JPanel(new GridLayout(1, 3, 60, 0));
        statsGrid.setBackground(new Color(59, 130, 246));
        statsGrid.setBorder(BorderFactory.createEmptyBorder(50, 0, 0, 0));

        statsGrid.add(createStatCard("10,000+", "Utilisateurs actifs"));
        statsGrid.add(createStatCard("50,000+", "Trajets partagés"));
        statsGrid.add(createStatCard("2M+", "Kilomètres économisés"));

        statsSection.add(statsTitle);
        statsSection.add(statsGrid);

        return statsSection;
    }

    private JPanel createStatCard(String number, String label) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(59, 130, 246));

        JLabel numberLabel = new JLabel(number);
        numberLabel.setFont(new Font("Segoe UI", Font.BOLD, 48));
        numberLabel.setForeground(Color.WHITE);
        numberLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel textLabel = new JLabel(label);
        textLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        textLabel.setForeground(new Color(219, 234, 254));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(numberLabel);
        card.add(Box.createRigidArea(new Dimension(0, 8)));
        card.add(textLabel);

        return card;
    }

    private JPanel createCallToActionSection() {
        JPanel ctaSection = new JPanel();
        ctaSection.setLayout(new BoxLayout(ctaSection, BoxLayout.Y_AXIS));
        ctaSection.setBackground(Color.WHITE);
        ctaSection.setBorder(BorderFactory.createEmptyBorder(80, 60, 80, 60));

        JLabel ctaTitle = new JLabel("Prêt à commencer votre voyage ?");
        ctaTitle.setFont(new Font("Segoe UI", Font.BOLD, 36));
        ctaTitle.setForeground(new Color(55, 65, 81));
        ctaTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel ctaSubtitle = new JLabel("Rejoignez des milliers d'utilisateurs qui font confiance à CarPool");
        ctaSubtitle.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ctaSubtitle.setForeground(new Color(107, 114, 128));
        ctaSubtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton ctaButton = createPrimaryButton("Commencer maintenant");
        ctaButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        ctaButton.setPreferredSize(new Dimension(220, 56));
        ctaButton.setFont(new Font("Segoe UI", Font.BOLD, 16));

        ctaSection.add(ctaTitle);
        ctaSection.add(Box.createRigidArea(new Dimension(0, 16)));
        ctaSection.add(ctaSubtitle);
        ctaSection.add(Box.createRigidArea(new Dimension(0, 32)));
        ctaSection.add(ctaButton);

        return ctaSection;
    }

    private JPanel createFooterSection() {
        JPanel footerSection = new JPanel();
        footerSection.setLayout(new BoxLayout(footerSection, BoxLayout.Y_AXIS));
        footerSection.setBackground(new Color(55, 65, 81));
        footerSection.setBorder(BorderFactory.createEmptyBorder(60, 60, 40, 60));

        JPanel footerContent = new JPanel(new BorderLayout());
        footerContent.setBackground(new Color(55, 65, 81));

        // Footer logo and description
        JPanel logoSection = new JPanel();
        logoSection.setLayout(new BoxLayout(logoSection, BoxLayout.Y_AXIS));
        logoSection.setBackground(new Color(55, 65, 81));

        JLabel footerLogo = new JLabel("CarPool");
        footerLogo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        footerLogo.setForeground(Color.WHITE);

        JLabel footerDesc = new JLabel("La plateforme de covoiturage nouvelle génération");
        footerDesc.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        footerDesc.setForeground(new Color(156, 163, 175));

        logoSection.add(footerLogo);
        logoSection.add(Box.createRigidArea(new Dimension(0, 8)));
        logoSection.add(footerDesc);

        // Copyright
        JLabel copyright = new JLabel("© 2025 CarPool. Tous droits réservés.");
        copyright.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        copyright.setForeground(new Color(156, 163, 175));
        copyright.setHorizontalAlignment(SwingConstants.CENTER);

        footerContent.add(logoSection, BorderLayout.WEST);

        footerSection.add(footerContent);
        footerSection.add(Box.createRigidArea(new Dimension(0, 40)));
        footerSection.add(copyright);

        return footerSection;
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(59, 130, 246));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 44));

        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(new Color(34, 197, 94));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(160, 44));

        return button;
    }

    private JButton createOutlineButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(59, 130, 246));
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(new Color(59, 130, 246), 1));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(180, 44));

        return button;
    }

    private void addEventListeners() {
        // Login button hover effects
        loginButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                loginButton.setBackground(new Color(37, 99, 235));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                loginButton.setBackground(new Color(59, 130, 246));
            }
        });

        // Register button hover effects
        registerButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                registerButton.setBackground(new Color(22, 163, 74));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                registerButton.setBackground(new Color(34, 197, 94));
            }
        });

        // Explore button hover effects
        exploreButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                exploreButton.setBackground(new Color(239, 246, 255));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                exploreButton.setBackground(Color.WHITE);
            }
        });
    }

    // Public methods for controller access
    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getExploreButton() {
        return exploreButton;
    }
}
