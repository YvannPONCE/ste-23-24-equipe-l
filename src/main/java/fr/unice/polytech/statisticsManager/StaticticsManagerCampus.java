package fr.unice.polytech.statisticsManager;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Menu;

import java.util.HashMap;

public interface StaticticsManagerCampus {

    public HashMap<Locations, Integer> get_popular_locations();
    public HashMap<Menu, Integer> get_popular_menus(String restaurantName);
    public int getOrdersCount();
}
