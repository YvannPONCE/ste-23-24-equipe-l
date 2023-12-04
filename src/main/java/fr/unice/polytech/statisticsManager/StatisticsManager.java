package fr.unice.polytech.statisticsManager;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.statisticsManager.object.VolumeInsight;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StatisticsManager implements StatisticManagerStudent, StatisticManagerRestaurant, StaticticsManagerCampus, StatisticManagerOrderManager {

    HashMap<Restaurant, HashMap<Menu, Integer>> menuStatisticsByRestaurants;
    List<VolumeInsight> volumeInsights;
    HashMap<String, List<Order>> userStatistics;
    HashMap<Locations,Integer> locationStatistics;
    RestaurantManager restaurantManager;
    public StatisticsManager(RestaurantManager restaurantManager)
    {
        this.restaurantManager = restaurantManager;
        menuStatisticsByRestaurants = new HashMap<>();
        userStatistics = new HashMap<>();
        locationStatistics = new HashMap<>();
        volumeInsights = new ArrayList<>();
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
        Map<String, List<Menu>> menusByRestaurants = groupOrder.getMenusByRestaurants();
        Map<String , List<Order>> globalOrders = groupOrder.getGlobalOrders();

        locationStatistics.put(location, locationStatistics.get(location)+1);
        for(Map.Entry<String, List<Menu>> entry : menusByRestaurants.entrySet())
        {
            addMenuToRestaurant(entry.getKey(), entry.getValue());
        }
        for(Map.Entry<String, List<Order>> entry : globalOrders.entrySet())
        {
            addOrderToUser(entry.getKey(), entry.getValue());
            addOrderToVolume(entry.getValue());
        }
    }

    private void addOrderToVolume(List<Order> orders) {
        for(Order order : orders){
            Instant instant = order.getCreation_time().toInstant(); // Convert Date to Instant
            LocalDateTime orderTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
            VolumeInsight volumeInsight =  volumeInsights.stream()
                    .filter(volumeInsight1 -> (volumeInsight1.getRestaurantName().equals(order.getRestaurant_name())))
                    .filter(volumeInsight1 -> volumeInsight1.inSameHour(orderTime))
                    .findFirst().orElse(null);

            if(volumeInsight != null){
                volumeInsight.addOrders(order.getItemCount());
            }else {
                volumeInsight = new VolumeInsight(order.getItemCount(), orderTime, order.getRestaurant_name());
                volumeInsights.add(volumeInsight);
            }
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
            menusPopularity.merge(new Menu(menu), 1, Integer::sum);
        }
    }
    private void addOrderToUser(String userEmail, List<Order> orders)
    {
        List<Order> userOrders = userStatistics.get(userEmail);
        if(userOrders == null)
        {
            userStatistics.put(userEmail, new ArrayList<>(orders));
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

    @Override
    public int getVolumeByHour(LocalDateTime time) {
        System.out.println(volumeInsights);
        return volumeInsights.stream()
                .filter(volumeInsight -> (volumeInsight.inSameHour(time)))
                .mapToInt(volumeInsight -> volumeInsight.getVolume())
                .sum();
    }
    @Override
    public int getVolumeByRestaurant(String restaurantName) {
        return volumeInsights.stream()
                .filter(volumeInsight -> (volumeInsight.getRestaurantName().equals(restaurantName)))
                .mapToInt(volumeInsight -> volumeInsight.getVolume())
                .sum();
    }
}
