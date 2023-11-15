package fr.unice.polytech;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Observable;

public class RestaurantCapacityCalculator extends Observable {
    Restaurant restaurant;
    private static final Duration PREPARATION_TIME = Duration.ofHours(1);
    private LocalDateTime nextSlot;

    // This field should not be public
    private LocalDateTime currentTime;

    public RestaurantCapacityCalculator(Restaurant restaurant) {
        this.restaurant=restaurant;
        this.currentTime = LocalDateTime.now();
    }


    public boolean canPlaceOrder(int numberOfMenus) {
        return this.restaurant.capacity - numberOfMenus > 0;
    }

    public LocalDateTime getCurrentTime() {
        return this.currentTime;
    }

    public void placeOrder(int numberOfMenus) {
        if (canPlaceOrder(numberOfMenus)) {
            restaurant.setCapacity(this.restaurant.capacity-=numberOfMenus);
            setChanged();
            notifyObservers();
            clearChanged();



            System.out.println("Order placed. Remaining capacity: " + (restaurant.capacity - numberOfMenus));
        }
    }
    public void resetCapacityafterDelivery(int numberOfMenus) {

            restaurant.setCapacity(this.restaurant.capacity+=numberOfMenus);
            setChanged();
            notifyObservers();
            clearChanged();
            System.out.println("Order placed. Remaining capacity: " + (restaurant.capacity - numberOfMenus));
    }

    public LocalDateTime getNextSlot() {
        this.nextSlot = getNextAvailableSlot();
        return nextSlot;
    }

    public int getCapacity() {
        return this.restaurant.capacity;
    }

    public LocalDateTime getNextAvailableSlot() {
        System.out.println(getCurrentTime() + "777");

        LocalDateTime deliveryTime = getCurrentTime().plus(PREPARATION_TIME);

        if (deliveryTime.isAfter(getCurrentTime())) {
            return deliveryTime;
        } else {
            long slotsNeeded = Duration.between(getCurrentTime(), deliveryTime).toMinutes();
            long slotsAvailable = (slotsNeeded / this.restaurant.capacity) + 1;
            return getCurrentTime().plusHours(slotsAvailable);
        }
    }
}
