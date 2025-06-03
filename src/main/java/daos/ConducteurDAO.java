package daos;
import db.DatabaseConnection;
import models.Conducteur;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConducteurDAO {

    /**
     * Ajoute un utilisateur comme conducteur
     * @param conducteur le conducteur à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterConducteur(Conducteur conducteur) {
        String sql = "CALL ajouter_conducteur(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, conducteur.getId());
            stmt.setString(2, conducteur.getPermisConduire());
            stmt.setString(3, conducteur.getVehicule());

            stmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'ajout d'un conducteur");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Récupère un conducteur par son ID utilisateur
     * @param id l'ID de l'utilisateur
     * @return le conducteur correspondant ou null si non trouvé
     */
    public Conducteur trouverParId(int id) {
        Conducteur conducteur = null;
        String sql = "SELECT u.*, c.permisConduire, c.vehicule, c.note " +
                "FROM UtilisateurInscrit u " +
                "JOIN Conducteur c ON u.id = c.id " +
                "WHERE u.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                conducteur = extraireConducteurDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'un conducteur par ID");
            e.printStackTrace();
        }

        return conducteur;
    }
    public boolean supprimerConducteur(int id) {
        String sql = "DELETE FROM Conducteur WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un conducteur");
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Met à jour les informations d'un conducteur
     * @param conducteur le conducteur avec les nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourConducteur(Conducteur conducteur) {
        // Mettre à jour d'abord l'utilisateur inscrit
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        boolean utilisateurMaj = utilisateurDAO.mettreAJourUtilisateur(conducteur);

        if (!utilisateurMaj) {
            return false;
        }

        // Puis mettre à jour les infos spécifiques au conducteur
        String sql = "UPDATE Conducteur SET permisConduire = ?, vehicule = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, conducteur.getPermisConduire());
            stmt.setString(2, conducteur.getVehicule());
            stmt.setInt(3, conducteur.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un conducteur");
            e.printStackTrace();
            return false;
        }
    }
    public List<Conducteur> listerTous() {
        List<Conducteur> conducteurs = new ArrayList<>();
        String sql = "SELECT * FROM Conducteur";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Conducteur conducteur = extraireConducteurDuResultSet(rs);
                conducteurs.add(conducteur);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les Conducteurs");
            e.printStackTrace();
        }

        return conducteurs;
    }
    /**
     * Extrait un objet Conducteur d'un ResultSet
     * @param rs le ResultSet contenant les données d'un conducteur
     * @return un objet Conducteur
     * @throws SQLException si une erreur survient lors de l'accès aux données
     */
    private Conducteur extraireConducteurDuResultSet(ResultSet rs) throws SQLException {
        Conducteur conducteur = new Conducteur();

        // Attributs d'UtilisateurInscrit
        conducteur.setId(rs.getInt("id"));
        conducteur.setNom(rs.getString("nom"));
        conducteur.setPrenom(rs.getString("prenom"));
        conducteur.setEmail(rs.getString("email"));
        conducteur.setMotDePasse(rs.getString("motDePasse"));
        conducteur.setTelephone(rs.getString("telephone"));
        conducteur.setDateInscription(rs.getTimestamp("dateInscription"));
        conducteur.setStatut(rs.getString("statut"));
        conducteur.setTypeUtilisateur(rs.getString("type_utilisateur"));

        // Attributs spécifiques au conducteur
        conducteur.setPermisConduire(rs.getString("permisConduire"));
        conducteur.setVehicule(rs.getString("vehicule"));
        conducteur.setNote(rs.getFloat("note"));

        return conducteur;
    }
}
