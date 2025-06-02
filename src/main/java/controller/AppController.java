package controller;
import view.*;
import javax.swing.*;

public class AppController {
    private JFrame mainFrame;

    public AppController(JFrame frame) {
        this.mainFrame = frame;
        showLoginView();
    }

    private void showLoginView() {
        LoginView loginView = new LoginView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(loginView);

        loginView.getLoginButton().addActionListener(e -> showDashboard());
        loginView.getRegisterButton().addActionListener(e -> showRegistrationView());

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showRegistrationView() {
        RegistrationView registrationView = new RegistrationView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(registrationView);

        // Récupération des boutons
        JButton registerBtn = registrationView.getRegisterButton();
        JButton cancelBtn = registrationView.getCancelButton();

        // Gestion des clics
        registerBtn.addActionListener(e -> {
            if(validateRegistration(registrationView)) {
                showDashboard();
            }
        });

        cancelBtn.addActionListener(e -> showLoginView());

        mainFrame.revalidate();
        mainFrame.repaint();
        registrationView.getRegisterButton().addActionListener(e -> {
            if(validateRegistration(registrationView)) {
                enregistrerUtilisateur(registrationView);
                showDashboard();
            }
        });
    }
    private void enregistrerUtilisateur(RegistrationView view) {

    }
    private boolean validateRegistration(RegistrationView view) {
       /* // Validation du nom
        if(view.getName().trim().isEmpty()) {
            showError("Le nom complet est obligatoire");
            return false;
        }

        // Validation email avec regex
        if(!view.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            showError("Format d'email invalide");
            return false;
        }

        // Validation mot de passe
        if(view.getPassword().length() < 8) {
            showError("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }

        // Validation conducteur
        if(view.isDriver() && view.getCarModel().isEmpty()) {
            showError("Veuillez saisir le modèle de votre véhicule");
            return false;
        }
*/
        return true;
    }

    private void showDashboard() {
        DashboardView dashboardView = new DashboardView();
        mainFrame.dispose(); // Ferme la fenêtre de login
        dashboardView.setVisible(true);
    }
    private void showProfileView() {
        ProfileView profileView = new ProfileView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(profileView);

        // Charger les données utilisateur
       /* User currentUser = UserService.getCurrentUser();
        profileView.setUserData(
                currentUser.getName(),
                currentUser.getEmail(),
                currentUser.getPhone(),
                currentUser.isDriver(),
                currentUser.getCarModel()
        );
*/
        // Gestion des boutons
        profileView.getSaveButton().addActionListener(e -> {
            if(validateProfile(profileView)) {
                updateUserProfile(profileView);
                showDashboard();
            }
        });

        profileView.getCancelButton().addActionListener(e -> showDashboard());

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private boolean validateProfile(ProfileView view) {
       /* if(view.getName().isEmpty()) {
            showError("Le nom ne peut pas être vide");
            return false;
        }

        if(!view.getNewPassword().isEmpty() && view.getNewPassword().length() < 8) {
            showError("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }
*/
        return true;
    }

    private void updateUserProfile(ProfileView view) {

    }
}
