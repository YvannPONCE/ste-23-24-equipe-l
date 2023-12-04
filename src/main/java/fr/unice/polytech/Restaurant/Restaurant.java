package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Menu;
import fr.unice.polytech.Order;
import fr.unice.polytech.Schedule;
import lombok.Getter;
import net.bytebuddy.asm.Advice;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Restaurant implements RestaurantUser, RestaurantManager, RestaurantOrderManager {

    private String name;
    private Schedule horaires;
    private Map<Integer, Integer> hourlyCapacities;
    private List<Menu> listemenu;
    private List<Order> orders;
    int capacity;
    int discountThreshold;
    int discountPeriod;
    double discountPercentage;
    HashMap<String, LocalDate> discountedUsers;

    public Restaurant(String name) {
        this.name = name;
        this.horaires = new Schedule();
        this.listemenu = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.capacity=10;
        this.hourlyCapacities = new HashMap<>();
        this.discountPercentage = 0.15;
        this.discountPeriod = 15;
        this.discountThreshold = 10;
        this.discountedUsers = new HashMap<>();
        initializeHourlyCapacities();
    }

    public void setHourlyCapacity(int hour, int capacity) {
        hourlyCapacities.put(hour, capacity);
    }

    // Méthode pour obtenir la capacité à une heure spécifique
    public int getHourlyCapacity(int hour) {
        return hourlyCapacities.getOrDefault(hour, capacity);

    }
    private void initializeHourlyCapacities() {
        List<Integer> openingHoursList = horaires.getOpeningHours();
        for (int hour : openingHoursList) {
            setHourlyCapacity(hour, capacity);
        }
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }
    public int getDiscountThreshold() {
        return discountThreshold;
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
    public void addMenu(Menu menu) {
        listemenu.add(menu);
    }
    public void setCapacity(int i) {
        this.capacity=i;
    }

    public void addUserToDiscountedUsers(String mail) {
        LocalDate lastDiscountExpirationDate = discountedUsers.get(mail);
        if (lastDiscountExpirationDate == null || lastDiscountExpirationDate.isBefore(LocalDate.now())) {
            discountedUsers.put(mail, LocalDate.now().plusDays(this.discountPeriod));
        } else {
            LocalDate newDiscount = lastDiscountExpirationDate.plusDays(this.discountPeriod);
            discountedUsers.put(mail, newDiscount);
        }
    }

    public LocalDate getDiscountExpirationDate(String mail) {
        return discountedUsers.get(mail);
    }
}
