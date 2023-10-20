package fr.unice.polytech;

import java.util.HashMap;
import java.util.Map;

public class Schedule {


    public  Map<String, String[]> schedule;

    public Schedule() {
        schedule = new HashMap<>();
        initializeSchedule();
    }

    private void initializeSchedule() {
        // Initialisez le planning avec tous les jours de la semaine.
        String[] defaultHours = {"Fermé", "Fermé"}; // Par défaut, tous les jours sont fermés.
        schedule.put("lundi", defaultHours);
        schedule.put("mardi", defaultHours);
        schedule.put("mercredi", defaultHours);
        schedule.put("jeudi", defaultHours);
        schedule.put("vendredi", defaultHours);
        schedule.put("samedi", defaultHours);
        schedule.put("dimanche", defaultHours);
    }

    public void setOpeningHours(String day, String openingTime, String closingTime) {
        if (schedule.containsKey(day.toLowerCase())) {
            String[] hours = {openingTime, closingTime};
            schedule.put(day.toLowerCase(), hours);
        } else {
            System.out.println("Jour invalide. Veuillez spécifier un jour de la semaine valide.");
        }
    }
    public Map<String, String[]> getSchedule() {
        return schedule;
    }

    public void setSchedule(Map<String, String[]> schedule) {
        this.schedule = schedule;
    }


    public String getOpeningHours(String day) {
        if (schedule.containsKey(day.toLowerCase())) {
            String[] hours = schedule.get(day.toLowerCase());
            if (hours[0].equals("Fermé") && hours[1].equals("Fermé")) {
                return "Le " + day + " est fermé ce jour-là.";
            } else {
                return "Le " + day + " est ouvert de " + hours[0] + " à " + hours[1] + ".";
            }
        } else {
            return "Jour invalide. Veuillez spécifier un jour de la semaine valide.";
        }
    }

    public void displaySchedule() {
        for (Map.Entry<String, String[]> entry : schedule.entrySet()) {
            String day = entry.getKey();
            String[] hours = entry.getValue();
            System.out.println("Le " + day + " : " + hours[0] + " à " + hours[1]);
        }
    }


    public String getStoreHours(String day) {
        if (schedule.containsKey(day)) {
            String[] hours = schedule.get(day);
            return hours[0] + " - " + hours[1];
        } else {
            return "Horaires non disponibles pour ce jour.";
        }
    }
}
