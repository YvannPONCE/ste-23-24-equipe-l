package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.RestaurantManager.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CompleteMultiOrder {

    private UUID orderId;
    private OrderManager orderManager;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private DeliveryManager deliveryManager;
    private User user;

    @Given("restaurants {string} and {string} use st-eats and one delivery man {string}")
    public void restaurants_and_use_st_eats_and_one_delivery_man(String restaurantName1, String restaurantName2, String deliveryManName) {
        restaurant1 = new Restaurant(restaurantName1);
        restaurant2 = new Restaurant(restaurantName2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant1);
        restaurantManager.add_restaurant(restaurant2);
        userManager = new UserManager();
        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
        deliveryManager = new DeliveryManager(orderManager, orderManager.userManager);
        deliveryManager.addDeliveryman(deliveryManName,"albert");
        orderManager.addDeliveryManager(deliveryManager);

    }
    @Given("the user {string} order a {string} at {string} for {double} euros")
    public void the_user_order_a_at_for_euros(String userEmail, String menuName, String restaurantName, Double menuPrice) {
        userManager.add_user( new User(userEmail,userEmail, Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurantName);
        Menu menu = new Menu(menuName, menuPrice);
        order.add_menu(menu);

        orderId = orderManager.place_order(userEmail, order, Locations.HALL_PRINCIPAL);
    }

    @Given("the user {string} order a {string} at {string} for {double} euros on his friend order")
    public void the_user_order_a_at_for_euros_on_his_friend_order(String userEmail, String menuName, String restaurantName, Double menuPrice) {
        userManager.add_user( new User(userEmail,userEmail, Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurantName);
        Menu menu = new Menu(menuName, menuPrice);
        order.add_menu(menu);

        orderManager.place_order(userEmail, order, Locations.HALL_PRINCIPAL, this.orderId);
    }

    @Given("the user {string} pay his order")
    public void the_user_pay_his_order(String userEmail) {
        orderManager.pay_order(this.orderId, userEmail, "7936 3468 9302 8371");
        Assert.assertNotEquals(Status.PAID, orderManager.get_current_orders(this.orderId));
    }
    @Given("the user {string} pay his order in second")
    public void the_user_pay_his_order_in_second(String userEmail) {
        orderManager.pay_order(this.orderId, userEmail, "7936 3468 9302 8371");
        Assert.assertEquals(Status.PAID, orderManager.get_current_orders(this.orderId).getOrderStatus());
    }
    @Given("The simple order is marked ready by the restaurant {string}")
    public void the_simple_order_is_marked_ready_by_the_restaurant(String restaurantName) {
        orderManager.validate_order(orderId, restaurantName);
        Assert.assertNotEquals(Status.READY, orderManager.get_current_orders(this.orderId).getOrderStatus());
    }
    @Given("The simple order is marked ready by the restaurant {string} in second")
    public void the_simple_order_is_marked_ready_by_the_restaurant_in_second(String restaurantName) {
        orderManager.validate_order(orderId, restaurantName);
        Assert.assertEquals(Status.READY, orderManager.get_current_orders(this.orderId).getOrderStatus());
    }
    @When("user {string} confirm the delivery")
    public void user_confirm_the_delivery(String userEmail) {
        orderManager.validate_order_receipt(orderId);
        Assert.assertEquals(Status.DELIVERED, orderManager.get_current_orders(this.orderId).getOrderStatus());
    }
    @When("delivery man {string} confirm the delivery")
    public void delivery_man_confirm_the_delivery(String deliveryManName) {
        deliveryManager.validateOrder(deliveryManName,orderId);
    }
    @Then("The group order is marked as closed")
    public void the_group_order_is_marked_as_closed() {
        assertEquals(Status.CLOSED,orderManager.get_current_orders(orderId).getOrderStatus());
    }
}
