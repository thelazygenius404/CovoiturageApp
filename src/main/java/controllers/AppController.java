package controllers;

import views.*;
import javax.swing.*;

public class AppController {
    private JFrame mainFrame;

    public AppController(JFrame frame) {
        this.mainFrame = frame;
        showHomepage(); // Start with homepage instead of login
    }

    private void showHomepage() {
        HomepageView homepageView = new HomepageView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(homepageView);

        // Add action listeners for homepage buttons
        homepageView.getLoginButton().addActionListener(e -> showLoginView());
        homepageView.getRegisterButton().addActionListener(e -> showRegistrationView());
        homepageView.getExploreButton().addActionListener(e -> {
            // For now, redirect to login. In a real app, this might show public trajets
            showLoginView();
        });

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showLoginView() {
        LoginView loginView = new LoginView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(loginView);

        loginView.getLoginButton().addActionListener(e -> {
            // Add validation logic here
            String email = loginView.getEmail();
            String password = loginView.getPassword();

            if (validateLogin(email, password)) {
                showDashboard();
            } else {
                loginView.showError("Email ou mot de passe incorrect");
            }
        });

        loginView.getRegisterButton().addActionListener(e -> showRegistrationView());

        // Add a "back to homepage" functionality (optional)
        // You could add this as a button or link in the LoginView if desired

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void showRegistrationView() {
        RegistrationView registrationView = new RegistrationView();
        mainFrame.getContentPane().removeAll();
        mainFrame.add(registrationView);

        // Get the buttons
        JButton registerBtn = registrationView.getRegisterButton();
        JButton cancelBtn = registrationView.getCancelButton();

        // Handle registration
        registerBtn.addActionListener(e -> {
            if(validateRegistration(registrationView)) {
                enregistrerUtilisateur(registrationView);
                registrationView.showSuccess("Compte créé avec succès!");
                // Redirect to login after successful registration
                Timer timer = new Timer(2000, event -> showLoginView());
                timer.setRepeats(false);
                timer.start();
            }
        });

        // Handle cancel - go back to homepage
        cancelBtn.addActionListener(e -> showHomepage());

        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void enregistrerUtilisateur(RegistrationView view) {
        // Implementation for user registration
        // This would typically involve:
        // 1. Creating a new user with the provided details
        // 2. Saving to database
        // 3. If driver is selected, also creating driver record

        System.out.println("Registering user: " + view.getName());
        System.out.println("Email: " + view.getEmail());
        System.out.println("Is driver: " + view.isDriver());
        if (view.isDriver()) {
            System.out.println("Car model: " + view.getCarModel());
        }
    }

    private boolean validateLogin(String email, String password) {
        // Validation logic for login
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        // Add actual authentication logic here
        // For demo purposes, accept any non-empty credentials
        return true;
    }

    private boolean validateRegistration(RegistrationView view) {
        // Clear any previous error messages
        view.hideError();

        // Validation du nom
        if(view.getName().trim().isEmpty()) {
            view.showError("Le nom complet est obligatoire");
            return false;
        }

        // Validation email avec regex
        if(!view.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            view.showError("Format d'email invalide");
            return false;
        }

        // Validation mot de passe
        if(view.getPassword().length() < 8) {
            view.showError("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }

        // Validation téléphone
        if(view.getPhone().trim().isEmpty()) {
            view.showError("Le numéro de téléphone est obligatoire");
            return false;
        }

        // Validation conducteur
        if(view.isDriver() && view.getCarModel().trim().isEmpty()) {
            view.showError("Veuillez saisir le modèle de votre véhicule");
            return false;
        }

        return true;
    }

    private void showDashboard() {
        DashboardView dashboardView = new DashboardView();
        mainFrame.dispose(); // Close the current window
        dashboardView.setVisible(true);

        // Add action listeners for dashboard buttons
        dashboardView.getSearchButton().addActionListener(e -> showSearchView());
        dashboardView.getCreateRideButton().addActionListener(e -> showRideCreationView());
        dashboardView.getViewBookingsButton().addActionListener(e -> showBookingsView());
        dashboardView.getProfileButton().addActionListener(e -> showProfileView());
        dashboardView.getLogoutButton().addActionListener(e -> {
            dashboardView.dispose();
            // Create new main frame for homepage
            JFrame newFrame = new JFrame("CarPool");
            newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            newFrame.setSize(800, 600);
            newFrame.setLocationRelativeTo(null);
            new AppController(newFrame);
            newFrame.setVisible(true);
        });
    }

    private void showSearchView() {
        // Implementation for search view
        // This could be a new view for searching rides
        System.out.println("Search view - to be implemented");
    }

    private void showRideCreationView() {
        RideCreationView rideCreationView = new RideCreationView();

        // Create a new window for ride creation
        JFrame rideFrame = new JFrame("Créer un trajet - CarPool");
        rideFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rideFrame.setSize(800, 700);
        rideFrame.setLocationRelativeTo(null);
        rideFrame.add(rideCreationView);

        // Add action listeners
        rideCreationView.getCreateButton().addActionListener(e -> {
            if (validateRideCreation(rideCreationView)) {
                createRide(rideCreationView);
                rideCreationView.showSuccess("Trajet créé avec succès!");
                Timer timer = new Timer(2000, event -> rideFrame.dispose());
                timer.setRepeats(false);
                timer.start();
            }
        });

        rideCreationView.getCancelButton().addActionListener(e -> rideFrame.dispose());

        rideFrame.setVisible(true);
    }

    private void showBookingsView() {
        // Implementation for bookings view
        System.out.println("Bookings view - to be implemented");
    }

    private void showProfileView() {
        ProfileView profileView = new ProfileView();

        // Create a new window for profile
        JFrame profileFrame = new JFrame("Mon Profil - CarPool");
        profileFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        profileFrame.setSize(700, 600);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.add(profileView);

        // Load user data (this would come from your user service)
        // For demo purposes, using sample data
        profileView.setUserData(
                "John Doe",
                "john.doe@email.com",
                "0123456789",
                true,
                "Toyota Corolla"
        );

        // Add action listeners
        profileView.getSaveButton().addActionListener(e -> {
            if(validateProfile(profileView)) {
                updateUserProfile(profileView);
                profileView.showSuccess("Profil mis à jour avec succès!");
            }
        });

        profileView.getCancelButton().addActionListener(e -> profileFrame.dispose());

        profileFrame.setVisible(true);
    }

    private boolean validateRideCreation(RideCreationView view) {
        view.hideMessages();

        if (view.getFromLocation().trim().isEmpty()) {
            view.showError("Le lieu de départ est obligatoire");
            return false;
        }

        if (view.getToLocation().trim().isEmpty()) {
            view.showError("La destination est obligatoire");
            return false;
        }

        if (view.getAvailableSeats() <= 0) {
            view.showError("Le nombre de places doit être positif");
            return false;
        }

        if (view.getPricePerSeat() <= 0) {
            view.showError("Le prix doit être positif");
            return false;
        }

        return true;
    }

    private void createRide(RideCreationView view) {
        // Implementation for creating a ride
        System.out.println("Creating ride from " + view.getFromLocation() +
                " to " + view.getToLocation());
        System.out.println("Seats: " + view.getAvailableSeats());
        System.out.println("Price: " + view.getPricePerSeat() + "€");
        System.out.println("Description: " + view.getDescription());
    }

    private boolean validateProfile(ProfileView view) {
        view.hideMessages();

        if(view.getName().trim().isEmpty()) {
            view.showError("Le nom ne peut pas être vide");
            return false;
        }

        if(!view.getNewPassword().isEmpty() && view.getNewPassword().length() < 8) {
            view.showError("Le mot de passe doit contenir au moins 8 caractères");
            return false;
        }

        if(view.getPhone().trim().isEmpty()) {
            view.showError("Le téléphone ne peut pas être vide");
            return false;
        }

        return true;
    }

    private void updateUserProfile(ProfileView view) {
        // Implementation for updating user profile
        System.out.println("Updating profile for: " + view.getName());
        System.out.println("Phone: " + view.getPhone());
        if (!view.getNewPassword().isEmpty()) {
            System.out.println("Password updated");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(mainFrame, message, "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}