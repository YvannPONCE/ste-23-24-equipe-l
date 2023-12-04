package fr.unice.polytech.statisticsManager.object;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class VolumeInsight {
    int volume;
    LocalDateTime time;
    String restaurantName;

    public VolumeInsight(int volume, LocalDateTime time, String restaurantName){
        this.volume = volume;
        this.time = time;
        this.restaurantName = restaurantName;
    }
    public void addOrders(int volume){
        this.volume += volume;
    }
    public int getVolume() {
        return volume;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public boolean inSameHour(LocalDateTime time){
        return this.time.getDayOfYear() == time.getDayOfYear()
                && this.time.getHour() == time.getHour();
    }

    @Override
    public String toString() {
        return "VolumeInsight{" +
                "volume=" + volume +
                ", time=" + time +
                ", restaurantName='" + restaurantName + '\'' +
                '}';
    }
}
