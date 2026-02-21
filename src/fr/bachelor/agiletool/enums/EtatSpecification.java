package fr.bachelor.agiletool.enums;

public enum EtatSpecification {
    A_ANALYSER("À analyser"),
    ANALYSE("Analysé"),      // J'ai mis les accents pour l'affichage
    ANNULE("Annulé"),        // Idem
    TERMINE("Terminé");      // Idem

    private final String libelle;

    EtatSpecification(String libelle) {
        this.libelle = libelle;
    }

    // Permet de récupérer le texte pour l'affichage (ex: .getLibelle())
    // Au lieu du nom technique (ex: .name())
    public String getLibelle() {
        return libelle;
    }
}