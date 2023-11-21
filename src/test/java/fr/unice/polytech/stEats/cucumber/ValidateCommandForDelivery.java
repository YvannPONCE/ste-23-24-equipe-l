package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

public class ValidateCommandForDelivery {


    OrderManager orderManager;
    DeliveryManager deliveryManager;
    UUID orderId;

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;


    @Given("user {string} as order a {string} at {double} at {string} and as paid his command.")
    public void user_as_order_a_at_at_and_as_paid_his_command(String user_email, String menu_name, Double menu_price, String restaurant_name) {

        restaurant = new Restaurant(restaurant_name );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);



        orderManager = new OrderManager(restaurantManager, new UserManager(), new BusinessIntelligence(restaurantManager));
        orderManager.userManager.add_user(new User(user_email,"rrr", Role.CUSTOMER_STUDENT));
        deliveryManager = new DeliveryManager(orderManager, orderManager.userManager);
        orderManager.addDeliveryManager(deliveryManager);



        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));

        deliveryManager.addDeliveryman("Albert@gmail.com","Albert");
        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
        orderManager.pay_order(orderId, user_email, "7936 3468 9302 8371");
    }
    @When("the restaurant has finish preprared the order")
    public void the_restaurant_has_finish_preprared_the_order() {
        orderManager.validate_order(orderId, restaurant.getName());
    }
    @Then("The status of the order of {string} has change to READY.")
    public void the_status_of_the_order_of_has_change_to_ready(String user_email) {
        List<Order> orders = orderManager.get_current_orders(orderId, user_email);

        Assert.assertEquals(Status.READY, orders.get(0).getStatus());
    }
}
