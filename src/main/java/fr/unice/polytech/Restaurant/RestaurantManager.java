package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Menu;
import fr.unice.polytech.Order;
import fr.unice.polytech.Schedule;

import java.util.List;

public interface RestaurantManager extends RestaurantUser {
    public void setHourlyCapacity(int hour, int capacity);
    public int getHourlyCapacity(int hour);
    public void setHoraires(Schedule horaires);
    public void setListemenu(List<Menu> listemenu);
    public List<Order> getOrders();
    public void addMenu(Menu menu);
    public void setCapacity(int i);
}
