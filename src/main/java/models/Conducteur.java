package models;

public class Conducteur extends UtilisateurInscrit{
    private String permisConduire;
    private String vehicule;
    private float note;

    // Constructeur
    public Conducteur() {
        super();
        this.note = 0;
    }

    public Conducteur(String nom, String prenom, String email, String motDePasse,
                      String telephone, String permisConduire, String vehicule) {
        super(nom, prenom, email, motDePasse, telephone);
        this.permisConduire = permisConduire;
        this.vehicule = vehicule;
        this.note = 0;
        this.setTypeUtilisateur("conducteur");
    }

    // Getters et Setters
    public String getPermisConduire() {
        return permisConduire;
    }

    public void setPermisConduire(String permisConduire) {
        this.permisConduire = permisConduire;
    }

    public String getVehicule() {
        return vehicule;
    }

    public void setVehicule(String vehicule) {
        this.vehicule = vehicule;
    }

    public float getNote() {
        return note;
    }

    public void setNote(float note) {
        this.note = note;
    }
}
