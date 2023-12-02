package fr.unice.polytech.statisticsManager;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticsManager implements StatisticManagerStudent, StatisticManagerRestaurant, StaticticsManagerCampus, StatisticManagerOrderManager {

    HashMap<Restaurant, HashMap<Menu, Integer>> menuStatisticsByRestaurants;
    HashMap<String, List<Order>> userStatistics;
    HashMap<Locations,Integer> locationStatistics;
    RestaurantManager restaurantManager;
    public StatisticsManager(RestaurantManager restaurantManager)
    {
        this.restaurantManager = restaurantManager;
        menuStatisticsByRestaurants = new HashMap<>();
        userStatistics = new HashMap<>();
        locationStatistics = new HashMap<>();
        initLocationsStatistics();
        initRestaurantsStatistics();
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


    public void addOrder(GroupOrder groupOrder)
    {
        Locations location = groupOrder.getDeliveryLocation();
        locationStatistics.put(location, locationStatistics.get(location)+1);

        HashMap<String, List<Menu>> menusByRestaurants = groupOrder.getMenusByRestaurants();
        for(Map.Entry<String, List<Menu>> entry : menusByRestaurants.entrySet())
        {
            addMenuToRestaurant(entry.getKey(), entry.getValue());
        }

        HashMap<String , List<Order>> globalOrders = groupOrder.getGlobalOrders();
        for(Map.Entry<String, List<Order>> entry : globalOrders.entrySet())
        {
            addOrderToUser(entry.getKey(), entry.getValue());
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
    private void addOrderToUser(String userEmail, List<Order> orders)
    {
        List<Order> userOrders = userStatistics.get(userEmail);
        if(userOrders == null)
        {
            userStatistics.put(userEmail, orders);
            return;
        }
        userOrders.addAll(orders);
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

    public int getOrdersCount() {
        int count = 0;

        for(HashMap<Menu ,Integer> map: menuStatisticsByRestaurants.values())
        {
            for (int value : map.values()) {
                count += value;
            }
        }
        return count;
    }

    public int getUserOrderCount(String userEmail)
    {
        List<Order> userOrders = userStatistics.get(userEmail);
        if(userOrders == null)return 0;
        return userOrders.size();
    }

    public HashMap<String, Integer> getFavoriteRestaurant(String userEmail){
        HashMap<String, Integer> restaurantCount = new HashMap<>();
        List<Order> userOrders = userStatistics.get(userEmail);
        if(userOrders == null)return new HashMap<>();
        for(Order order : userOrders)
        {
            restaurantCount.put(order.getRestaurant_name(), restaurantCount.getOrDefault(order.getRestaurant_name(), 0)+1);
        }
        return restaurantCount;
    }
}
