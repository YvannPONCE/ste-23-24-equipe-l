package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Menu;
import fr.unice.polytech.Order;
import fr.unice.polytech.Schedule;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;


public class Restaurant implements RestaurantUser, RestaurantManager, RestaurantOrderManager {

    private String name;
    private Schedule horaires;
    private Map<Integer, Integer> hourlyCapacities;
    private List<Menu> listemenu;
    private List<Order> orders;
     int capacity;
     //String is the mail of the user, Date is the date of the last discount
     private HashMap<String, LocalDate> DiscountedUsers;
     private int DiscountThreshold;
     private double DiscountPercentage;
     private int DiscountDuration;

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

    public void setDiscountThreshold(int discountThreshold) {
        this.DiscountThreshold = discountThreshold;
    }

    public Integer getDiscountThreshold() {
        return this.DiscountThreshold;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.DiscountPercentage = discountPercentage;
    }

    public void setDiscountDuration(int discountDuration) {
        this.DiscountDuration = discountDuration;
    }

    public void addDiscountedUser(String mail) {
        LocalDate oldDate = this.DiscountedUsers.get(mail);
        if(oldDate != null && oldDate.isAfter(LocalDate.now())) {
            this.DiscountedUsers.put(mail, oldDate.plusDays(DiscountDuration));
            return;
        }
        this.DiscountedUsers.put(mail, LocalDate.now().plusDays(DiscountDuration));
    }
    
}










































