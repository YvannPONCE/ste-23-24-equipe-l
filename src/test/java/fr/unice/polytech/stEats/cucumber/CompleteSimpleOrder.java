package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CompleteSimpleOrder {

    UUID orderId;
    OrderManager orderManager;
    RestaurantManager restaurantManager;
    UserManager userManager;
    Restaurant restaurant;
    String user_email;
    User deliveryMan;
    private NotificationCenter notificationCenter;

    DeliveryManager deliveryManager;
    private User user;

    @Given("a restaurant {string} use stEats and a delivery man {string}")
    public void a_restaurant_use_st_eats_and_a_delivery_man(String restaurantName, String deliveryManName) {
        restaurant = new Restaurant(restaurantName);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, notificationCenter);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryMan = new User("delivery@gmail.com", "pass", Role.DELIVER_MAN);
        userManager.addUser(deliveryMan);
        orderManager.addDeliveryManager(deliveryManager);
    }

    @Given("user {string} order a {string} at {string} for {double} euros")
    public void user_order_a_at_for_euros(String userEmail, String menu_name, String restaurant_name, Double menu_price) {
        user=new User(userEmail,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        orderId = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
    }
    @Given("{string} pay his order")
    public void pay_his_order(String userEmail) {
        orderManager.payOrders(userEmail, "7936 3468 9302 8371");
    }
    @Given("The order is marked ready by the restaurant {string}")
    public void the_order_is_marked_ready_by_the_restaurant(String restaurant_name) {
        orderManager.processingOrder(orderId, restaurant_name);
        orderManager.setOrderReady(orderId, restaurant_name);
        Assert.assertEquals(Status.READY, deliveryManager.getOrderStatus(orderId));
    }

    @When("The user {string} confirm the delivery")
    public void the_user_confirm_the_delivery(String string) {
        deliveryManager.validateOrder(deliveryMan.getEmail());
        Assert.assertEquals(Status.DELIVERED, deliveryManager.getOrderStatus(orderId));
    }

    @When("The delivery man {string} confirm the delivery")
    public void the_delivery_man_confirm_the_delivery(String string) {
        deliveryManager.validateOrder(deliveryMan.getEmail());
    }

    @Then("The order is marked as closed")
    public void the_order_is_marked_as_delivered() {
        HashMap<String, List<Order>> orderHistory = userManager.getOrderHistory(user.getEmail());
        assertEquals(Status.CLOSED,orderHistory.get(orderHistory.keySet().iterator().next()).get(0).getOrderState().getStatus());
    }

}
