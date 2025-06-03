package daos;
import db.DatabaseConnection;
import models.UtilisateurInscrit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe d'accès aux données pour les utilisateurs inscrits
 */
public class UtilisateurDAO {

    /**
     * Insère un nouvel utilisateur dans la base de données
     * @param utilisateur l'utilisateur à insérer
     * @return l'ID généré pour l'utilisateur
     */
    public int insererUtilisateur(UtilisateurInscrit utilisateur) {
        int generatedId = -1;
        String sql = "CALL creer_compte(?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getEmail());
            stmt.setString(4, utilisateur.getMotDePasse());
            stmt.setString(5, utilisateur.getTelephone());

            stmt.executeUpdate();

            // Retrieve generated ID
            try (PreparedStatement idStmt = conn.prepareStatement(
                    "SELECT id FROM UtilisateurInscrit WHERE email = ?")) {
                idStmt.setString(1, utilisateur.getEmail());
                ResultSet rs = idStmt.executeQuery();
                if (rs.next()) {
                    generatedId = rs.getInt("id");
                    utilisateur.setId(generatedId);
                }
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'insertion d'un utilisateur");
            e.printStackTrace();
        }
        return generatedId;
    }

    /**
     * Supprime un utilisateur de la base de données
     * @param id l'ID de l'utilisateur à supprimer
     * @return true si la suppression a réussi, false sinon
     */
    public boolean supprimerUtilisateur(int id) {
        String sql = "DELETE FROM UtilisateurInscrit WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression d'un utilisateur");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère un utilisateur par son ID
     * @param id l'ID de l'utilisateur
     * @return l'utilisateur correspondant ou null si non trouvé
     */
    public UtilisateurInscrit trouverParId(int id) {
        UtilisateurInscrit utilisateur = null;
        String sql = "SELECT * FROM UtilisateurInscrit WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = extraireUtilisateurDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'un utilisateur par ID");
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Recherche un utilisateur par son email
     * @param email l'email à rechercher
     * @return l'utilisateur correspondant ou null si non trouvé
     */
    public UtilisateurInscrit trouverParEmail(String email) {
        UtilisateurInscrit utilisateur = null;
        String sql = "SELECT * FROM UtilisateurInscrit WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = extraireUtilisateurDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la recherche d'un utilisateur par email");
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Vérifie les identifiants de connexion d'un utilisateur
     * @param email l'email de l'utilisateur
     * @param motDePasse le mot de passe (non haché)
     * @return l'utilisateur si connexion réussie, null sinon
     */
    public UtilisateurInscrit verifierConnexion(String email, String motDePasse) {
        UtilisateurInscrit utilisateur = null;
        String sql = "SELECT * FROM UtilisateurInscrit WHERE email = ? AND motDePasse = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, motDePasse); // Dans une vraie application, comparer le hash du mot de passe
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = extraireUtilisateurDuResultSet(rs);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la vérification des informations de connexion");
            e.printStackTrace();
        }

        return utilisateur;
    }

    /**
     * Met à jour les informations d'un utilisateur
     * @param utilisateur l'utilisateur avec les nouvelles informations
     * @return true si la mise à jour a réussi, false sinon
     */
    public boolean mettreAJourUtilisateur(UtilisateurInscrit utilisateur) {
        String sql = "UPDATE UtilisateurInscrit SET nom = ?, prenom = ?, " +
                "telephone = ?, statut = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, utilisateur.getNom());
            stmt.setString(2, utilisateur.getPrenom());
            stmt.setString(3, utilisateur.getTelephone());
            stmt.setString(4, utilisateur.getStatut());
            stmt.setInt(5, utilisateur.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.err.println("Erreur lors de la mise à jour d'un utilisateur");
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Liste tous les utilisateurs inscrits
     * @return une liste d'utilisateurs
     */
    public List<UtilisateurInscrit> listerTous() {
        List<UtilisateurInscrit> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM UtilisateurInscrit";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                UtilisateurInscrit utilisateur = extraireUtilisateurDuResultSet(rs);
                utilisateurs.add(utilisateur);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération de tous les utilisateurs");
            e.printStackTrace();
        }

        return utilisateurs;
    }

    /**
     * Extrait un objet UtilisateurInscrit d'un ResultSet
     * @param rs le ResultSet contenant les données d'un utilisateur
     * @return un objet UtilisateurInscrit
     * @throws SQLException si une erreur survient lors de l'accès aux données
     */
    private UtilisateurInscrit extraireUtilisateurDuResultSet(ResultSet rs) throws SQLException {
        UtilisateurInscrit utilisateur = new UtilisateurInscrit();
        utilisateur.setId(rs.getInt("id"));
        utilisateur.setNom(rs.getString("nom"));
        utilisateur.setPrenom(rs.getString("prenom"));
        utilisateur.setEmail(rs.getString("email"));
        utilisateur.setMotDePasse(rs.getString("motDePasse"));
        utilisateur.setTelephone(rs.getString("telephone"));
        utilisateur.setDateInscription(rs.getTimestamp("dateInscription"));
        utilisateur.setStatut(rs.getString("statut"));
        utilisateur.setTypeUtilisateur(rs.getString("type_utilisateur"));
        return utilisateur;
    }
}