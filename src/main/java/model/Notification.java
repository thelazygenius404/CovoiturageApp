package model;
import java.time.LocalDateTime;

public class Notification {
    private int id;
    private LocalDateTime date;
    private String contenu;
    private String statut; // non_lue, lue
    private int utilisateurId;
    private UtilisateurInscrit utilisateur;

    // Constructeurs
    public Notification() {}

    public Notification(String contenu, int utilisateurId) {
        this.contenu = contenu;
        this.utilisateurId = utilisateurId;
        this.date = LocalDateTime.now();
        this.statut = "non_lue";
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public int getUtilisateurId() { return utilisateurId; }
    public void setUtilisateurId(int utilisateurId) { this.utilisateurId = utilisateurId; }

    public UtilisateurInscrit getUtilisateur() { return utilisateur; }
    public void setUtilisateur(UtilisateurInscrit utilisateur) { this.utilisateur = utilisateur; }
}
