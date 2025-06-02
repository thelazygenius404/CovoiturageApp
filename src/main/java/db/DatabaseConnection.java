package db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    // Configuration de la connexion
    private static final String BASE_URL = "jdbc:mariadb://localhost:3306/";
    private static final String DB_NAME = "systeme_covoiturage";
    private static final String URL = BASE_URL + DB_NAME;
    private static final String USER = "root";  // à remplacer par votre nom d'utilisateur
    private static final String PASSWORD = "";  // à remplacer par votre mot de passe
    private static final String driver = "org.mariadb.jdbc.Driver";
    private static Connection connection;

    /**
     * Établit une connexion à la base de données
     * @return l'objet Connection pour interagir avec la base de données
     */
    public static Connection getConnection() throws Exception {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to MySQL successfully!");
            return connection;
        } catch(Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Établit une connexion au serveur MySQL sans spécifier de base de données
     * @return l'objet Connection pour interagir avec le serveur
     */
    public static Connection getServerConnection() throws Exception {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(BASE_URL, USER, PASSWORD);
            System.out.println("Connected to MySQL server successfully!");
            return connection;
        } catch(Exception e) {
            System.out.println("Server connection failed!");
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Ferme la connexion à la base de données
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Connexion à la base de données fermée");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion");
                e.printStackTrace();
            }
        }
    }

    /**
     * Établit une connexion à une base de données spécifique
     * @param dbName Nom de la base de données
     * @return l'objet Connection pour interagir avec la base de données
     */
    public static Connection getConnectionWithDB(String dbName) throws Exception {
        String url = BASE_URL + dbName;

        // Register JDBC driver
        Class.forName(driver);

        // Open a connection
        Connection conn = DriverManager.getConnection(url, USER, PASSWORD);
        return conn;
    }

    /**
     * Crée la base de données et les tables nécessaires
     */
    public static void creatDB() throws Exception {
        // First connect to server without specifying a database
        connection = getServerConnection();
        Statement state = connection.createStatement();

        // Create database
        String DB = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
        state.executeUpdate(DB);

        // Close the initial connection
        if (connection != null) {
            connection.close();
        }

        // Reconnect with the specific database
        connection = getConnectionWithDB(DB_NAME);
        state = connection.createStatement();

        // Create tables
        DB = "CREATE TABLE IF NOT EXISTS UtilisateurInscrit (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    nom VARCHAR(100) NOT NULL," +
                "    prenom VARCHAR(100) NOT NULL," +
                "    email VARCHAR(150) NOT NULL UNIQUE," +
                "    motDePasse VARCHAR(255) NOT NULL," +
                "    telephone VARCHAR(20)," +
                "    dateInscription DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "    statut ENUM('actif', 'inactif', 'suspendu') DEFAULT 'actif'," +
                "    type_utilisateur ENUM('standard', 'passager', 'conducteur') DEFAULT 'standard'" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Passager (" +
                "    id INT PRIMARY KEY," +
                "    methodePaiement VARCHAR(50)," +
                "    preferences TEXT," +
                "    FOREIGN KEY (id) REFERENCES UtilisateurInscrit(id) ON DELETE CASCADE" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Conducteur (" +
                "    id INT PRIMARY KEY," +
                "    permisConduire VARCHAR(50) NOT NULL," +
                "    vehicule VARCHAR(100)," +
                "    note FLOAT DEFAULT 0," +
                "    FOREIGN KEY (id) REFERENCES UtilisateurInscrit(id) ON DELETE CASCADE" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Administrateur (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    nom VARCHAR(100) NOT NULL," +
                "    prenom VARCHAR(100) NOT NULL," +
                "    email VARCHAR(150) NOT NULL UNIQUE," +
                "    motDePasse VARCHAR(255) NOT NULL," +
                "    droitsAcces TEXT" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Notification (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "    contenu TEXT NOT NULL," +
                "    statut ENUM('non_lue', 'lue') DEFAULT 'non_lue'," +
                "    utilisateurId INT NOT NULL," +
                "    FOREIGN KEY (utilisateurId) REFERENCES UtilisateurInscrit(id) ON DELETE CASCADE" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS APICartographie (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    cle VARCHAR(255) NOT NULL," +
                "    fournisseur VARCHAR(100) NOT NULL" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Trajet (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    lieuDepart VARCHAR(255) NOT NULL," +
                "    lieuArrivee VARCHAR(255) NOT NULL," +
                "    dateDepart DATETIME NOT NULL," +
                "    nbPlacesDisponibles INT NOT NULL," +
                "    prix DECIMAL(10, 2) NOT NULL," +
                "    statut ENUM('planifié', 'en_cours', 'terminé', 'annulé') DEFAULT 'planifié'," +
                "    conducteurId INT NOT NULL," +
                "    apiCartographieId INT," +
                "    FOREIGN KEY (conducteurId) REFERENCES Conducteur(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (apiCartographieId) REFERENCES APICartographie(id)" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Reservation (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    dateReservation DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "    statut ENUM('en_attente', 'confirmée', 'annulée') DEFAULT 'en_attente'," +
                "    nombrePlaces INT NOT NULL," +
                "    prixTotal DECIMAL(10, 2) NOT NULL," +
                "    passagerId INT NOT NULL," +
                "    trajetId INT NOT NULL," +
                "    FOREIGN KEY (passagerId) REFERENCES Passager(id) ON DELETE CASCADE," +
                "    FOREIGN KEY (trajetId) REFERENCES Trajet(id) ON DELETE CASCADE" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS SystemePaiement (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    nom VARCHAR(100) NOT NULL," +
                "    commission DECIMAL(5, 2) NOT NULL," +
                "    reservationId INT," +
                "    FOREIGN KEY (reservationId) REFERENCES Reservation(id)" +
                ")";
        state.executeUpdate(DB);

        DB = "CREATE TABLE IF NOT EXISTS Avis (" +
                "    id INT AUTO_INCREMENT PRIMARY KEY," +
                "    note FLOAT NOT NULL CHECK (note BETWEEN 0 AND 5)," +
                "    commentaire TEXT," +
                "    date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "    reservationId INT UNIQUE, " +
                "    FOREIGN KEY (reservationId) REFERENCES Reservation(id) ON DELETE CASCADE" +
                ")";
        state.executeUpdate(DB);

        // Create indexes safely with try-catch blocks
        createIndex(connection, "CREATE INDEX idx_trajet_dates ON Trajet(dateDepart)");
        createIndex(connection, "CREATE INDEX idx_trajet_lieux ON Trajet(lieuDepart, lieuArrivee)");
        createIndex(connection, "CREATE INDEX idx_reservation_statut ON Reservation(statut)");
        createIndex(connection, "CREATE INDEX idx_utilisateur_email ON UtilisateurInscrit(email)");
        createStoredProcedures(connection);
        createTriggers(connection);
    }

    /**
     * Crée un index en gérant les erreurs si l'index existe déjà
     */
    private static void createIndex(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            // Index may already exist - log the error but continue
            System.out.println("Note: Index creation skipped (may already exist): " + e.getMessage());
        }
    }

    /**
     * Crée les procédures stockées pour la base de données
     */
    private static void createStoredProcedures(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();

        // Use individual try-catch blocks for each procedure to isolate errors
        createProcedureWithErrorHandling(conn, "DROP PROCEDURE IF EXISTS creer_compte");
        createProcedureWithErrorHandling(conn, "DROP PROCEDURE IF EXISTS ajouter_conducteur");
        createProcedureWithErrorHandling(conn, "DROP PROCEDURE IF EXISTS ajouter_passager");
        createProcedureWithErrorHandling(conn, "DROP PROCEDURE IF EXISTS rechercher_trajets");
        createProcedureWithErrorHandling(conn, "DROP PROCEDURE IF EXISTS creer_reservation");

        // Create stored procedure: creer_compte
       try{ String createProcedure = "CREATE PROCEDURE creer_compte(" +
                "    IN p_nom VARCHAR(100), " +
                "    IN p_prenom VARCHAR(100), " +
                "    IN p_email VARCHAR(150), " +
                "    IN p_motDePasse VARCHAR(255), " +
                "    IN p_telephone VARCHAR(20) " +
                ") " +
                "BEGIN " +
                "    INSERT INTO UtilisateurInscrit(nom, prenom, email, motDePasse, telephone) " +
                "    VALUES (p_nom, p_prenom, p_email, p_motDePasse, p_telephone); " +
                "END";
        createProcedureWithErrorHandling(conn, createProcedure);

        // Create stored procedure: ajouter_conducteur
        createProcedure = "CREATE PROCEDURE ajouter_conducteur(" +
                "    IN p_utilisateurId INT," +
                "    IN p_permisConduire VARCHAR(50)," +
                "    IN p_vehicule VARCHAR(100)" +
                ")" +
                "BEGIN" +
                "    UPDATE UtilisateurInscrit SET type_utilisateur = 'conducteur' WHERE id = p_utilisateurId;" +
                "    " +
                "    INSERT INTO Conducteur(id, permisConduire, vehicule)" +
                "    VALUES (p_utilisateurId, p_permisConduire, p_vehicule);" +
                "END";
        createProcedureWithErrorHandling(conn, createProcedure);

        // Create stored procedure: ajouter_passager
        createProcedure = "CREATE PROCEDURE ajouter_passager(" +
                "    IN p_utilisateurId INT," +
                "    IN p_methodePaiement VARCHAR(50)," +
                "    IN p_preferences TEXT" +
                ")" +
                "BEGIN" +
                "    UPDATE UtilisateurInscrit SET type_utilisateur = 'passager' WHERE id = p_utilisateurId;" +
                "    " +
                "    INSERT INTO Passager(id, methodePaiement, preferences)" +
                "    VALUES (p_utilisateurId, p_methodePaiement, p_preferences);" +
                "END";
        createProcedureWithErrorHandling(conn, createProcedure);

        // Create stored procedure: rechercher_trajets
        createProcedure = "CREATE PROCEDURE rechercher_trajets(" +
                "    IN p_lieuDepart VARCHAR(255)," +
                "    IN p_lieuArrivee VARCHAR(255)," +
                "    IN p_dateDepart DATE" +
                ")" +
                "BEGIN" +
                "    SELECT t.*, c.note AS note_conducteur, c.vehicule" +
                "    FROM Trajet t" +
                "    JOIN Conducteur c ON t.conducteurId = c.id" +
                "    WHERE t.lieuDepart LIKE CONCAT('%', p_lieuDepart, '%')" +
                "    AND t.lieuArrivee LIKE CONCAT('%', p_lieuArrivee, '%')" +
                "    AND DATE(t.dateDepart) = p_dateDepart" +
                "    AND t.nbPlacesDisponibles > 0" +
                "    AND t.statut = 'planifié'" +
                "    ORDER BY t.prix ASC;" +
                "END";
        createProcedureWithErrorHandling(conn, createProcedure);

        // Create stored procedure: creer_reservation
        createProcedure = "CREATE PROCEDURE creer_reservation(" +
                "    IN p_passagerId INT," +
                "    IN p_trajetId INT," +
                "    IN p_nombrePlaces INT" +
                ")" +
                "BEGIN" +
                "    DECLARE v_prix DECIMAL(10, 2);" +
                "    DECLARE v_places_disponibles INT;" +
                "    SELECT prix, nbPlacesDisponibles INTO v_prix, v_places_disponibles" +
                "    FROM Trajet" +
                "    WHERE id = p_trajetId;" +
                "    IF v_places_disponibles >= p_nombrePlaces THEN" +
                "        INSERT INTO Reservation(passagerId, trajetId, nombrePlaces, prixTotal)" +
                "        VALUES (p_passagerId, p_trajetId, p_nombrePlaces, v_prix * p_nombrePlaces);" +
                "        UPDATE Trajet" +
                "        SET nbPlacesDisponibles = nbPlacesDisponibles - p_nombrePlaces" +
                "        WHERE id = p_trajetId;" +
                "        INSERT INTO Notification(contenu, utilisateurId)" +
                "        SELECT CONCAT('Nouvelle réservation pour votre trajet de ', lieuDepart, ' à ', lieuArrivee, ' le ', DATE_FORMAT(dateDepart, '%d/%m/%Y')), conducteurId" +
                "        FROM Trajet" +
                "        WHERE id = p_trajetId;" +
                "    END IF;" +
                "END";
        createProcedureWithErrorHandling(conn, createProcedure);

        System.out.println("Stored procedures created successfully");
    } catch (Exception e) {
        System.err.println("Error creating stored procedures: " + e.getMessage());
        System.out.println("Continuing without stored procedures");
    }
    }
    private static void createProcedureWithErrorHandling(Connection conn, String sql) {
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("SQL statement execution warning: " + e.getMessage());
            // Continue execution - don't throw the exception
        }
    }

    /**
     * Crée les triggers pour la base de données
     */
    private static void createTriggers(Connection conn) throws SQLException {
        try {
            Statement stmt = conn.createStatement();

            // Drop existing triggers if they exist
            createProcedureWithErrorHandling(conn, "DROP TRIGGER IF EXISTS before_reservation_insert");
            createProcedureWithErrorHandling(conn, "DROP TRIGGER IF EXISTS after_avis_insert");

            // Create trigger: before_reservation_insert
            String createTrigger = "CREATE TRIGGER before_reservation_insert " +
                    "BEFORE INSERT ON Reservation " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "    DECLARE places_dispo INT; " +
                    "    SELECT nbPlacesDisponibles INTO places_dispo " +
                    "    FROM Trajet " +
                    "    WHERE id = NEW.trajetId; " +
                    "    IF places_dispo < NEW.nombrePlaces THEN " +
                    "        SIGNAL SQLSTATE '45000' " +
                    "        SET MESSAGE_TEXT = 'Pas assez de places disponibles pour ce trajet'; " +
                    "    END IF; " +
                    "END";
            createProcedureWithErrorHandling(conn, createTrigger);

            // Create trigger: after_avis_insert
            createTrigger = "CREATE TRIGGER after_avis_insert " +
                    "AFTER INSERT ON Avis " +
                    "FOR EACH ROW " +
                    "BEGIN " +
                    "    DECLARE v_conducteur_id INT; " +
                    "    SELECT t.conducteurId INTO v_conducteur_id " +
                    "    FROM Reservation r " +
                    "    JOIN Trajet t ON r.trajetId = t.id " +
                    "    WHERE r.id = NEW.reservationId; " +
                    "    UPDATE Conducteur c " +
                    "    SET c.note = ( " +
                    "        SELECT AVG(a.note) " +
                    "        FROM Avis a " +
                    "        JOIN Reservation r ON a.reservationId = r.id " +
                    "        JOIN Trajet t ON r.trajetId = t.id " +
                    "        WHERE t.conducteurId = v_conducteur_id) " +
                    "    WHERE c.id = v_conducteur_id; " +
                    "END";
            createProcedureWithErrorHandling(conn, createTrigger);

            System.out.println("Triggers created successfully");
        } catch (SQLException e) {
            System.err.println("Error creating triggers: " + e.getMessage());
            System.out.println("Continuing without triggers");
        }
    }

    public static void main(String[] args) {
        try {
            creatDB();
            System.out.println("Database creation completed successfully!");
        } catch (Exception e) {
            System.err.println("Error during database creation:");
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }
}