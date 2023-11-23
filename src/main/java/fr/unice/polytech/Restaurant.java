package fr.unice.polytech;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Restaurant {



    private String name;


    private Schedule horaires;
    private Map<Integer, Integer> hourlyCapacities;

    private List<Menu> listemenu;
    private List<Order> orders;
     int capacity;






    public Restaurant(String name) {
        this.name = name;
        this.horaires = new Schedule();
        this.listemenu = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.capacity=10;
        this.hourlyCapacities = new HashMap<>();
        initializeHourlyCapacities();

    }

    public void setHourlyCapacity(int hour, int capacity) {
        hourlyCapacities.put(hour, capacity);
    }

    // Méthode pour obtenir la capacité à une heure spécifique
    public int getHourlyCapacity(int hour) {
        return (int) hourlyCapacities.getOrDefault(hour, capacity);

    }
    private void initializeHourlyCapacities() {
        List<Integer> openingHoursList = horaires.getOpeningHours();
        for (int hour : openingHoursList) {
            setHourlyCapacity(hour, capacity);
        }
    }

    public void setcapacity(int newC) {
        this.capacity=newC;
    }
    public int getCapacity() {
        return capacity;
    }

    public Schedule getHoraires() {
        return horaires;
    }

    public String getName() {
        return name;
    }

    public void setHoraires(Schedule horaires) {
        this.horaires = horaires;
    }


    public List<Menu> getListemenu() {
        return listemenu;
    }

    public void setListemenu(List<Menu> listemenu) {
        this.listemenu = listemenu;
    }

    public void placeOrder(List<Order> ordersToAdd) {
        this.orders.addAll(ordersToAdd);
    }

    public List<Order> getOrders() {
        return this.orders;
    }


    public void addMenu(Menu menu) {
        listemenu.add(menu);
    }
    public void setCapacity(int i) {
        this.capacity=i;
    }

    
}










































