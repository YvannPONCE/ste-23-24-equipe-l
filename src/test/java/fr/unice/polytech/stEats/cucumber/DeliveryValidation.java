package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
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
        BusinessIntelligence businessIntelligence = new BusinessIntelligence(restaurantManager);
        orderManager = new OrderManager(restaurantManager, new UserManager(), businessIntelligence);

        orderID = orderManager.place_order(email,order, Locations.HALL_PRINCIPAL);
        orderManager.validate_order(order.getId(),email);
    }

    @Given("The delivery man {string} is assigned to this order")
    public void the_delivery_man_is_assigned_to_this_order(String email) {
        deliveryManager = new DeliveryManager(orderManager);
        deliveryManager.addDeliveryman(email);
        deliveryManager.addOrder(orderID);
    }

    @When("The delivery man {string} wants to validate the delivery in turn")
    public void the_delivery_man_wants_to_validate_the_delivery_in_turn(String email) {
        deliveryManager.validateOrder(email,orderID);
    }

    @Then("The order statue of {string} updates as closed")
    public void the_order_statue_updates_as_closed(String email) {
        GroupOrder groupOrder = orderManager.get_current_orders(orderID);
         for (Order order : orderManager.get_current_orders(orderID,email)) {
                assertEquals(Status.CLOSED, order.getStatus());
            }
    }

    @Then("The delivery man {string} become available for an new delivery")
    public void the_delivery_man_become_available_for_an_new_delivery(String email) {
        assertEquals(deliveryManager.isAvailable(email),true);
    }


}
