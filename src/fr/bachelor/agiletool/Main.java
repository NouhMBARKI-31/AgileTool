package fr.bachelor.agiletool;

import fr.bachelor.agiletool.ui.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        // Point d'entrée unique de l'application
        // Lance le menu principal qui gère l'initialisation des managers
        new MenuPrincipal().afficher();
    }
}