package fr.bachelor.agiletool.enums;

// Enumération : permet de définir une liste fixe d'états possibles.
// Cela évite les fautes de frappe (on ne peut pas écrire "Validée" au lieu de "Vérifiée").
public enum EtatContrainte {
    A_PRENDRE_EN_COMPTE("À prendre en compte"),
    PRISE_EN_COMPTE_A_VERIFIER("Prise en compte à vérifier"),
    VERIFIEE("Vérifiée"),
    ANNULEE("Annulée");

    // Le libellé sert à afficher un texte propre (avec accents et espaces) à l'écran
    private String libelle;

    EtatContrainte(String libelle) {
        this.libelle = libelle;
    }

    public String getLibelle() {
        return libelle;
    }
}