package fr.unice.polytech.RestaurantManager;

import fr.unice.polytech.Restaurant.Restaurant;

public interface RestaurantManagerAdmin extends RestaurantManagerUser {
    public void addRestaurant(Restaurant restaurant);
    public void remove_restaurant(String restaurant_name);

}
