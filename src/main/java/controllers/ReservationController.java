package controllers;
import daos.ReservationDAO;
import models.Reservation;
import java.util.List;
import java.util.Optional;

public class ReservationController {
    private final ReservationDAO reservationDAO;

    public ReservationController() {
        this.reservationDAO = new ReservationDAO();
    }

    public int creerReservation(int passagerId, int trajetId, int nombrePlaces) {
        // Validation des données
        if (passagerId <= 0) {
            throw new IllegalArgumentException("ID passager invalide");
        }
        if (trajetId <= 0) {
            throw new IllegalArgumentException("ID trajet invalide");
        }
        if (nombrePlaces <= 0) {
            throw new IllegalArgumentException("Le nombre de places doit être positif");
        }

        return reservationDAO.creerReservation(passagerId, trajetId, nombrePlaces);
    }

    public Optional<Reservation> obtenirReservation(int id) {
        return Optional.ofNullable(reservationDAO.trouverParId(id));
    }

    public List<Reservation> obtenirToutesLesReservations() {
        return reservationDAO.listerTous();
    }

    public List<Reservation> obtenirReservationsParPassager(int passagerId) {
        return reservationDAO.listerReservationsPassager(passagerId);
    }

    public List<Reservation> obtenirReservationsParTrajet(int trajetId) {
        return reservationDAO.listerReservationsTrajet(trajetId);
    }

    public boolean confirmerReservation(int id) {
        Optional<Reservation> reservationOpt = Optional.ofNullable(reservationDAO.trouverParId(id));
        if (reservationOpt.isPresent()) {
            return reservationDAO.mettreAJourStatutReservation(id,"confirmée");
        }
        return false;
    }

    public boolean annulerReservation(int id) {
        Optional<Reservation> reservationOpt = Optional.ofNullable(reservationDAO.trouverParId(id));
        if (reservationOpt.isPresent()) {
            return reservationDAO.mettreAJourStatutReservation(id,"annulée");
        }
        return false;
    }

    public boolean mettreAJourReservation(Reservation reservation) {
        if (reservation == null || reservation.getId() <= 0) {
            return false;
        }
        return reservationDAO.mettreAJourReservation(reservation);
    }
}
