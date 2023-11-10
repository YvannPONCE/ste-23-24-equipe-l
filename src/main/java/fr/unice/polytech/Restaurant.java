package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;


public class Restaurant {


    String name;


    Schedule horaires;


    List<Menu> listemenu;
    List<Order> orders;





    public Restaurant(String name) {
        this.name = name;
        this.horaires = new Schedule();
        this.listemenu = new ArrayList<>();
        this.orders = new ArrayList<>();

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

}










































