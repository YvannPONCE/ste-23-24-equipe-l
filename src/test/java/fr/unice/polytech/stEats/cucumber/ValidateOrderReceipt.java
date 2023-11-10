package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class ValidateOrderReceipt {
    OrderManager orderManager;
    Restaurant mockRestaurant;
    UUID orderId;
    UserManager mockuserManager;
    User user;
    private Order order;
    String email;

    @Given("user {string} as order a {string} at {double} at {string} and as paid his command")
    public void user_as_order_a_at_at_and_as_paid_his_command(String string, String string2, Double double1, String string3) {

        RestaurantManager mockRestaurantManager = Mockito.mock(RestaurantManager.class);
        mockRestaurant = Mockito.mock(Restaurant.class);
        mockuserManager=Mockito.mock(UserManager.class);
        email=string;
        Mockito.when(mockRestaurantManager.get_restaurant(Mockito.anyString())).thenReturn(mockRestaurant);
        user=new User(string,"james", Role.CUSTOMER_STUDENT);
        Mockito.when(mockRestaurantManager.get_restaurant(Mockito.anyString())).thenReturn(mockRestaurant);
        Mockito.when(mockuserManager.get_order_history(email)).thenReturn(user.getOrderHistory());
        Mockito.when(mockRestaurant.getName()).thenReturn(string3);

        orderManager = new OrderManager(mockRestaurantManager);
        orderManager.userManager =mockuserManager;

         order = new Order(string3);
        order.add_menu(new Menu(string,double1));
        Mockito.when(mockRestaurant.getOrders()).thenReturn(Arrays.asList(order));

        orderId = orderManager.place_order(string, order, Locations.HALL_PRINCIPAL);

        orderManager.pay_order(orderId, string, "7936 3468 9302 8371");
        orderManager.validate_order(orderId,string);

    }
    @Given("the order was delivered")
    public void the_order_was_delivered() {
        order.setStatus(Status.DELIVERED);

    }

    @When("user {string} confirm the receipt")
    public void user_confirm_the_receipt(String string) {
        orderManager.validate_order_receipt(email, order.getId());
    }

    @Then("the order is marked as delivered")
    public void the_order_is_marked_as_delivered() {
        assertEquals(order.getStatus(),Status.DELIVERED);
    }
}
