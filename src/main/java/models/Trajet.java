package models;
import java.util.Date;
public class Trajet {
    private int id;
    private String lieuDepart;
    private String lieuArrivee;
    private Date dateDepart;
    private int nbPlacesDisponibles;
    private double prix;
    private String statut;  // "planifié", "en_cours", "terminé", "annulé"
    private int conducteurId;
    private int apiCartographieId;

    // Constructeur
    public Trajet() {
    }

    public Trajet(String lieuDepart, String lieuArrivee, Date dateDepart,
                  int nbPlacesDisponibles, double prix, int conducteurId) {
        this.lieuDepart = lieuDepart;
        this.lieuArrivee = lieuArrivee;
        this.dateDepart = dateDepart;
        this.nbPlacesDisponibles = nbPlacesDisponibles;
        this.prix = prix;
        this.statut = "planifié";
        this.conducteurId = conducteurId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLieuDepart() {
        return lieuDepart;
    }

    public void setLieuDepart(String lieuDepart) {
        this.lieuDepart = lieuDepart;
    }

    public String getLieuArrivee() {
        return lieuArrivee;
    }

    public void setLieuArrivee(String lieuArrivee) {
        this.lieuArrivee = lieuArrivee;
    }

    public Date getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    public int getNbPlacesDisponibles() {
        return nbPlacesDisponibles;
    }

    public void setNbPlacesDisponibles(int nbPlacesDisponibles) {
        this.nbPlacesDisponibles = nbPlacesDisponibles;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getConducteurId() {
        return conducteurId;
    }

    public void setConducteurId(int conducteurId) {
        this.conducteurId = conducteurId;
    }

    public int getApiCartographieId() {
        return apiCartographieId;
    }

    public void setApiCartographieId(int apiCartographieId) {
        this.apiCartographieId = apiCartographieId;
    }

    @Override
    public String toString() {
        return lieuDepart + " → " + lieuArrivee + " le " + dateDepart;
    }
}
