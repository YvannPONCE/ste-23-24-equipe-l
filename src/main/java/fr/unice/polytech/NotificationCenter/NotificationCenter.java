package fr.unice.polytech.NotificationCenter;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.NotificationDeliveryManagerInterface;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class NotificationCenter implements NotificationDeliveryManagerInterface, NotificationOrderManagerInterface {


    public UserManager userManager;

    public  NotificationCenter(UserManager userManager) {
        this.userManager=userManager;
    }
    
    private boolean orderReadyNotificationSent = false;
    @Override
    public boolean order_confirmed(UUID order_id, Locations locations, Date delivery_date, String customer_email) {
        String message= String.format("Dear %s,\n\nThank you for placing an order with order ID %s. Your order for delivery to %s on %s has been confirmed.\n\nBest regards,\nThe Order Confirmation Team",
                customer_email, order_id.toString(), locations.toString(), delivery_date.toString());
        User user=findUser(customer_email);
        if(user==null)return false;
        user.getNotifications().add(new Notification(message));
        sendNotification(customer_email, message);
        return true;
    }

    @Override
    public boolean orderReady(UUID order_id, String delivery_manName, String deliveryman_mail, Locations locations, String customer_email) {
        
        String message = String.format("Dear %s,\n\n"
                + "You have a new delivery request for order ID %s.\n"
                + "Delivery location: %s.\n\n"
                + "Please proceed with the delivery.\n\n"
                + "Best regards,\nThe Delivery Team",delivery_manName, order_id, locations);

        String userMessage = String.format("Dear Customer,\n\n"
                + "Good news! Your order with ID %s is now ready for delivery.\n"
                + "Our delivery team is on the way to your location at %s, and your assigned delivery person is %s.\n"
                + "You can expect your delivery soon.\n\n"
                + "Thank you for choosing our services!\n\n"
                + "Best regards,\nThe Delivery Team", order_id, locations, delivery_manName);
        User user2=findUser(customer_email);
        user2.getNotifications().add(new Notification(userMessage));
        // Envoyer le message à l'utilisateur
        sendNotification(customer_email, userMessage);
        orderReadyNotificationSent = true;
        User user=findUser(deliveryman_mail);
        user.getNotifications().add(new Notification(message));
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
        User user=findUser(customer_email);
        user.getNotifications().add(new Notification(notification));
        sendNotification(customer_email,notification);

        return true;
    }

    @Override
    public boolean order_to_deliver(String username, String delivery_manEmail, LocalDateTime pickuptime, List<Restaurant> restaurants, Locations locations, String customerEmail) {

        return true;
    }

    public User findUser(String email){
        List<User> users=this.userManager.getUserList();
        for(User user: users){
            if(user.get_email()==email){
                return user;
            }
        }

        return null;
        
    }


    // Generic method to send notifications
    public void sendNotification(String recipient, String message) {
        NotificationDecoratorInterface emailDecorator = new EmailNotificationDecorator(recipient);
        emailDecorator.sendNotification(message);

    }
}