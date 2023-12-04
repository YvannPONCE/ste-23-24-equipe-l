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

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DeliveryValidation {

    OrderManager orderManager;
    UUID orderID;
    DeliveryManager deliveryManager;
    UserManager userManager;
    private RestaurantManager restaurantManager;
    private Restaurant restaurant;
    private NotificationCenter notificationCenter;

    @Given("The campus user {string} has ordered")
    public void the_campus_user_has_ordered(String email) {
        userManager = new UserManager();
        restaurant = new Restaurant("KFC");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        Order order = new Order(restaurant.getName());
        order.add_menu(new Menu("Bucket",21));
        notificationCenter = new NotificationCenter(userManager);
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, notificationCenter);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
        userManager.add_user(new User(email,"rrr", Role.CUSTOMER_STUDENT));
        orderID = orderManager.placeOrder(email,order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders( email, "7936 3468 9302 8371");
        orderManager.processingOrder(orderID, restaurant.getName());
    }

    @Given("The delivery man {string} is assigned to this order")
    public void the_delivery_man_is_assigned_to_this_order(String email) {
        userManager.addUser(new User(email, email, Role.DELIVER_MAN));
        orderManager.setOrderReady(orderID, restaurant.getName());
    }

    @When("The user validate the order")
    public void the_user_validate_the_order(){
        deliveryManager.validateOrder(orderID);
    }

    @Then("The order statue of {string} updates as closed")
    public void the_order_statue_updates_as_closed(String email) {
        GroupOrder groupOrder = orderManager.getCurrentOrders(orderID);
         for (Order order : orderManager.getCurrentOrders(orderID,email)) {
                assertEquals(Status.CLOSED, order.getOrderState().getStatus());
            }
    }

    @Then("The delivery man {string} become available for an new delivery")
    public void the_delivery_man_become_available_for_an_new_delivery(String email) {
        assertEquals(true, deliveryManager.getDeliveryMenAvailability().get(email));
    }


}
