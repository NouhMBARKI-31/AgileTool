package fr.bachelor.agiletool.models;

// Représente une action décidée en réunion (Qui fait Quoi pour Quand)
public class ActionItem {
    private String task;  // Le "Quoi"
    private String owner; // Le "Qui"
    private String dueDate; // Le "Quand"

    public ActionItem(String task, String owner, String dueDate) {
        this.task = task;
        this.owner = owner;
        this.dueDate = dueDate;
    }

    public String getTask() { return task; }
    public String getOwner() { return owner; }
    public String getDueDate() { return dueDate; }

    @Override
    public String toString() {
        return String.format("| %-30s | %-20s | %-15s |", task, owner, dueDate);
    }

    public String toCSV() {
        return task + ";" + owner + ";" + dueDate;
    }

    public static ActionItem fromCSV(String line) {
        String[] parts = line.split(";");
        if (parts.length >= 3) {
            return new ActionItem(parts[0], parts[1], parts[2]);
        }
        return null;
    }
}