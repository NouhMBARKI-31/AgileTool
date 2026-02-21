package fr.bachelor.agiletool.models;

import java.util.ArrayList;
import java.util.List;

public class RapportReunion {
    private String titre;
    private String auteur;
    private String participants;
    private String dateReunion;
    private List<ActionItem> actions;

    public RapportReunion(String titre, String auteur, String participants, String dateReunion) {
        this.titre = titre;
        this.auteur = auteur;
        this.participants = participants;
        this.dateReunion = dateReunion;
        this.actions = new ArrayList<>();
    }

    public String getTitre() { return titre; }
    public String getAuteur() { return auteur; }
    public String getParticipants() { return participants; }
    public String getDateReunion() { return dateReunion; }
    public List<ActionItem> getActions() { return actions; }

    public void addAction(String quoi, String qui, String quand) {
        ActionItem newItem = new ActionItem(quoi, qui, quand);
        this.actions.add(newItem);
    }

    // Affiche le rapport directement dans la console avec un joli cadre
    public void displayTable() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║ RAPPORT DE RÉUNION : " + String.format("%-53s", titre) + "║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ Auteur       : " + String.format("%-58s", auteur) + "║");
        System.out.println("║ Date         : " + String.format("%-58s", dateReunion) + "║");
        System.out.println("║ Participants : " + String.format("%-58s", participants) + "║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");
        System.out.println("║ TABLEAU DES ACTIONS (Quoi, Qui, Quand)                                    ║");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════╣");

        if (actions.isEmpty()) {
            System.out.println("║ Aucune action enregistrée                                                  ║");
        } else {
            for (ActionItem item : actions) {
                System.out.println("║ " + item + " ║");
            }
        }
        System.out.println("╚════════════════════════════════════════════════════════════════════════════╝");
    }

    public String toFileContent() {
        StringBuilder sb = new StringBuilder();
        sb.append("RAPPORT DE RÉUNION\n");
        sb.append("==================\n\n");
        sb.append("Titre: ").append(titre).append("\n");
        sb.append("Auteur: ").append(auteur).append("\n");
        sb.append("Date: ").append(dateReunion).append("\n");
        sb.append("Participants: ").append(participants).append("\n\n");
        sb.append("ACTIONS:\n");
        sb.append("QUOI;QUI;QUAND\n");
        for (ActionItem action : actions) {
            sb.append(action.toCSV()).append("\n");
        }
        return sb.toString();
    }
}