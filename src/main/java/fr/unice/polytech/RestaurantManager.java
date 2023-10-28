package fr.unice.polytech;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantManager {

    List<Restaurant> restaurants;

    public RestaurantManager()
    {
        this.restaurants = new ArrayList<>();
    }

    public void add_restaurant(Restaurant restaurant)
    {
        this.restaurants.add(restaurant);
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

    public Restaurant get_restaurant(String restaurant_name) {
        List<Restaurant> foundRestaurants = this.restaurants.stream()
                .filter(restaurant -> restaurant.getName().equals(restaurant_name))
                .collect(Collectors.toList());

        if(restaurants.size()>0)
        {
            return foundRestaurants.get(0);
        }
        return null;
    }
}
