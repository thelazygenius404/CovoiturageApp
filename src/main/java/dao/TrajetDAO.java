package dao;
import db.DatabaseConnection;
import model.Conducteur;
import model.Trajet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class TrajetDAO {

    /**
     * Insère un nouveau trajet dans la base de données
     * @param trajet le trajet à insérer
     * @return l'ID généré pour le trajet
     */
    public int insererTrajet(Trajet trajet) {
        int generatedId = -1;
        String sql = "INSERT INTO Trajet (lieuDepart, lieuArrivee, dateDepart, nbPlacesDisponibles, " +
                "prix, statut, conducteurId) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, trajet.getLieuDepart());
            stmt.setString(2, trajet.getLieuArrivee());
            stmt.setTimestamp(3, new Timestamp(trajet.getDateDepart().getTime()));
            stmt.setInt(4, trajet.getNbPlacesDisponibles());
            stmt.setDouble(5, trajet.getPrix());
            stmt.setString(6, trajet.getStatut());
            stmt.setInt(7, trajet.getConducteurId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        generatedId = generatedKeys.getInt(1);
                        trajet.setId(generatedId);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion d'un trajet");
            e.printStackTrace();
        }

        return generatedId;
    }

    /**
     * Recherche un trajet par son ID
     * @param id l'ID du trajet
     * @return le trajet correspondant ou null si non trouvé
     */
    public Trajet trouverParId(int id) {
        Trajet trajet = null;
        String sql = "SELECT * FROM Trajet WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                trajet = extraireTrajetDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'un trajet par ID");
            e.printStackTrace();
        }

        return trajet;
    }

    /**
     * Recherche des trajets selon les critères
     * @param lieuDepart le lieu de départ à rechercher
     * @param lieuArrivee le lieu d'arrivée à rechercher
     * @param dateDepart la date de départ à rechercher
     * @return une liste de trajets correspondant aux critères
     */
    public List<Trajet> rechercherTrajets(String lieuDepart, String lieuArrivee, Date dateDepart) {
        List<Trajet> trajets = new ArrayList<>();
        String sql = "CALL rechercher_trajets(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, lieuDepart);
            stmt.setString(2, lieuArrivee);
            stmt.setDate(3, new java.sql.Date(dateDepart.getTime()));

            boolean hasResults = stmt.execute();
            if (hasResults) {
                try (ResultSet rs = stmt.getResultSet()) {
                    while (rs.next()) {
                        Trajet trajet = extraireTrajetDuResultSet(rs);
                        trajets.add(trajet);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche de trajets");
            e.printStackTrace();
        }

        return trajets;
    }

    /**
     * Liste tous les trajets d'un conducteur
     * @param conducteurId l'ID du conducteur
     * @return une liste de trajets du conducteur
     */
    public List<Trajet> listerTrajetsDuConducteur(int conducteurId) {
        List<Trajet> trajets = new ArrayList<>();
        String sql = "SELECT * FROM Trajet WHERE conducteurId = ? ORDER BY dateDepart DESC";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, conducteurId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Trajet trajet = extraireTrajetDuResultSet(rs);
                trajets.add(trajet);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des trajets d'un conducteur");
            e.printStackTrace();
        }

        return trajets;
    }

    /**
     * Met à jour un trajet
     * @param trajet le trajet avec les nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourTrajet(Trajet trajet) {
        String sql = "UPDATE Trajet SET lieuDepart = ?, lieuArrivee = ?, dateDepart = ?, " +
                "nbPlacesDisponibles = ?, prix = ?, statut = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, trajet.getLieuDepart());
            stmt.setString(2, trajet.getLieuArrivee());
            stmt.setTimestamp(3, new Timestamp(trajet.getDateDepart().getTime()));
            stmt.setInt(4, trajet.getNbPlacesDisponibles());
            stmt.setDouble(5, trajet.getPrix());
            stmt.setString(6, trajet.getStatut());
            stmt.setInt(7, trajet.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un trajet");
            e.printStackTrace();
            return false;
        }
    }
    public List<Trajet> listerTous() {
        List<Trajet> trajets = new ArrayList<>();
        String sql = "SELECT * FROM Trajet";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Trajet trajet = extraireTrajetDuResultSet(rs);
                trajets.add(trajet);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les Conducteurs");
            e.printStackTrace();
        }

        return trajets;
    }
    /**
     * Supprime un trajet de la base de données
     * @param id l'ID du trajet à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerTrajet(int id) {
        String sql = "DELETE FROM Trajet WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un trajet");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extrait un objet Trajet d'un ResultSet
     * @param rs le ResultSet contenant les données d'un trajet
     * @return un objet Trajet
     * @throws SQLException si une erreur survient lors de l'accès aux données
     */
    private Trajet extraireTrajetDuResultSet(ResultSet rs) throws SQLException {
        Trajet trajet = new Trajet();
        trajet.setId(rs.getInt("id"));
        trajet.setLieuDepart(rs.getString("lieuDepart"));
        trajet.setLieuArrivee(rs.getString("lieuArrivee"));
        trajet.setDateDepart(rs.getTimestamp("dateDepart"));
        trajet.setNbPlacesDisponibles(rs.getInt("nbPlacesDisponibles"));
        trajet.setPrix(rs.getDouble("prix"));
        trajet.setStatut(rs.getString("statut"));
        trajet.setConducteurId(rs.getInt("conducteurId"));

        // Gestion de champs qui peuvent être NULL
        int apiId = rs.getInt("apiCartographieId");
        if (!rs.wasNull()) {
            trajet.setApiCartographieId(apiId);
        }

        return trajet;
    }
}
