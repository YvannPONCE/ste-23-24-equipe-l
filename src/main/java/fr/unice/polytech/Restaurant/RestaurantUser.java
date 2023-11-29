package fr.unice.polytech.Restaurant;

import fr.unice.polytech.Menu;
import fr.unice.polytech.Schedule;

import java.util.List;

public interface RestaurantUser {
    public Schedule getHoraires();
    public String getName();
    public List<Menu> getListemenu();
}
