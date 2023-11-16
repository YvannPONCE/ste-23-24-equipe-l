package fr.unice.polytech;

import java.util.Observable;
import java.util.Observer;


   public class OrderObserver implements Observer {
        private RestaurantCapacityCalculator restaurantCapacityManager;

        public OrderObserver(RestaurantCapacityCalculator restaurantCapacityManager) {
            this.restaurantCapacityManager = restaurantCapacityManager;
            this.restaurantCapacityManager.addObserver(this);
        }


        @Override
        public void update(Observable o, Object arg) {
            //System.out.println("Capacity changed. New capacity: " + restaurantCapacityManager.getCapacity());
        }
    }



