package fr.unice.polytech.RestaurantManager;

import java.util.Observer;

public interface CapacityObserver extends Observer {
    void updateCapacity(int newCapacity);
}
