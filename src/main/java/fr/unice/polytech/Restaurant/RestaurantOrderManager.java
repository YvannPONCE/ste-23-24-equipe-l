package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Order;

import java.util.List;

public interface RestaurantOrderManager {
    public int getHourlyCapacity(int hour);
    public void placeOrder(List<Order> ordersToAdd);
    public void setHourlyCapacity(int hour, int capacity);
    public int getCapacity();
}
