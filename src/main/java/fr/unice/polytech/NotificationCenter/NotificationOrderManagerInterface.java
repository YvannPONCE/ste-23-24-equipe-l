package fr.unice.polytech.NotificationCenter;

import fr.unice.polytech.Enum.Locations;

import java.util.Date;
import java.util.UUID;

public interface NotificationOrderManagerInterface {
    public boolean order_confirmed(UUID order_id, Locations locations, Date delivery_date, String customer_email);

    public boolean orderReady(UUID order_id, String delivery_manName, String deliveryman_PhoneNb, Locations locations, String email);

}
