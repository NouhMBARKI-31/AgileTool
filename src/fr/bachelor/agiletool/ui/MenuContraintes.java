package fr.bachelor.agiletool.ui;

import fr.bachelor.agiletool.managers.GestionnaireContraintes;
import fr.bachelor.agiletool.models.Contrainte;
import fr.bachelor.agiletool.enums.EtatContrainte;

import java.util.Scanner;

public class MenuContraintes {
    private GestionnaireContraintes gestionnaire;
    private Scanner scanner;

    public MenuContraintes(GestionnaireContraintes gestionnaire, Scanner scanner) {
        this.gestionnaire = gestionnaire;
        this.scanner = scanner;
    }

    public void afficher() {
        boolean retour = false;
        // Tant que l'utilisateur ne veut pas revenir en arrière, on reste dans ce menu
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                         GESTION DES CONTRAINTES                            ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Ajouter une contrainte                                                 ║");
            System.out.println("║  2. Lister toutes les contraintes                                          ║");
            System.out.println("║  3. Modifier l'état d'une contrainte                                       ║");
            System.out.println("║  0. Retour au menu principal                                               ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    ajouterContrainte();
                    break;
                case 2:
                    gestionnaire.afficherTout();
                    break;
                case 3:
                    modifierEtatContrainte();
                    break;
                case 0:
                    retour = true; // Permet de sortir de la boucle while
                    break;
                default:
                    System.out.println("✗ Choix invalide.");
            }
        }
    }

    private void ajouterContrainte() {
        System.out.print("\nDescription de la contrainte : ");
        scanner.nextLine(); // Astuce indispensable : consomme le "Entrée" qui traîne dans le tampon
        String description = scanner.nextLine();
        gestionnaire.ajouter(description);
    }

    private void modifierEtatContrainte() {
        System.out.print("\nID de la contrainte : ");
        int id = lireEntier();
        Contrainte contrainte = gestionnaire.trouverParId(id);

        if (contrainte == null) {
            System.out.println("✗ Contrainte non trouvée.");
            return;
        }

        System.out.println("\nÉtats disponibles :");
        System.out.println("1. À prendre en compte");
        System.out.println("2. Prise en compte à vérifier");
        System.out.println("3. Vérifiée");
        System.out.println("4. Annulée");
        System.out.print("Choisir le nouvel état : ");
        int choix = lireEntier();

        scanner.nextLine(); // Nettoyage du tampon avant de lire du texte

        // Selon l'état choisi, on demande des infos supplémentaires
        switch (choix) {
            case 1:
                contrainte.setEtat(EtatContrainte.A_PRENDRE_EN_COMPTE);
                break;
            case 2:
                contrainte.setEtat(EtatContrainte.PRISE_EN_COMPTE_A_VERIFIER);
                break;
            case 3:
                contrainte.setEtat(EtatContrainte.VERIFIEE);
                System.out.print("Vérifiée par (nom) : ");
                contrainte.setVerificateurNom(scanner.nextLine());
                System.out.print("Date de vérification (jj/mm/aaaa) : ");
                contrainte.setDateVerification(scanner.nextLine());
                break;
            case 4:
                contrainte.setEtat(EtatContrainte.ANNULEE);
                System.out.print("Raison de l'annulation : ");
                contrainte.setRaison(scanner.nextLine());
                break;
            default:
                System.out.println("✗ Choix invalide.");
                return;
        }

        // On n'oublie pas de sauvegarder les changements dans le fichier
        gestionnaire.sauvegarder();
        System.out.println("✓ État mis à jour avec succès.");
    }

    // Petite méthode utilitaire pour éviter le crash si l'utilisateur tape des lettres
    private int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next(); // On passe l'entrée invalide
        }
        return scanner.nextInt();
    }
}