package fr.bachelor.agiletool.models;

import fr.bachelor.agiletool.enums.EtatContrainte;

public class Contrainte {
    private int id;
    private String description;
    private EtatContrainte etat;
    private String verificateurNom;
    private String dateVerification;
    private String raison;

    public Contrainte(int id, String description) {
        this.id = id;
        this.description = description;
        this.etat = EtatContrainte.A_PRENDRE_EN_COMPTE;
        this.verificateurNom = "";
        this.dateVerification = "";
        this.raison = "";
    }

    // Getters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public EtatContrainte getEtat() { return etat; }
    public String getVerificateurNom() { return verificateurNom; }
    public String getDateVerification() { return dateVerification; }
    public String getRaison() { return raison; }

    // Setters
    public void setEtat(EtatContrainte etat) { this.etat = etat; }
    public void setVerificateurNom(String nom) { this.verificateurNom = nom; }
    public void setDateVerification(String date) { this.dateVerification = date; }
    public void setRaison(String raison) { this.raison = raison; }

    // Conversion en texte pour le fichier csv
    public String toCSV() {
        return id + ";" + description + ";" + etat.name() + ";" +
                verificateurNom + ";" + dateVerification + ";" + raison;
    }

    public static Contrainte fromCSV(String line) {
        String[] parts = line.split(";");
        Contrainte contrainte = new Contrainte(Integer.parseInt(parts[0]), parts[1]);
        contrainte.setEtat(EtatContrainte.valueOf(parts[2]));
        if (parts.length > 3) contrainte.setVerificateurNom(parts[3]);
        if (parts.length > 4) contrainte.setDateVerification(parts[4]);
        if (parts.length > 5) contrainte.setRaison(parts[5]);
        return contrainte;
    }

    @Override
    public String toString() {
        String desc = description.length() > 40 ? description.substring(0, 37) + "..." : description;
        return String.format("%-4d | %-40s | %-30s", id, desc, etat.getLibelle());
    }
}