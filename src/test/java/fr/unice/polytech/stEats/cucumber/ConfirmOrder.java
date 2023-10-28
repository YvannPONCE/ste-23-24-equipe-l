package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import io.cucumber.java.da.Men;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.internal.matchers.Or;

import java.util.List;
import java.util.UUID;

public class ConfirmOrder {

    RestaurantManager restaurantManager;
    OrderManager orderManager;
    UUID orderId;


    @Given("user {string} added to his cart a {string} at {double} from {string}")
    public void user_added_to_his_cart_a_at_from(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        this.orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @When("user {string} pay his command")
    public void user_pay_his_command(String user_email) {
        orderManager.pay_order(this.orderId, user_email);
    }
    @Then("The order {string} at {double} from {string} has been transmit to the restaurant")
    public void the_order_at_from_has_been_transmit_to_the_restaurant(String restaurantName, String menuName, double menuPrice) {
        List<Order> orders = restaurantManager.get_restaurant(restaurantName).getOrders();
        Assert.assertEquals(1, orders.size());
        Order order = orders.get(0);
        Assert.assertEquals(restaurantName, order.get_restaurant_name());
        List<Menu> menus = order.get_menus();
        Assert.assertEquals(2, menus.size());
        Menu menu = menus.get(0);
        Assert.assertEquals(menuName, menu.get_name());
        Assert.assertEquals(menuPrice, menu.get_price());
    }
}
