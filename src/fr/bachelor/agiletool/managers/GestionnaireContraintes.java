package fr.bachelor.agiletool.managers;

import fr.bachelor.agiletool.models.Contrainte;
import fr.bachelor.agiletool.enums.EtatContrainte;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireContraintes {
    private static final String FICHIER = "contraintes.csv";
    private List<Contrainte> contraintes;
    private int prochainId;

    public GestionnaireContraintes() {
        contraintes = new ArrayList<>();
        prochainId = 1;
        charger();
    }

    public void ajouter(String description) {
        Contrainte contrainte = new Contrainte(prochainId++, description);
        contraintes.add(contrainte);
        sauvegarder();
        System.out.println("✓ Contrainte ajoutée avec l'ID: " + contrainte.getId());
    }

    // Recherche classique avec une boucle (alternative aux Streams)
    public Contrainte trouverParId(int id) {
        for (Contrainte c : contraintes) {
            if (c.getId() == id) return c;
        }
        return null;
    }

    public void afficherTout() {
        if (contraintes.isEmpty()) {
            System.out.println("\n⚠ Aucune contrainte enregistrée.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                            LISTE DES CONTRAINTES                           ║");
        System.out.println("╠════╦══════════════════════════════════════════╦════════════════════════════╣");
        System.out.println("║ ID ║              Description                 ║            État            ║");
        System.out.println("╠════╬══════════════════════════════════════════╬════════════════════════════╣");

        for (Contrainte c : contraintes) {
            System.out.println("║ " + c + " ║");
        }

        System.out.println("╚════╩══════════════════════════════════════════╩════════════════════════════╝");
    }

    public void sauvegarder() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FICHIER))) {
            for (Contrainte c : contraintes) {
                writer.println(c.toCSV());
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde: " + e.getMessage());
        }
    }

    public void charger() {
        File file = new File(FICHIER);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FICHIER))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    Contrainte c = Contrainte.fromCSV(line);
                    contraintes.add(c);
                    // On s'assure que le prochain ID sera supérieur au max actuel
                    if (c.getId() >= prochainId) {
                        prochainId = c.getId() + 1;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement: " + e.getMessage());
        }
    }
}