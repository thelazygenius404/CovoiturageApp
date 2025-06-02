package dao;
import db.DatabaseConnection;
import model.Passager;
import java.sql.*;
public class PassagerDAO {
    /**
     * Ajoute un utilisateur comme passager
     * @param passager le passager à ajouter
     * @return true si l'ajout a réussi, false sinon
     */
    public boolean ajouterPassager(Passager passager) {
        String sql = "CALL ajouter_passager(?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, passager.getId());
            stmt.setString(2, passager.getMethodePaiement());
            stmt.setString(3, passager.getPreferences());

            stmt.execute();
            return true;
        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout d'un passager");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère un passager par son ID utilisateur
     * @param id l'ID de l'utilisateur
     * @return le passager correspondant ou null si non trouvé
     */
    public Passager trouverParId(int id) {
        Passager passager = null;
        String sql = "SELECT u.*, p.methodePaiement, p.preferences " +
                "FROM UtilisateurInscrit u " +
                "JOIN Passager p ON u.id = p.id " +
                "WHERE u.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                passager = extrairePassagerDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'un passager par ID");
            e.printStackTrace();
        }

        return passager;
    }

    /**
     * Met à jour les informations d'un passager
     * @param passager le passager avec les nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourPassager(Passager passager) {
        // Mettre à jour d'abord l'utilisateur inscrit
        UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
        boolean utilisateurMaj = utilisateurDAO.mettreAJourUtilisateur(passager);

        if (!utilisateurMaj) {
            return false;
        }

        // Puis mettre à jour les infos spécifiques au passager
        String sql = "UPDATE Passager SET methodePaiement = ?, preferences = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, passager.getMethodePaiement());
            stmt.setString(2, passager.getPreferences());
            stmt.setInt(3, passager.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un passager");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Extrait un objet Passager d'un ResultSet
     * @param rs le ResultSet contenant les données d'un passager
     * @return un objet Passager
     * @throws SQLException si une erreur survient lors de l'accès aux données
     */
    private Passager extrairePassagerDuResultSet(ResultSet rs) throws SQLException {
        Passager passager = new Passager();

        // Attributs d'UtilisateurInscrit
        passager.setId(rs.getInt("id"));
        passager.setNom(rs.getString("nom"));
        passager.setPrenom(rs.getString("prenom"));
        passager.setEmail(rs.getString("email"));
        passager.setMotDePasse(rs.getString("motDePasse"));
        passager.setTelephone(rs.getString("telephone"));
        passager.setDateInscription(rs.getTimestamp("dateInscription"));
        passager.setStatut(rs.getString("statut"));
        passager.setTypeUtilisateur(rs.getString("type_utilisateur"));

        // Attributs spécifiques au passager
        passager.setMethodePaiement(rs.getString("methodePaiement"));
        passager.setPreferences(rs.getString("preferences"));

        return passager;
    }
}
