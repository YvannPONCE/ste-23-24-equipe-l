package fr.unice.polytech;
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
        return restaurant.getHourlyCapacity(getCurrentTime().getHour()) - numberOfMenus > 0;
    }

    public LocalDateTime getCurrentTime() {
        return currentTime;
    }
    public boolean canPlaceOrder(int numberOfMenus, LocalDateTime chosenSlot) {
        int chosenHour = chosenSlot.getHour();
        int chosenCapacity = restaurant.getHourlyCapacity(chosenHour);

        // Check if the chosen time slot has enough capacity for the order
        return chosenCapacity - numberOfMenus > 0;
    }

    public void placeOrder(int numberOfMenus) {
        if (canPlaceOrder(numberOfMenus)) {
            restaurant.setHourlyCapacity(getCurrentTime().getHour(),
                    restaurant.getHourlyCapacity(getCurrentTime().getHour()) - numberOfMenus);

            setChanged();
            notifyObservers();
            clearChanged();
        }
    }
    public void placeOrder_slot(int numberOfMenus, LocalDateTime chosenSlot) {
        int chosenHour = chosenSlot.getHour();

        updateHourlyCapacity(chosenHour, restaurant.getHourlyCapacity(chosenHour) - numberOfMenus);

        setChanged();
        notifyObservers();
        clearChanged();
    }

    // Assuming you have a method to update the capacity for the chosen hour
    private void updateHourlyCapacity(int hour, int newCapacity) {
        restaurant.setHourlyCapacity(hour,newCapacity);
    }

    public void resetCapacityafterDelivery(int numberOfMenus) {

            updateHourlyCapacity(currentTime.getHour(),this.restaurant.getHourlyCapacity(currentTime.getHour())+numberOfMenus);
            setChanged();
            notifyObservers();
            clearChanged();
            //System.out.println("Order placed. Remaining capacity: " + (restaurant.capacity - numberOfMenus));
    }

    public LocalDateTime getNextSlot() {
        this.nextSlot = getNextAvailableSlot();
        return nextSlot;
    }
    public LocalDateTime getNextSlot_chosen(LocalDateTime chosenSlot){
        this.nextSlot=getNextSlotAfterChosen(chosenSlot);
        return nextSlot;
    }

    public int getCapacity() {
        return this.restaurant.capacity;
    }
    public LocalDateTime getNextAvailableSlot() {

        LocalDateTime deliveryTime = getCurrentTime().plus(PREPARATION_TIME);

        if (deliveryTime.isAfter(getCurrentTime())) {
            return deliveryTime;
        } else {
            long slotsNeeded = Duration.between(getCurrentTime(), deliveryTime).toMinutes();
            long slotsAvailable = (slotsNeeded / this.restaurant.capacity) + 1;
            return getCurrentTime().plusHours(slotsAvailable);
        }
    }

    public LocalDateTime getNextSlotAfterChosen(LocalDateTime chosenSlot) {
        int chosenHour = chosenSlot.getHour();
        int chosenCapacity = restaurant.getHourlyCapacity(chosenHour);

        LocalDateTime nextSlot = chosenSlot.plusHours(1);
        int nextCapacity = restaurant.getHourlyCapacity(nextSlot.getHour());

        while (nextCapacity <= 0) {
            nextSlot = nextSlot.plusHours(1);
            nextCapacity = restaurant.getHourlyCapacity(nextSlot.getHour());
        }

        return nextSlot;
    }

}
