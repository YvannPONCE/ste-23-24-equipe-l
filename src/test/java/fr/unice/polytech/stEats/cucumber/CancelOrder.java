package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;


public class CancelOrder {

    private OrderManager orderManager;
    private UserManager userManager;
    User user;
    Double menuPrice;
    UUID orderId;
    private Restaurant restaurant;
    private RestaurantManager restaurantManager;

    @Given("{string} is a customer")
    public void is_a_customer(String userEmail) {
        userManager=new UserManager();
        user = new User(userEmail, userEmail);
        userManager.add_user(user);
        restaurantManager = new RestaurantManager();
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager));
    }
    @Given("{string} has placed an order with {string} at {double} euros from {string}")
    public void has_placed_an_order_with_at_euros_from(String userEmail, String menuName, Double menuPrice, String restaurantName) {
        restaurant = new Restaurant(restaurantName);
        restaurant.setCapacity(10);
        restaurantManager.add_restaurant(restaurant);
        this.menuPrice = menuPrice;

        Menu menu = new Menu(menuName, menuPrice);
        Order order = new Order(restaurantName);
        order.add_menu(menu);

        orderId = orderManager.place_order(userEmail, order, Locations.HALL_PRINCIPAL);
       order.getOrderState().next();

    }
    @When("I request to cancel my order")
    public void i_request_to_cancel_my_order() {
        orderManager.cancelOrder(orderId, user.getEmail());
    }
    @Then("my order is successfully cancelled")
    public void my_order_is_successfully_cancelled() {
        List<Order> orders = orderManager.getCurrentOrders(orderId, user.getEmail());
        Assert.assertFalse(orders.isEmpty());
        for(Order order : orders){
            Assert.assertEquals(Status.CANCELED, order.getOrderState().getStatus());
        }

    }
    @Then("I am refunded for my cancelled order")
    public void i_am_refunded_for_my_cancelled_order() {
        User finalUser = userManager.get_user(user.getEmail());
        Assert.assertEquals(menuPrice ,user.getCredit());
    }

    @Then("the cancellation is denied")
    public void the_cancellation_is_denied() {
        orderManager.reprocessingOrder(orderId, restaurant.getName());
    }

    @Then("my order remains unchanged")
    public void my_order_remains_unchanged() {
        List<Order> orders = orderManager.getCurrentOrders(orderId, user.getEmail());
        Assert.assertFalse(orders.isEmpty());
        for(Order order : orders){
            Assert.assertEquals(Status.PROCESSING, order.getOrderState().getStatus());
        }
    }

}
