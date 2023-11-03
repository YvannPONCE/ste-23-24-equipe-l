package fr.unice.polytech;

import fr.unice.polytech.Enum.Status;

import javax.xml.stream.Location;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Order {
    UUID id;
    Status status;
    String restaurant_name;
    Date Creation_time;
    List<Menu> menus;


    public  Order(String restaurant_name){
        this.restaurant_name = restaurant_name;
        this.menus = new ArrayList<>();
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

    public void setId(UUID id) {
        this.id = id;
    }
}
