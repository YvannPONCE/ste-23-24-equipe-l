package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.RestaurantManager.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.UUID;

import static org.junit.Assert.assertTrue;

public class DeliveryManNotificationdefs {

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private UUID orderId;
    NotificationCenter notificationCenter;
    private UserManager userManager;
    private Order order;

    @Given("user {string} ordered from {string}")
    public void user_ordered_from(String string, String string2) {
        restaurant = new Restaurant(string2 );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);


        orderManager = new OrderManager(restaurantManager, new UserManager(), new BusinessIntelligence(restaurantManager));

        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager );
        deliveryManager.addDeliveryman("Albert@gmail.com","Albert");
        orderManager.addDeliveryManager(deliveryManager);
        orderManager.userManager.add_user(new User(string,"user", Role.CUSTOMER_STAFF));
    this.notificationCenter=new NotificationCenter(orderManager.userManager);

    order = new Order(string2);
        order.add_menu(new Menu("chicken nuggets", 5.50));


        orderId = orderManager.place_order(string, order, Locations.HALL_PRINCIPAL);
        orderManager.pay_order(orderId, string, "7936 3468 9302 8371");
    }
    @When("the order is ready and restaurant validate the order for delivery")
    public void the_order_is_ready_and_restaurant_validate_the_order_for_delivery() {
        orderManager.validate_order(orderId, restaurant.getName());
    }

    @Then("the delivery lan receives a  notification with the user informations")
    public void the_delivery_lan_receives_a_notification_with_the_user_informations() {
        String message = String.format("Dear %s,\n\n"
                + "You have a new delivery request for order ID %s.\n"
                + "Delivery location: %s.\n\n"
                + "Please proceed with the delivery.\n\n"
                + "Best regards,\nThe Delivery Team","Albert", orderId, Locations.HALL_PRINCIPAL);

     Assert.assertEquals(notificationCenter.findUser("Albert@gmail.com").getNotifications().get(0).getMessage(),message);
    }

}
