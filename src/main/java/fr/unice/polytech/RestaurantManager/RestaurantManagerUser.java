package fr.unice.polytech.RestaurantManager;

import fr.unice.polytech.Restaurant.RestaurantUser;

import java.util.List;

public interface RestaurantManagerUser {
    public RestaurantUser getRestaurant(String restaurant_name);
    public List<RestaurantUser> getRestaurantsByTimeAndDay(int hour, int minute, String day);
    public List<RestaurantUser> getClosedRestaurantsByTimeAndDay(int hour, int minute, String day);
}
