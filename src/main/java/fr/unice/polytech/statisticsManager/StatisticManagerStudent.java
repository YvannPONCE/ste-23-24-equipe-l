package fr.unice.polytech.statisticsManager;

import fr.unice.polytech.Enum.Locations;

import java.time.LocalDateTime;
import java.util.HashMap;

public interface StatisticManagerStudent {

    public HashMap<Locations, Integer> get_popular_locations();
    public int getOrdersCount();
    public HashMap<String, Integer> getFavoriteRestaurant(String userEmail);

    public int getVolumeByHour(LocalDateTime localDateTime);
}
