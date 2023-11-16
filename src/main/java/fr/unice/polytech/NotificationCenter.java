package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationCenter implements NotificationDeliveryManagerInterface,NotificationOrderManagerInterface{
    @Override
    public boolean order_delivered(UUID order_id, Locations locations, Date delivery_date, String customer_email) {
        return false;
    }

    @Override
    public boolean order_to_deliver(String username, String delivery_manEmail, Date pickuptime, List<Restaurant> restaurants, Locations locations, String customerEmail) {
        return false;
    }

    @Override
    public boolean order_confirmed(UUID order_id, Locations locations, Date delivery_date, String customer_email) {
        return false;
    }

    @Override
    public boolean order_ready(UUID order_id, String delivery_manName, String deliveryman_PhoneNb, Locations locations, String customerEmail) {
        return false;
    }
}
