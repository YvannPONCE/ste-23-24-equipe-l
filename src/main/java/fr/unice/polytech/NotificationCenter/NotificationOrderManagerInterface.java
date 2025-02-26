package fr.unice.polytech.NotificationCenter;

import fr.unice.polytech.Enum.Locations;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public interface NotificationOrderManagerInterface {
    public boolean order_confirmed(UUID order_id, Locations locations, LocalDateTime delivery_date, String customer_email);

    public boolean orderReady(UUID order_id, String delivery_manName, Locations locations, String email);

}
