package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;


public class Restaurant {


    private String name;


    private Schedule horaires;


    private List<Menu> listemenu;
    private List<Order> orders;
     int capacity;






    public Restaurant(String name) {
        this.name = name;
        this.horaires = new Schedule();
        this.listemenu = new ArrayList<>();
        this.orders = new ArrayList<>();
        this.capacity=10;


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

    public void setCapacity(int i) {
        this.capacity=i;
    }
}










































