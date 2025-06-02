package model;
import java.time.LocalDateTime;

public class Avis {
    private int id;
    private float note;
    private String commentaire;
    private LocalDateTime date;
    private int reservationId;
    private Reservation reservation;

    // Constructeurs
    public Avis() {}

    public Avis(float note, String commentaire, int reservationId) {
        this.note = note;
        this.commentaire = commentaire;
        this.reservationId = reservationId;
        this.date = LocalDateTime.now();
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public float getNote() { return note; }
    public void setNote(float note) { this.note = note; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }

    public int getReservationId() { return reservationId; }
    public void setReservationId(int reservationId) { this.reservationId = reservationId; }

    public Reservation getReservation() { return reservation; }
    public void setReservation(Reservation reservation) { this.reservation = reservation; }
}