package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CompleteSimpleOrder {

    UUID orderId;
    OrderManager orderManager;
    RestaurantManager restaurantManager;
    UserManager userManager;
    Restaurant restaurant;
    String user_email;

    DeliveryManager deliveryManager;
    private User user;

    @Given("a restaurant {string} use stEats and a delivery man {string}")
    public void a_restaurant_use_st_eats_and_a_delivery_man(String restaurantName, String deliveryManName) {
        restaurant = new Restaurant(restaurantName);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        userManager = new UserManager();
        BusinessIntelligence businessIntelligence = new BusinessIntelligence(restaurantManager);
        orderManager = new OrderManager(restaurantManager, userManager, businessIntelligence);
        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager);
        deliveryManager.addDeliveryman(deliveryManName,"delivery@gmail.com");
        orderManager.addDeliveryManager(deliveryManager);
    }

    @Given("user {string} order a {string} at {string} for {double} euros")
    public void user_order_a_at_for_euros(String user_email, String menu_name, String restaurant_name, Double menu_price) {
        this.user_email = user_email;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @Given("{string} pay his order")
    public void pay_his_order(String user_email) {
        orderManager.pay_order(this.orderId, user_email, "7936 3468 9302 8371");
    }
    @Given("The order is marked ready by the restaurant {string}")
    public void the_order_is_marked_ready_by_the_restaurant(String restaurant_name) {
        orderManager.validate_order(orderId, restaurant_name);
    }

    @When("The user {string} confirm the delivery")
    public void the_user_confirm_the_delivery(String string) {
        orderManager.validate_order_receipt(orderId);
    }

    @When("The delivery man {string} confirm the delivery")
    public void the_delivery_man_confirm_the_delivery(String string) {
        deliveryManager = new DeliveryManager(orderManager,orderManager.userManager );
        deliveryManager.addDeliveryman(string,"delivery");
        deliveryManager.addOrder(orderId);
        deliveryManager.validateOrder(string,orderId);
    }

    @Then("The order is marked as closed")
    public void the_order_is_marked_as_delivered() {
        assertEquals(Status.CLOSED,orderManager.get_current_orders(orderId).getOrderStatus());
    }

}
