package fr.bachelor.agiletool.ui;

import fr.bachelor.agiletool.managers.GestionnaireContraintes;
import fr.bachelor.agiletool.managers.GestionnaireRapports;
import fr.bachelor.agiletool.managers.GestionnaireSpecifications;
import java.util.Scanner;

public final class MenuPrincipal {
    private final Scanner scanner;
    // On déclare les gestionnaires ici pour qu'ils soient accessibles partout
    // et qu'ils gardent les données en mémoire tant que l'appli tourne.
    private final GestionnaireSpecifications gestSpec;
    private final GestionnaireContraintes gestContraintes;
    private final GestionnaireRapports gestRapports;

    public MenuPrincipal() {
        // Initialisation de tous les outils au démarrage
        this.scanner = new Scanner(System.in);
        this.gestSpec = new GestionnaireSpecifications();
        this.gestContraintes = new GestionnaireContraintes();
        this.gestRapports = new GestionnaireRapports();
    }

    public void afficher() {
        // Boucle infinie : le programme ne s'arrête que si l'utilisateur choisit "0"
        while (true) {
            System.out.println("\n╔══════════════════════════════════╗");
            System.out.println("║       AGILE TOOL - MENU          ║");
            System.out.println("╠══════════════════════════════════╣");
            System.out.println("║ 1. Gérer les Spécifications      ║");
            System.out.println("║ 2. Gérer les Contraintes         ║");
            System.out.println("║ 3. Rapports de Réunion           ║");
            System.out.println("║ 0. Quitter                       ║");
            System.out.println("╚══════════════════════════════════╝");
            System.out.print("> Votre choix : ");

            String choix = scanner.nextLine().trim();

            switch (choix) {
                // On passe le scanner et le gestionnaire correspondant au sous-menu
                case "1" -> new MenuSpecifications(scanner, gestSpec).afficher();
                case "2" -> new MenuContraintes(gestContraintes, scanner).afficher();
                case "3" -> new MenuRapports(gestRapports, scanner).afficher();
                case "0" -> {
                    System.out.println("Au revoir !");
                    // Le return sort de la méthode afficher() et donc termine le main()
                    return;
                }
                default -> System.out.println("✗ Choix invalide.");
            }
        }
    }
}