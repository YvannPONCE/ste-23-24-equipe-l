package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.UUID;

public class UserNotificationdefs {

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private User user;
    private Object email;
    private Order order;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private UUID orderId;

    @Given("user {string} ordered from {string} a {string} menu")
    public void user_ordered_from_a_menu(String string, String string2, String string3) {
        userManager = new UserManager();
        restaurant = new Restaurant(string2 );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(string,"ggg",Role.CUSTOMER_STAFF);
        email=string;
        order = new Order(string2);
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), notificationCenter);

        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        userManager.add_user(user);
        userManager.addUser( new User("Albert@gmail.com","Albert", Role.DELIVER_MAN));
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu(string3, 5.50));
        orderId = orderManager.placeOrder((String) email, order, Locations.HALL_PRINCIPAL);

        orderManager.setOrderReady(orderId, "chickenTacky");
    }
    @When("the order is validated")
    public void the_order_is_validated() {
        User user1=new User("gmail.com","rrr",Role.CUSTOMER_STAFF);
    }
    @Then("user receives a notification with the order information")
    public void user_receives_a_notification_with_the_order_information() {
        GroupOrder groupOrder = orderManager.getCurrentOrders(orderId);
        String message= String.format("Dear %s,\n\nThank you for placing an order with order ID %s. Your order for delivery to %s on %s has been confirmed.\n\nBest regards,\nThe Order Confirmation Team",
                user.getEmail(),orderId.toString(), Locations.HALL_PRINCIPAL, groupOrder.getDeliveryTime());
        Assert.assertEquals(user.getNotifications().get(0).getMessage(),message);


      
    }
    @Given("registred user  {string} ordered from {string} and")
    public void registred_user_ordered_from_and(String string, String string2) {
        restaurant = new Restaurant(string2 );
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(string,"ggg",Role.CUSTOMER_STAFF);
        email=string;
        order = new Order(string2);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), notificationCenter);

        deliveryManager = new DeliveryManager(userManager, notificationCenter);orderManager.userManager.add_user(user);
        userManager.addUser( new User("Albert@gmail.com","Albert", Role.DELIVER_MAN));
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu("chickennuggets", 5.50));
        orderId = orderManager.placeOrder((String) email, order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders((String) email,"7936 3468 9302 8371");


    }


    @When("the order is ready for delivery")
    public void the_order_is_ready_for_delivery() {
        orderManager.processingOrder(orderId,"chickenTacky");
        orderManager.setOrderReady(orderId, "chickenTacky");

    }
    @Then("user receives a notification with delivery information")
    public void user_receives_a_notification_with_delivery_information() {
        String userMessage = String.format("Dear Customer,\n\n"
                + "Good news! Your order with ID %s is now ready for delivery.\n"
                + "Our delivery team is on the way to your location at %s, and your assigned delivery person is %s.\n"
                + "You can expect your delivery soon.\n\n"
                + "Thank you for choosing our services!\n\n"
                + "Best regards,\nThe Delivery Team", orderId, Locations.HALL_PRINCIPAL, "Albert@gmail.com");
      Assert.assertEquals(userMessage, user.getNotifications().get(1).getMessage());
    }


    @Given("user {string} ordered from {string} and the order was delivered")
    public void user_ordered_from_and_the_order_was_delivered(String userEmail, String restaurantName) {
        userManager = new UserManager();
        User deliveryMan = new User("deliveryMan", "password", Role.DELIVER_MAN);
        userManager.add_user(deliveryMan);
        notificationCenter = new NotificationCenter(userManager);
        restaurant = new Restaurant(restaurantName );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user=new User(userEmail,"ggg",Role.CUSTOMER_STAFF);
        email=userEmail;
        order = new Order(restaurantName);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), notificationCenter);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);orderManager.userManager.add_user(user);
        userManager.addUser( new User("Albert@gmail.com","Albert", Role.DELIVER_MAN));
        orderManager.addDeliveryManager(deliveryManager);
        this.notificationCenter=new NotificationCenter(orderManager.userManager);


        order.add_menu(new Menu("chickennuggets", 5.50));
        orderId = orderManager.placeOrder((String) email, order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders(userEmail, "7936 3468 9302 8371");
        orderManager.processingOrder(orderId,"chickenTacky");
        orderManager.setOrderReady(orderId,"chickenTacky");

    }
    @When("user validate  order  receipt")
    public void user_validate_order_receipt() {
    deliveryManager.validateOrder("deliveryMan");
    deliveryManager.validateOrder(orderId);
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





