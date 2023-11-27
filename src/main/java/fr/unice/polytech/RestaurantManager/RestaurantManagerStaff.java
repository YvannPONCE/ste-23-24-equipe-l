package fr.unice.polytech.RestaurantManager;

import fr.unice.polytech.Order;
import fr.unice.polytech.Restaurant.RestaurantManager;
import fr.unice.polytech.Restaurant.RestaurantUser;

import java.util.List;

public interface RestaurantManagerStaff extends RestaurantManagerUser {
    public List<Order> getCurrentOrders(String restaurantName);
    public RestaurantManager getRestaurant(String restaurant_name);
}
