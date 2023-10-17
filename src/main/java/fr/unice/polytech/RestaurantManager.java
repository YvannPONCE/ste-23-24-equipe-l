package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;

public class RestaurantManager {

    List<Restaurant> restaurants;

    public RestaurantManager()
    {
        this.restaurants = new ArrayList<>();
    }

    public Boolean add_restaurant(Restaurant restaurant)
    {
        //this.restaurants.add(restaurant);
        return false;
    }

    public Boolean remove_restaurant(String restaurant_name)
    {
        //remove restaurant
        return false;
    }

    public List<Restaurant> get_restaurants()
    {
        //return restaurants;
        return new ArrayList<>();
    }
}
