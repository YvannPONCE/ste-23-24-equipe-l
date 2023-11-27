package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.UUID;

public class UserNotificationdefs {

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private User user;
    private Object email;
    private Order order;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private UUID orderId;

    @Given("user {string} ordered from {string} a {string} menu")
    public void user_ordered_from_a_menu(String string, String string2, String string3) {
        restaurant = new Restaurant(string2 );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(string,"ggg",Role.CUSTOMER_STAFF);
        email=string;
        order = new Order(string2);
        orderManager = new OrderManager(restaurantManager, new UserManager(), new StatisticsManager(restaurantManager));

        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager );orderManager.userManager.add_user(user);
        deliveryManager.addDeliveryman("Albert@gmail.com","Albert");
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu(string3, 5.50));
        orderId = orderManager.place_order((String) email, order, Locations.HALL_PRINCIPAL);

        orderManager.validate_order(orderId, "chickenTacky");
    }
    @When("the order is validated")
    public void the_order_is_validated() {
        User user1=new User("gmail.com","rrr",Role.CUSTOMER_STAFF);
    }
    @Then("user receives a notification with the order information")
    public void user_receives_a_notification_with_the_order_information() {
        String message= String.format("Dear %s,\n\nThank you for placing an order with order ID %s. Your order for delivery to %s on %s has been confirmed.\n\nBest regards,\nThe Order Confirmation Team",
                user.get_email(),orderId.toString(), Locations.HALL_PRINCIPAL, order.getCreationTime());
        Assert.assertEquals(user.getNotifications().get(0).getMessage(),message);


      
    }
    @Given("registred user  {string} ordered from {string} and")
    public void registred_user_ordered_from_and(String string, String string2) {
        restaurant = new Restaurant(string2 );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(string,"ggg",Role.CUSTOMER_STAFF);
        email=string;
        order = new Order(string2);
        orderManager = new OrderManager(restaurantManager, new UserManager(), new StatisticsManager(restaurantManager));

        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager );orderManager.userManager.add_user(user);
        deliveryManager.addDeliveryman("Albert@gmail.com","Albert");
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu("chickennuggets", 5.50));
        orderId = orderManager.place_order((String) email, order, Locations.HALL_PRINCIPAL);


    }


    @When("the order is ready for delivery")
    public void the_order_is_ready_for_delivery() {
        orderManager.validate_order(orderId, "chickenTacky");
    }
    @Then("user receives a notification with delivery information")
    public void user_receives_a_notification_with_delivery_information() {
        String userMessage = String.format("Dear Customer,\n\n"
                + "Good news! Your order with ID %s is now ready for delivery.\n"
                + "Our delivery team is on the way to your location at %s, and your assigned delivery person is %s.\n"
                + "You can expect your delivery soon.\n\n"
                + "Thank you for choosing our services!\n\n"
                + "Best regards,\nThe Delivery Team", orderId, Locations.HALL_PRINCIPAL, "Albert");
      Assert.assertEquals(user.getNotifications().get(1).getMessage(),userMessage);
    }


    @Given("user {string} ordered from {string} and the order was delivered")
    public void user_ordered_from_and_the_order_was_delivered(String string, String string2) {
        restaurant = new Restaurant(string2 );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(string,"ggg",Role.CUSTOMER_STAFF);
        email=string;
        order = new Order(string2);
        orderManager = new OrderManager(restaurantManager, new UserManager(), new StatisticsManager(restaurantManager));
        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager );orderManager.userManager.add_user(user);
        deliveryManager.addDeliveryman("Albert@gmail.com","Albert");
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu("chickennuggets", 5.50));
        orderId = orderManager.place_order((String) email, order, Locations.HALL_PRINCIPAL);

        orderManager.validate_order(orderId, "chickenTacky");
        orderManager.pay_order(orderId, string, "7936 3468 9302 8371");

    }
    @When("user validate  order  receipt")
    public void user_validate_order_receipt() {
    orderManager.validate_order_receipt(orderId);
    }
    @Then("user receives a notificatin")
    public void user_receives_a_notificatin() {
        String notification = String.format("Dear %s,\n\n"
                + "Your order (ID: %s) has been successfully delivered to the following location:\n"
                + "%s.\n\n"
                + "Thank you for choosing our service!\n\n"
                + "Best regards,\nThe Delivery Team", email, orderId,Locations.HALL_PRINCIPAL);
        Assert.assertEquals(user.getNotifications().get(2).getMessage(),notification);


    }
    }





