package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
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
    private RestaurantManager restaurantManager;

    @Given("The campus user {string} has confirmed receipt of their order")
    public void the_campus_user_has_confirmed_receipt_of_their_order(String email) {
        Restaurant restaurant = new Restaurant("KFC");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        Order order = new Order("KFC");
        order.add_menu(new Menu("Bucket",21));
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        orderManager = new OrderManager(restaurantManager, new UserManager(), statisticsManager);
            orderManager.userManager.add_user(new User(email,"rrr", Role.CUSTOMER_STUDENT));
        orderID = orderManager.place_order(email,order, Locations.HALL_PRINCIPAL);
        orderManager.validate_order(order.getId(),email);
    }

    @Given("The delivery man {string} is assigned to this order")
    public void the_delivery_man_is_assigned_to_this_order(String email) {
        deliveryManager = new DeliveryManager(orderManager, orderManager.userManager);
        deliveryManager.addDeliveryman(email,"deliveryMan");
        deliveryManager.addOrder(orderID);
    }

    @When("The delivery man {string} wants to validate the delivery in turn")
    public void the_delivery_man_wants_to_validate_the_delivery_in_turn(String email) {
        deliveryManager.validateOrder(email,orderID);
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
        assertEquals(deliveryManager.isAvailable(email),true);
    }


}
