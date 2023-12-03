package fr.unice.polytech.RestaurantManager;

import fr.unice.polytech.Order;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.Restaurant.RestaurantUser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantManager implements RestaurantManagerUser, RestaurantManagerAdmin, RestaurantManagerOrderManager, RestaurantManagerStaff {

    List<Restaurant> restaurants;

    public RestaurantManager()
    {
        this.restaurants = new ArrayList<>();
    }

    public void add_restaurant(Restaurant restaurant)
    {
        this.restaurants.add(restaurant);
    }

    public List<Restaurant> getRestaurants()
    {
        //return restaurants;
        return this.restaurants;
    }

    public Restaurant getRestaurant(String restaurant_name) {
        List<Restaurant> foundRestaurants = this.restaurants.stream()
                .filter(restaurant -> restaurant.getName().equals(restaurant_name))
                .collect(Collectors.toList());
        if(foundRestaurants.size()>0)
        {
            return foundRestaurants.get(0);
        }
        return null;
    }

    public void remove_restaurant(String restaurant_name){
        this.restaurants.removeIf(restaurant -> restaurant.getName().equals(restaurant_name));
    }
    public List<RestaurantUser> getRestaurantsByTimeAndDay(int hour, int minute, String day) {
        return this.restaurants.stream()
                .filter(restaurant -> restaurant.getHoraires().isOpenAt(hour, minute, day))
                .collect(Collectors.toList());
    }
    public List<RestaurantUser> getClosedRestaurantsByTimeAndDay(int hour, int minute, String day) {
        return this.restaurants.stream()
                .filter(restaurant -> restaurant.getHoraires().isClosedAt(hour, minute, day))
                .collect(Collectors.toList());
    }

    public void addRestaurant(Restaurant restaurant)
    {
        this.restaurants.add(restaurant);
    }

}
