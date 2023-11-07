package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.UUID;

public class CompleteSimpleOrder {

    UUID orderId;
    OrderManager orderManager;
    RestaurantManager restaurantManager;
    Restaurant restaurant;

    @Given("user {string} order a {string} at {string} for {double} euros")
    public void user_order_a_at_for_euros(String user_email, String menu_name, String restaurant_name, Double menu_price) {
        restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @Given("{string} pay his order")
    public void pay_his_order(String user_email) {
        orderManager.pay_order(this.orderId, user_email);
    }
    @Given("The order is marked ready by the restaurant {string}")
    public void the_order_is_marked_ready_by_the_restaurant(String restaurant_name) {
        orderManager.validate_order(orderId, restaurant_name);
    }
    @When("The delivery man deliver the order, {string} confirm the delivery")
    public void the_delivery_man_deliver_the_order_confirm_the_delivery(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("The order is marked as delivered")
    public void the_order_is_marked_as_delivered() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
