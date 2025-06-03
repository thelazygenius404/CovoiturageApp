package models;
import java.util.Date;
public class Reservation {
    private int id;
    private Date dateReservation;
    private String statut;  // "en_attente", "confirmée", "annulée"
    private int nombrePlaces;
    private double prixTotal;
    private int passagerId;
    private int trajetId;

    // Constructeur
    public Reservation() {
        this.dateReservation = new Date();
    }

    public Reservation(int nombrePlaces, double prixTotal, int passagerId, int trajetId) {
        this.dateReservation = new Date();
        this.statut = "en_attente";
        this.nombrePlaces = nombrePlaces;
        this.prixTotal = prixTotal;
        this.passagerId = passagerId;
        this.trajetId = trajetId;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public int getNombrePlaces() {
        return nombrePlaces;
    }

    public void setNombrePlaces(int nombrePlaces) {
        this.nombrePlaces = nombrePlaces;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getPassagerId() {
        return passagerId;
    }

    public void setPassagerId(int passagerId) {
        this.passagerId = passagerId;
    }

    public int getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(int trajetId) {
        this.trajetId = trajetId;
    }
}
