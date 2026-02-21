package fr.bachelor.agiletool.ui;

import fr.bachelor.agiletool.enums.EtatSpecification;
import fr.bachelor.agiletool.managers.GestionnaireSpecifications;
import fr.bachelor.agiletool.models.Specification;
import java.util.Optional;
import java.util.Scanner;

public class MenuSpecifications {
    private final Scanner scanner;
    private final GestionnaireSpecifications gestionnaire;

    public MenuSpecifications(Scanner scanner, GestionnaireSpecifications gestionnaire) {
        this.scanner = scanner;
        this.gestionnaire = gestionnaire;
    }

    public void afficher() {
        boolean retour = false;
        while (!retour) {
            System.out.println("\n--- MENU SPÉCIFICATIONS ---");
            System.out.println("1. Ajouter un besoin");
            System.out.println("2. Lister les besoins");
            System.out.println("3. Modifier / Changer état");
            System.out.println("0. Retour");
            System.out.print("> ");

            String choix = scanner.nextLine().trim();
            switch (choix) {
                case "1" -> ajouter();
                case "2" -> gestionnaire.afficherTout();
                case "3" -> modifier();
                case "0" -> retour = true;
                default -> System.out.println("Choix invalide.");
            }
        }
    }

    private void ajouter() {
        System.out.print("Description du besoin : ");
        String desc = scanner.nextLine();
        gestionnaire.ajouter(desc);
    }

    private void modifier() {
        System.out.print("ID du besoin à modifier : ");
        try {
            int id = Integer.parseInt(scanner.nextLine());
            // On utilise Optional pour gérer proprement le cas où l'ID n'existe pas
            Optional<Specification> optSpec = gestionnaire.trouverParId(id);

            if (optSpec.isEmpty()) {
                System.out.println("✗ ID introuvable.");
                return;
            }

            Specification s = optSpec.get();
            System.out.println("État actuel : " + s.getEtat().getLibelle());
            System.out.println("Nouvel état ?");
            System.out.println("1. À analyser (Date prévue)");
            System.out.println("2. Analysé (Charge, Dates, Qui)");
            System.out.println("3. Annulé (Raison)");
            System.out.println("4. Terminé / En cours (% progression)");
            System.out.print("> ");

            String choix = scanner.nextLine().trim();

            // Logique conditionnelle : on demande des infos différentes selon le choix
            switch (choix) {
                case "1" -> {
                    s.setEtat(EtatSpecification.A_ANALYSER);
                    System.out.print("Date prévue de l'analyse (jj/mm/aaaa) : ");
                    s.setDatePrevueAnalyse(scanner.nextLine());
                }
                case "2" -> {
                    s.setEtat(EtatSpecification.ANALYSE);
                    System.out.print("Charge (jours/hommes) : ");
                    s.setCharge(Integer.parseInt(scanner.nextLine()));
                    System.out.print("Date début : ");
                    s.setDateDebut(scanner.nextLine());
                    System.out.print("Date fin : ");
                    s.setDateFin(scanner.nextLine());
                    System.out.print("Qui (Responsable) : ");
                    s.setResponsable(scanner.nextLine());
                }
                case "3" -> {
                    s.setEtat(EtatSpecification.ANNULE);
                    System.out.print("Raison de l'annulation : ");
                    s.setRaison(scanner.nextLine());
                }
                case "4" -> {
                    s.setEtat(EtatSpecification.TERMINE);
                    System.out.print("Taux de progression (0-100) : ");
                    try {
                        int taux = Integer.parseInt(scanner.nextLine());
                        s.setTauxProgression(taux);
                    } catch (NumberFormatException e) {
                        System.out.println("Valeur invalide, mis à 0.");
                        s.setTauxProgression(0);
                    }
                }
                default -> System.out.println("Annulation de la modification.");
            }
            // Sauvegarde immédiate après modification
            gestionnaire.sauvegarder();
            System.out.println("✓ Spécification mise à jour.");

        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez entrer un nombre valide.");
        }
    }
}