package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;

import java.util.*;
import java.util.stream.Collectors;

public class BusinessIntelligence {
    HashMap<Restaurant, HashMap<Menu, Integer>> menuStatisticsByRestaurants;
    HashMap<Locations,Integer> locationStatistics;
    RestaurantManager restaurantManager;
    public BusinessIntelligence(RestaurantManager restaurantManager)
    {
        this.restaurantManager = restaurantManager;
        menuStatisticsByRestaurants = new HashMap<>();
        locationStatistics = new HashMap<>();
        initLocationsStatistics();
    }
    private void initLocationsStatistics()
    {
        for (Locations location : Locations.values())
        {
            locationStatistics.put(location, 0);
        }
    }
    private void initRestaurantsStatistics()
    {
        HashMap<Restaurant, HashMap<Menu, Integer>> menuStatisticsByRestaurants = new HashMap<>();
        for (Restaurant restaurant : restaurantManager.getRestaurants())
        {
            menuStatisticsByRestaurants.put(restaurant, initMenusStatistics(restaurant.getListemenu()));
        }
    }
    private HashMap<Menu, Integer> initMenusStatistics(List<Menu> menus)
    {
        HashMap<Menu, Integer> menusStatistics = new HashMap<>();
        for(Menu menu : menus)
        {
            menusStatistics.put(menu, 0);
        }
        return menusStatistics;
    }


    public void add_order(GroupOrder groupOrder)
    {
        HashMap<String, List<Menu>> menusByRestaurants = groupOrder.getMenusByRestaurants();
        Locations location = groupOrder.get_delivery_location();
        locationStatistics.put(location, locationStatistics.get(location)+1);

        for(Map.Entry<String, List<Menu>> entry : menusByRestaurants.entrySet())
        {
            addMenuToRestaurant(entry.getKey(), entry.getValue());
        }
    }
    private void addMenuToRestaurant(String restaurantName, List<Menu> menus)
    {
        Restaurant corespondingRestaurant = menuStatisticsByRestaurants.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(restaurantName))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);

        if(corespondingRestaurant == null)return;

       HashMap<Menu, Integer> menusPopularity = menuStatisticsByRestaurants.get(corespondingRestaurant);
        for (Menu menu : menus) {
            menusPopularity.merge(menu, 1, Integer::sum);
        }
    }

    public HashMap<Locations, Integer> get_popular_locations()
    {
        return locationStatistics;
    }

    public HashMap<Menu, Integer> get_popular_menus(String restaurantName)
    {
        Restaurant corespondingRestaurant = menuStatisticsByRestaurants.entrySet().stream()
                .filter(entry -> entry.getKey().getName().equals(restaurantName))
                .map(entry -> entry.getKey())
                .findFirst()
                .orElse(null);

        if(corespondingRestaurant == null)return null;

        return menuStatisticsByRestaurants.get(corespondingRestaurant);
    }

}
