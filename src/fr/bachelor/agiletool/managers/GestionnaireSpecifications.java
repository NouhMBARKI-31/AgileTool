package fr.bachelor.agiletool.managers;

import fr.bachelor.agiletool.models.Specification;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GestionnaireSpecifications {
    private static final String FICHIER = "specifications.csv";
    private List<Specification> specifications;
    private int prochainId;

    public GestionnaireSpecifications() {
        specifications = new ArrayList<>();
        prochainId = 1;
        // Dès qu'on crée le gestionnaire, on charge les données existantes
        charger();
    }

    public void ajouter(String description) {
        // On utilise l'ID actuel puis on l'incrémente pour le suivant (++)
        Specification s = new Specification(prochainId++, description);
        specifications.add(s);

        // On sauvegarde immédiatement pour ne pas perdre de données en cas de crash
        sauvegarder();
        System.out.println("✓ Spécification ajoutée avec l'ID : " + s.getId());
    }

    // Utilisation des Streams Java pour chercher proprement un ID
    public Optional<Specification> trouverParId(int id) {
        return specifications.stream().filter(s -> s.getId() == id).findFirst();
    }

    public void afficherTout() {
        if (specifications.isEmpty()) {
            System.out.println("\n⚠ Aucune spécification enregistrée.");
            return;
        }
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ LISTE DES SPÉCIFICATIONS                                             ║");
        System.out.println("╠══════╦════════════════════════════════╦═════════════════╦════════════╣");
        System.out.println("║ ID   ║ Description                    ║ État            ║ Prog.      ║");
        System.out.println("╠══════╬════════════════════════════════╬═════════════════╬════════════╣");
        for (Specification s : specifications) {
            System.out.println("║ " + s + " ║");
        }
        System.out.println("╚══════╩════════════════════════════════╩═════════════════╩════════════╝");
    }

    // Écrit toute la liste dans le fichier CSV (écrase l'ancien contenu)
    public void sauvegarder() {
        // Le try(...) ferme automatiquement le fichier à la fin, c'est plus sûr
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHIER))) {
            for (Specification s : specifications) {
                writer.println(s.toCSV());
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur sauvegarde spécifications : " + e.getMessage());
        }
    }

    private void charger() {
        File file = new File(FICHIER);
        // Si le fichier n'existe pas (premier lancement), on ne fait rien
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Specification s = Specification.fromCSV(line);
                    if (s != null) {
                        specifications.add(s);
                        // Important : On met à jour le compteur d'ID pour ne pas réutiliser un ID existant
                        if (s.getId() >= prochainId) {
                            prochainId = s.getId() + 1;
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur chargement spécifications : " + e.getMessage());
        }
    }
}