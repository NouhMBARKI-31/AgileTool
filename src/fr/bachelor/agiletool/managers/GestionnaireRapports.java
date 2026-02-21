package fr.bachelor.agiletool.managers;

import fr.bachelor.agiletool.models.RapportReunion;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireRapports {
    private static final String DOSSIER = "rapports";
    private static final String INDEX = "rapports/index.csv";
    // On garde en mémoire juste la liste des fichiers, pas tout le contenu des rapports
    private List<String> indexRapports;

    public GestionnaireRapports() {
        indexRapports = new ArrayList<>();

        // Création automatique du dossier "rapports" s'il n'existe pas
        File dir = new File(DOSSIER);
        if (!dir.exists()) {
            dir.mkdir();
        }
        chargerIndex();
    }

    public void sauvegarderRapport(RapportReunion rapport) {
        // Génération d'un nom unique basé sur l'heure actuelle (timestamp)
        String nomFichier = "rapport_" + System.currentTimeMillis() + ".txt";
        String cheminComplet = DOSSIER + "/" + nomFichier;

        try (PrintWriter writer = new PrintWriter(new FileWriter(cheminComplet))) {
            // 1. On écrit le contenu détaillé dans un fichier .txt
            writer.print(rapport.toFileContent());

            // 2. On ajoute une entrée dans l'index pour le retrouver facilement plus tard
            indexRapports.add(nomFichier + ";" + rapport.getTitre() + ";" + rapport.getDateReunion());
            sauvegarderIndex();

            System.out.println("✓ Rapport sauvegardé: " + nomFichier);
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde du rapport: " + e.getMessage());
        }
    }

    public void afficherListeRapports() {
        if (indexRapports.isEmpty()) {
            System.out.println("\n⚠ Aucun rapport enregistré.");
            return;
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        LISTE DES RAPPORTS                                  ║");
        System.out.println("╠════╦═══════════════════════════════════════╦═══════════════════════════════╣");
        System.out.println("║ N° ║              Titre                    ║            Date               ║");
        System.out.println("╠════╬═══════════════════════════════════════╬═══════════════════════════════╣");

        for (int i = 0; i < indexRapports.size(); i++) {
            String[] parts = indexRapports.get(i).split(";");
            System.out.printf("║ %-2d ║ %-41s ║ %-29s ║%n",
                    i + 1,
                    parts.length > 1 ? parts[1] : "Sans titre",
                    parts.length > 2 ? parts[2] : "");
        }

        System.out.println("╚════╩═══════════════════════════════════════╩═══════════════════════════════╝");
    }

    // Sauvegarde l'index (la liste des fichiers)
    private void sauvegarderIndex() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(INDEX))) {
            for (String ligne : indexRapports) {
                writer.println(ligne);
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur lors de la sauvegarde de l'index: " + e.getMessage());
        }
    }

    // Charge l'index au démarrage
    private void chargerIndex() {
        File file = new File(INDEX);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(INDEX))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    indexRapports.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("✗ Erreur lors du chargement de l'index: " + e.getMessage());
        }
    }
}