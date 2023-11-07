package fr.unice.polytech;

import fr.unice.polytech.Enum.Status;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    UUID id;
    Status status;
    String restaurant_name;
    Date creation_time;
    List<Menu> menus;
    private double totalPrice;


    public  Order(String restaurant_name){
        this.restaurant_name = restaurant_name;
        this.menus = new ArrayList<>();
        this.totalPrice=0.0;
        this.creation_time=new Date();
    }

    public int getItemCount() {
        return menus.size(); // Ou utilisez d'autres attributs pour calculer le nombre d'articles
    }


    public UUID getId() {
        return id;
    }

    public void add_menu(Menu menu)
    {
        this.menus.add(menu);
    }

    public String get_restaurant_name() {
        return restaurant_name;
    }

    public List<Menu> get_menus() {
        return menus;
    }

    public Status getStatus() {
        return status;
    }
    public void setCreation_time(Date date){
         this.creation_time=date;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
    public Date getCreationTime() {
        return creation_time;
    }
    public void displayOrderSummary() {
        System.out.println("Restaurant Name: " + restaurant_name);
        for (Menu menu : menus) {
            System.out.println("Menu Name: " +menu.getItemName());
        }
    }


    public void setId(UUID id) {
        this.id = id;
    }
    public double calculateTotalPrice() {

        for (Menu menu : menus) {
            totalPrice += menu.getPrice();
        }
        return totalPrice;
    }

}
