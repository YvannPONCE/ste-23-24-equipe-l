package fr.unice.polytech;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Schedule {

    private Map<String, String[]> schedule;

    public Schedule() {
        schedule = new HashMap<>();
        initializeSchedule();
    }

    private void initializeSchedule() {
        // Initialize the schedule with default opening hours from 9:00 to 00:00.
        String[] defaultHours = {"9:00", "00:00"};
        schedule.put("lundi", defaultHours);
        schedule.put("mardi", defaultHours);
        schedule.put("mercredi", defaultHours);
        schedule.put("jeudi", defaultHours);
        schedule.put("vendredi", defaultHours);
        schedule.put("samedi", defaultHours);
        schedule.put("dimanche", defaultHours);
    }

    public void setOpeningHours(String day, String openingTime, String closingTime) {
        String lowercaseDay = day.toLowerCase();

        if (schedule.containsKey(lowercaseDay)) {
            String[] hours = {openingTime, closingTime};
            schedule.put(lowercaseDay, hours);
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

    public List<Integer> getOpeningHours() {
        List<Integer> openingHoursList = new ArrayList<>();
        for (Map.Entry<String, String[]> entry : schedule.entrySet()) {
            String[] hours = entry.getValue();
            if (!(hours[0].equals("Fermé") && hours[1].equals("Fermé"))) {
                openingHoursList.add(Integer.parseInt(hours[0].replace(":", ""))); // Convert "9:00" to 900
                openingHoursList.add(Integer.parseInt(hours[1].replace(":", ""))); // Convert "00:00" to 0
            }
        }
        return openingHoursList;
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
