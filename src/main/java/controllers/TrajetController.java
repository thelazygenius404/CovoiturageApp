package controllers;
import daos.TrajetDAO;
import models.Trajet;
import java.util.List;
import java.util.Date;
import java.util.Optional;

public class TrajetController {
    private final TrajetDAO trajetDAO;

    public TrajetController() {
        this.trajetDAO = new TrajetDAO();
    }

    public int creerTrajet(String lieuDepart, String lieuArrivee, Date dateDepart,
                           int nbPlacesDisponibles, double prix, int conducteurId) {
        // Validation des données
        if (lieuDepart == null || lieuDepart.trim().isEmpty()) {
            throw new IllegalArgumentException("Le lieu de départ est obligatoire");
        }
        if (lieuArrivee == null || lieuArrivee.trim().isEmpty()) {
            throw new IllegalArgumentException("Le lieu d'arrivée est obligatoire");
        }
        if (dateDepart == null || !dateDepart.after(new Date())) {
            throw new IllegalArgumentException("La date de départ doit être dans le futur");
        }
        if (nbPlacesDisponibles <= 0) {
            throw new IllegalArgumentException("Le nombre de places doit être positif");
        }
        if (prix == 0.0 || prix < 0.0) {
            throw new IllegalArgumentException("Le prix doit être positif");
        }
        if (conducteurId <= 0) {
            throw new IllegalArgumentException("ID conducteur invalide");
        }

        Trajet trajet = new Trajet(lieuDepart, lieuArrivee, dateDepart, nbPlacesDisponibles, prix, conducteurId);
        return trajetDAO.insererTrajet(trajet);
    }

    public List<Trajet> rechercherTrajets(String lieuDepart, String lieuArrivee, Date dateDepart) {
        if (lieuDepart == null || lieuArrivee == null || dateDepart == null) {
            throw new IllegalArgumentException("Tous les critères de recherche sont obligatoires");
        }
        return trajetDAO.rechercherTrajets(lieuDepart,lieuArrivee,new java.sql.Date(dateDepart.getTime()));
    }

    public Optional<Trajet> obtenirTrajet(int id) {
        return Optional.ofNullable(trajetDAO.trouverParId(id));
    }
    public List<Trajet> obtenirTousLesTrajets() {
        return trajetDAO.listerTous();
    }

    public List<Trajet> obtenirTrajetsParConducteur(int conducteurId) {
        return trajetDAO.listerTrajetsDuConducteur(conducteurId);
    }

    public boolean mettreAJourTrajet(Trajet trajet) {
        if (trajet == null || trajet.getId() <= 0) {
            return false;
        }
        return trajetDAO.mettreAJourTrajet(trajet);
    }

    public boolean supprimerTrajet(int id) {
        if (id <= 0) {
            return false;
        }
        return trajetDAO.supprimerTrajet(id);
    }

    public boolean annulerTrajet(int id) {
        Trajet trajet = trajetDAO.trouverParId(id);
        if (!"annulé".equalsIgnoreCase(trajet.getStatut()) && !"terminé".equalsIgnoreCase(trajet.getStatut())) {
            trajet.setStatut("annulé");
            return trajetDAO.mettreAJourTrajet(trajet);
        }
        return false;
    }
}