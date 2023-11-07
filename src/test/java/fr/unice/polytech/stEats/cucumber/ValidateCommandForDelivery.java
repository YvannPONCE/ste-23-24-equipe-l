package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ValidateCommandForDelivery {


    OrderManager orderManager;
    Restaurant mockRestaurant;
    UUID orderId;


    @Given("user {string} as order a {string} at {double} at {string} and as paid his command.")
    public void user_as_order_a_at_at_and_as_paid_his_command(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        RestaurantManager mockRestaurantManager = Mockito.mock(RestaurantManager.class);
        mockRestaurant = Mockito.mock(Restaurant.class);

        Mockito.when(mockRestaurantManager.get_restaurant(Mockito.anyString())).thenReturn(mockRestaurant);
        Mockito.when(mockRestaurant.getName()).thenReturn(restaurant_name);

        orderManager = new OrderManager(mockRestaurantManager);

        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        Mockito.when(mockRestaurant.getOrders()).thenReturn(Arrays.asList(order));

        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
        orderManager.pay_order(orderId, user_email, "7936 3468 9302 8371");
    }
    @When("the restaurant has finish preprared the order")
    public void the_restaurant_has_finish_preprared_the_order() {
        List<Order> orders = mockRestaurant.getOrders();
        orderManager.validate_order(orders.get(0).getId(), mockRestaurant.getName());
    }
    @Then("The status of the order of {string} has change to READY.")
    public void the_status_of_the_order_of_has_change_to_ready(String user_email) {
        List<Order> orders = orderManager.get_current_orders(orderId, user_email);

        Assert.assertEquals(Status.READY, orders.get(0).getStatus());
    }
}
