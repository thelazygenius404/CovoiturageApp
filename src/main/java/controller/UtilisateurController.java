package controller;

import dao.UtilisateurDAO;
import model.UtilisateurInscrit;

import java.util.List;

public class UtilisateurController {
    private final UtilisateurDAO utilisateurDAO;

    public UtilisateurController() {
        this.utilisateurDAO = new UtilisateurDAO();
    }

    public int creerCompte(String nom, String prenom, String email, String motDePasse, String telephone) {
        // Validation des données
        if (nom == null || nom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le nom est obligatoire");
        }
        if (prenom == null || prenom.trim().isEmpty()) {
            throw new IllegalArgumentException("Le prénom est obligatoire");
        }
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Email invalide");
        }
        if (motDePasse == null || motDePasse.length() < 6) {
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }

        // Vérifier si l'email existe déjà
        if (utilisateurDAO.trouverParEmail(email)!=null) {
            throw new IllegalArgumentException("Cet email est déjà utilisé");
        }

        UtilisateurInscrit utilisateur = new UtilisateurInscrit(nom, prenom, email, motDePasse, telephone);
        return utilisateurDAO.insererUtilisateur(utilisateur);
    }

    public UtilisateurInscrit authentifier(String email, String motDePasse) {
        if (email == null || motDePasse == null) {
            return null;
        }
        return utilisateurDAO.verifierConnexion(email, motDePasse);
    }

    public UtilisateurInscrit obtenirUtilisateurParEmail(String email) {
        return utilisateurDAO.trouverParEmail(email);
    }

    public UtilisateurInscrit obtenirUtilisateur(int id) {
        return utilisateurDAO.trouverParId(id);
    }

    public List<UtilisateurInscrit> obtenirTousLesUtilisateurs() {
        return utilisateurDAO.listerTous();
    }

    public boolean mettreAJourUtilisateur(UtilisateurInscrit utilisateur) {
        if (utilisateur == null || utilisateur.getId() <= 0) {
            return false;
        }
        return utilisateurDAO.mettreAJourUtilisateur(utilisateur);
    }

    public boolean supprimerUtilisateur(int id) {
        if (id <= 0) {
            return false;
        }
        return utilisateurDAO.supprimerUtilisateur(id);
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }
}
