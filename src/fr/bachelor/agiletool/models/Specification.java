package fr.bachelor.agiletool.models;

import fr.bachelor.agiletool.enums.EtatSpecification;

public class Specification {
    private final int id;
    private String description;
    private EtatSpecification etat;

    // Ces champs dépendent de l'état (ex: on n'a pas de "tauxProgression" au début)
    private String datePrevueAnalyse; // Pour "À analyser"
    private int charge;               // Pour "Analysé"
    private String dateDebut;         // Pour "Analysé"
    private String dateFin;           // Pour "Analysé"
    private String responsable;       // Pour "Analysé"
    private String raison;            // Pour "Annulé"
    private int tauxProgression;      // Pour "Terminé" ou en cours

    public Specification(int id, String description) {
        this.id = id;
        this.description = description;
        this.etat = EtatSpecification.A_ANALYSER;
        this.tauxProgression = 0;

        // J'initialise les chaînes vides pour éviter les erreurs "NullPointerException" plus tard
        this.datePrevueAnalyse = "";
        this.dateDebut = "";
        this.dateFin = "";
        this.responsable = "";
        this.raison = "";
    }

    // Getters et Setters
    public int getId() { return id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public EtatSpecification getEtat() { return etat; }
    public void setEtat(EtatSpecification etat) { this.etat = etat; }

    public String getDatePrevueAnalyse() { return datePrevueAnalyse; }
    public void setDatePrevueAnalyse(String datePrevueAnalyse) { this.datePrevueAnalyse = datePrevueAnalyse; }

    public int getCharge() { return charge; }
    public void setCharge(int charge) { this.charge = charge; }

    public String getDateDebut() { return dateDebut; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }

    public String getDateFin() { return dateFin; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }

    public String getResponsable() { return responsable; }
    public void setResponsable(String responsable) { this.responsable = responsable; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }

    public int getTauxProgression() { return tauxProgression; }
    public void setTauxProgression(int tauxProgression) {
        if (tauxProgression < 0) this.tauxProgression = 0;
        else if (tauxProgression > 100) this.tauxProgression = 100;
        else this.tauxProgression = tauxProgression;
    }

    // Gestion CSV
    public String toCSV() {
        return String.join(";",
                String.valueOf(id),
                description,
                etat.name(),
                datePrevueAnalyse,
                String.valueOf(charge),
                dateDebut,
                dateFin,
                responsable,
                raison,
                String.valueOf(tauxProgression)
        );
    }

    public static Specification fromCSV(String line) {
        String[] parts = line.split(";");
        // On s'attend à au moins 10 colonnes (même vides)
        if (parts.length < 3) return null;

        Specification s = new Specification(Integer.parseInt(parts[0]), parts[1]);
        s.setEtat(EtatSpecification.valueOf(parts[2]));

        // Récupération des champs optionnels (avec sécurité sur l'index)
        if (parts.length > 3) s.setDatePrevueAnalyse(parts[3]);
        if (parts.length > 4) s.setCharge(parts[4].isEmpty() || parts[4].equals("null") ? 0 : Integer.parseInt(parts[4]));
        if (parts.length > 5) s.setDateDebut(parts[5]);
        if (parts.length > 6) s.setDateFin(parts[6]);
        if (parts.length > 7) s.setResponsable(parts[7]);
        if (parts.length > 8) s.setRaison(parts[8]);
        if (parts.length > 9) s.setTauxProgression(parts[9].isEmpty() || parts[9].equals("null") ? 0 : Integer.parseInt(parts[9]));

        return s;
    }

    @Override
    public String toString() {
        String descCourte = description.length() > 30 ? description.substring(0, 27) + "..." : description;
        return String.format("%-4d | %-30s | %-15s | %3d%%", id, descCourte, etat.getLibelle(), tauxProgression);
    }
}