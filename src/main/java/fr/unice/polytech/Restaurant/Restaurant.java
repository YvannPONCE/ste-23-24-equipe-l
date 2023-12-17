package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Role;
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
    private List<String> staffMembers;
    int capacity;
    int discountThreshold;
    int discountPeriod;
    double discountPercentage;
    private HashMap<Role, Double> roleDiscount;
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
        this.staffMembers = new ArrayList<>();
        initializeHourlyCapacities();

        roleDiscount = new HashMap<>();
        roleDiscount.put(Role.CUSTOMER_TEACHER, 0.20);
        roleDiscount.put(Role.DELIVER_MAN, 0.10);
        roleDiscount.put(Role.CUSTOMER_STAFF, 0.15);
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

    public List<String> getStaffMembers() {
        return staffMembers;
    }
    public void AddStaffMember(String staffMember) {
        staffMembers.add(staffMember);
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
    public List<Menu> getListemenu(Role role) {
        List<Menu> newMenuList = new ArrayList<>();
        for(Menu menu : listemenu){
            Menu menu2 = new Menu(menu.getItemName(), menu.getPrice()*roleDiscount.get(role), menu.getMenuType());
            newMenuList.add(menu2);
        }
        return newMenuList;
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

    public Menu getMenu(String menuName) {
        for (Menu menu : listemenu) {
            if (menu.getItemName().equals(menuName)) {
                return menu;
            }
        }
        return null;
    }
}
