package model;

public class Passager extends UtilisateurInscrit{
    private String methodePaiement;
    private String preferences;

    // Constructeur
    public Passager() {
        super();
    }

    public Passager(String nom, String prenom, String email, String motDePasse,
                    String telephone, String methodePaiement, String preferences) {
        super(nom, prenom, email, motDePasse, telephone);
        this.methodePaiement = methodePaiement;
        this.preferences = preferences;
        this.setTypeUtilisateur("passager");
    }

    // Getters et Setters
    public String getMethodePaiement() {
        return methodePaiement;
    }

    public void setMethodePaiement(String methodePaiement) {
        this.methodePaiement = methodePaiement;
    }

    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }
}
