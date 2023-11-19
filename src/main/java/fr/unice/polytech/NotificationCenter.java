package fr.unice.polytech;

import fr.unice.polytech.Enum.Locations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class NotificationCenter implements NotificationDeliveryManagerInterface, NotificationOrderManagerInterface {
    private boolean orderReadyNotificationSent = false;
    @Override
    public boolean order_confirmed(UUID order_id, Locations locations, LocalDateTime delivery_date, String customer_email) {
        return true;
    }

    @Override
    public boolean order_ready(UUID order_id, String delivery_manName, String deliveryman_mail, Locations locations, String customerEmail) {
        String message = String.format("Dear %s,\n\n"
                + "You have a new delivery request for order ID %s.\n"
                + "Delivery location: %s.\n\n"
                + "Please proceed with the delivery.\n\n"
                + "Best regards,\nThe Delivery Team",delivery_manName, order_id, locations);
        orderReadyNotificationSent = true;

        sendNotification(deliveryman_mail, message);
        return true;
    }

    @Override
    public boolean order_delivered(UUID order_id, Locations locations, LocalDateTime delivery_date, String customer_email) {
        String notification = String.format("Dear %s,\n\n"
                + "Your order (ID: %s) has been successfully delivered to the following location:\n"
                + "%s.\n\n"
                + "Thank you for choosing our service!\n\n"
                + "Best regards,\nThe Delivery Team", customer_email, order_id,locations);
        sendNotification(customer_email,notification);

        return true;
    }

    @Override
    public boolean order_to_deliver(String username, String delivery_manEmail, LocalDateTime pickuptime, List<Restaurant> restaurants, Locations locations, String customerEmail) {

        return true;
    }
    public boolean isOrderReadyNotificationSent() {
        return orderReadyNotificationSent;
    }


    // Generic method to send notifications
    public void sendNotification(String recipient, String message) {
        NotificationDecoratorInterface emailDecorator = new EmailNotificationDecorator(recipient);
        emailDecorator.sendNotification(message);
    }
}