package services;
import controllers.*;
import models.*;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class CovoiturageService {
    private final UtilisateurController utilisateurController;
    private final ConducteurController conducteurController;
    private final TrajetController trajetController;
    private final ReservationController reservationController;

    public CovoiturageService() {
        this.utilisateurController = new UtilisateurController();
        this.conducteurController = new ConducteurController();
        this.trajetController = new TrajetController();
        this.reservationController = new ReservationController();
    }

    // ===== GESTION DES UTILISATEURS =====

    public int inscrireUtilisateur(String nom, String prenom, String email,
                                   String motDePasse, String telephone) {
        return utilisateurController.creerCompte(nom, prenom, email, motDePasse, telephone);
    }

    public Optional<UtilisateurInscrit> connecterUtilisateur(String email, String motDePasse) {
        UtilisateurInscrit utilisateur = utilisateurController.authentifier(email, motDePasse);
        if (utilisateur != null) {
            return Optional.of(utilisateur);
        }
        return Optional.empty();
    }


    public boolean devenirConducteur(Conducteur c) {
        return conducteurController.ajouterConducteur(c);
    }

    // ===== GESTION DES TRAJETS =====

    public int publierTrajet(String lieuDepart, String lieuArrivee, Date dateDepart,
                             int nbPlaces, double prix, int conducteurId) {
        return trajetController.creerTrajet(lieuDepart, lieuArrivee, dateDepart, nbPlaces, prix, conducteurId);
    }

    public List<Trajet> rechercherTrajets(String depart, String arrivee, Date date) {
        return trajetController.rechercherTrajets(depart, arrivee, date);
    }

    public List<Trajet> obtenirMesTrajets(int conducteurId) {
        return trajetController.obtenirTrajetsParConducteur(conducteurId);
    }

    // ===== GESTION DES RÉSERVATIONS =====

    public int reserverTrajet(int passagerId, int trajetId, int nombrePlaces) {
        return reservationController.creerReservation(passagerId, trajetId, nombrePlaces);
    }

    public List<Reservation> obtenirMesReservations(int passagerId) {
        return reservationController.obtenirReservationsParPassager(passagerId);
    }

    public boolean confirmerReservation(int reservationId) {
        return reservationController.confirmerReservation(reservationId);
    }

    public boolean annulerReservation(int reservationId) {
        return reservationController.annulerReservation(reservationId);
    }

    // ===== MÉTHODES UTILITAIRES =====

    public Optional<UtilisateurInscrit> obtenirUtilisateur(int id) {
        return Optional.ofNullable(utilisateurController.obtenirUtilisateur(id));
    }

    public Optional<Trajet> obtenirTrajet(int id) {
        return trajetController.obtenirTrajet(id);
    }

    public Optional<Reservation> obtenirReservation(int id) {
        return reservationController.obtenirReservation(id);
    }
}
