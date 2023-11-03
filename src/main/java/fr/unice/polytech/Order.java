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


    public  Order(String restaurant_name){
        this.restaurant_name = restaurant_name;
        this.menus = new ArrayList<>();
        this.creation_time=new Date();
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
}
