package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface NotificationDeliveryManagerInterface {
    public boolean order_delivered(UUID order_id, Locations locations, LocalDateTime delivery_date, String customer_email);

    public boolean order_to_deliver(String username, String delivery_manEmail, LocalDateTime pickuptime, List<Restaurant> restaurants, Locations locations, String customerEmail);
}