package controllers;
import daos.ConducteurDAO;
import models.Conducteur;
import java.util.List;

public class ConducteurController {
    private final ConducteurDAO conducteurDAO;

    public ConducteurController() {
        this.conducteurDAO = new ConducteurDAO();
    }

    public boolean ajouterConducteur(Conducteur c) {
        if (c.getId()<= 0) {
            throw new IllegalArgumentException("ID utilisateur invalide");
        }
        if (c.getPermisConduire() == null || c.getPermisConduire().trim().isEmpty()) {
            throw new IllegalArgumentException("Le numéro de permis de conduire est obligatoire");
        }
        if (c.getVehicule() == null || c.getVehicule().trim().isEmpty()) {
            throw new IllegalArgumentException("Les informations du véhicule sont obligatoires");
        }
        return conducteurDAO.ajouterConducteur(c);
    }

    public Conducteur obtenirConducteur(int id) {
        return conducteurDAO.trouverParId(id);
    }

    public List<Conducteur> obtenirTousLesConducteurs() {
        return conducteurDAO.listerTous();
    }

    public boolean mettreAJourConducteur(Conducteur conducteur) {
        if (conducteur == null || conducteur.getId() <= 0) {
            return false;
        }
        return conducteurDAO.mettreAJourConducteur(conducteur);
    }

    public boolean supprimerConducteur(int id) {
        if (id <= 0) {
            return false;
        }
        return conducteurDAO.supprimerConducteur(id);
    }
}