package dao;
import db.DatabaseConnection;
import model.Reservation;
import model.Trajet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class ReservationDAO {
    /**
     * Crée une nouvelle réservation
     * @param passagerId ID du passager
     * @param trajetId ID du trajet
     * @param nombrePlaces nombre de places à réserver
     * @return l'ID de la réservation créée, ou -1 en cas d'échec
     */
    public int creerReservation(int passagerId, int trajetId, int nombrePlaces) {
        int reservationId = -1;
        String sql = "CALL creer_reservation(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, passagerId);
            stmt.setInt(2, trajetId);
            stmt.setInt(3, nombrePlaces);

            stmt.execute();

            // Récupérer l'ID de la dernière réservation
            String idSql = "SELECT LAST_INSERT_ID() as id";
            try (Statement idStmt = conn.createStatement();
                 ResultSet rs = idStmt.executeQuery(idSql)) {
                if (rs.next()) {
                    reservationId = rs.getInt("id");
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la création d'une réservation");
            e.printStackTrace();
        }

        return reservationId;
    }

    /**
     * Récupère une réservation par son ID
     * @param id l'ID de la réservation
     * @return la réservation correspondante ou null si non trouvée
     */
    public Reservation trouverParId(int id) {
        Reservation reservation = null;
        String sql = "SELECT * FROM Reservation WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                reservation = extraireReservationDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'une réservation par ID");
            e.printStackTrace();
        }

        return reservation;
    }
    public List<Reservation> listerTous() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM Reservation";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Reservation reservation = extraireReservationDuResultSet(rs);
                reservations.add(reservation);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les Conducteurs");
            e.printStackTrace();
        }

        return reservations;
    }
    /**
     * Liste toutes les réservations d'un passager
     * @param passagerId l'ID du passager
     * @return une liste de réservations du passager
     */
    public List<Reservation> listerReservationsPassager(int passagerId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, t.lieuDepart, t.lieuArrivee, t.dateDepart " +
                "FROM Reservation r " +
                "JOIN Trajet t ON r.trajetId = t.id " +
                "WHERE r.passagerId = ? " +
                "ORDER BY r.dateReservation DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, passagerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = extraireReservationDuResultSet(rs);
                reservations.add(reservation);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des réservations d'un passager");
            e.printStackTrace();
        }

        return reservations;
    }

    /**
     * Liste toutes les réservations pour un trajet donné
     * @param trajetId l'ID du trajet
     * @return une liste de réservations pour ce trajet
     */
    public List<Reservation> listerReservationsTrajet(int trajetId) {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT r.*, u.nom, u.prenom " +
                "FROM Reservation r " +
                "JOIN Passager p ON r.passagerId = p.id " +
                "JOIN UtilisateurInscrit u ON p.id = u.id " +
                "WHERE r.trajetId = ? " +
                "ORDER BY r.dateReservation";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, trajetId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = extraireReservationDuResultSet(rs);
                reservations.add(reservation);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des réservations d'un trajet");
            e.printStackTrace();
        }

        return reservations;
    }

    /**
     * Met à jour le statut d'une réservation
     * @param id l'ID de la réservation
     * @param statut le nouveau statut ("en_attente", "confirmée", "annulée")
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourStatutReservation(int id, String statut) {
        String sql = "UPDATE Reservation SET statut = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, statut);
            stmt.setInt(2, id);

            int rowsAffected = stmt.executeUpdate();

            // Si la réservation est annulée, remettre les places à disposition
            if (statut.equals("annulée")) {
                remettreEnDisponibiliteLesPlaces(id);
            }

            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour du statut d'une réservation");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Remet en disponibilité les places d'une réservation annulée
     * @param reservationId l'ID de la réservation
     */
    private void remettreEnDisponibiliteLesPlaces(int reservationId) {
        String sql = "UPDATE Trajet t " +
                "JOIN Reservation r ON t.id = r.trajetId " +
                "SET t.nbPlacesDisponibles = t.nbPlacesDisponibles + r.nombrePlaces " +
                "WHERE r.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, reservationId);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.err.println("Erreur lors de la remise en disponibilité des places");
            e.printStackTrace();
        }
    }
    /**
     * Met à jour les informations d'une réservation existante
     * @param reservation l'objet Reservation avec les nouvelles valeurs
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourReservation(Reservation reservation) {
        String sql = "UPDATE Reservation SET dateReservation = ?, statut = ?, nombrePlaces = ?, " +
                "prixTotal = ?, passagerId = ?, trajetId = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, (Timestamp) reservation.getDateReservation());
            stmt.setString(2, reservation.getStatut());
            stmt.setInt(3, reservation.getNombrePlaces());
            stmt.setDouble(4, reservation.getPrixTotal());
            stmt.setInt(5, reservation.getPassagerId());
            stmt.setInt(6, reservation.getTrajetId());
            stmt.setInt(7, reservation.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour de la réservation");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extrait un objet Reservation d'un ResultSet
     * @param rs le ResultSet contenant les données d'une réservation
     * @return un objet Reservation
     * @throws SQLException si une erreur survient lors de l'accès aux données
     */
    private Reservation extraireReservationDuResultSet(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getInt("id"));
        reservation.setDateReservation(rs.getTimestamp("dateReservation"));
        reservation.setStatut(rs.getString("statut"));
        reservation.setNombrePlaces(rs.getInt("nombrePlaces"));
        reservation.setPrixTotal(rs.getDouble("prixTotal"));
        reservation.setPassagerId(rs.getInt("passagerId"));
        reservation.setTrajetId(rs.getInt("trajetId"));
        return reservation;
    }
}
