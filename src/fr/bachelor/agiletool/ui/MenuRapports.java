package fr.bachelor.agiletool.ui;

import fr.bachelor.agiletool.managers.GestionnaireRapports;
import fr.bachelor.agiletool.models.RapportReunion;

import java.util.Scanner;

public class MenuRapports {
    private GestionnaireRapports gestionnaire;
    private Scanner scanner;

    public MenuRapports(GestionnaireRapports gestionnaire, Scanner scanner) {
        this.gestionnaire = gestionnaire;
        this.scanner = scanner;
    }

    public void afficher() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                        RAPPORTS DE RÉUNION                                 ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
            System.out.println("║  1. Créer un nouveau rapport                                               ║");
            System.out.println("║  2. Lister tous les rapports                                               ║");
            System.out.println("║  0. Retour au menu principal                                               ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
            System.out.print("Votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerRapport();
                    break;
                case 2:
                    gestionnaire.afficherListeRapports();
                    break;
                case 0:
                    retour = true;
                    break;
                default:
                    System.out.println("✗ Choix invalide.");
            }
        }
    }

    private void creerRapport() {
        scanner.nextLine(); // On vide le buffer avant de commencer les questions
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                     CRÉATION D'UN RAPPORT DE RÉUNION                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");

        System.out.print("\nTitre de la réunion : ");
        String titre = scanner.nextLine();

        System.out.print("Nom de l'auteur : ");
        String auteur = scanner.nextLine();

        System.out.print("Participants (séparés par des virgules) : ");
        String participants = scanner.nextLine();

        System.out.print("Date de la réunion (jj/mm/aaaa) : ");
        String date = scanner.nextLine();

        // On crée l'objet rapport en mémoire
        RapportReunion rapport = new RapportReunion(titre, auteur, participants, date);

        System.out.println("\n--- Ajout des actions (Quoi, Qui, Quand) ---");
        System.out.println("Tapez 'fin' dans le champ 'Quoi' pour terminer.");

        // Boucle pour ajouter autant d'actions que nécessaire
        while (true) {
            System.out.print("\nQuoi (action à réaliser) : ");
            String quoi = scanner.nextLine();

            // Condition de sortie de la boucle
            if (quoi.equalsIgnoreCase("fin")) {
                break;
            }

            System.out.print("Qui (responsable) : ");
            String qui = scanner.nextLine();

            System.out.print("Quand (échéance) : ");
            String quand = scanner.nextLine();

            rapport.addAction(quoi, qui, quand);
            System.out.println("✓ Action ajoutée.");
        }

        // On affiche un récapitulatif avant de sauvegarder
        rapport.displayTable();
        gestionnaire.sauvegarderRapport(rapport);
    }

    private int lireEntier() {
        while (!scanner.hasNextInt()) {
            System.out.print("Veuillez entrer un nombre valide : ");
            scanner.next();
        }
        return scanner.nextInt();
    }
}