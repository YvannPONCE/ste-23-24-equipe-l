package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ConfirmOrder {

    OrderManager orderManager;
    RestaurantManager restaurantManager;
    UUID order_id;

    @Given("a user {string} added to their cart a {string} at {double} from {string}")
    public void a_user_added_to_their_cart_a_at_from(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        this.order_id = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @When("the user {string} pays their order and payment fails")
    public void the_user_pays_their_order_and_payment_fails(String user_email) {
        orderManager.pay_order(this.order_id, user_email, "7936 3468 9302 8871");
    }
    @Then("the order {string} at {double} from {string} will not be transmitted to the restaurant")
    public void the_order_will_not_be_transmitted_to_the_restaurant(String menuName, Double menuPrice, String restaurantName) {
        // change this to do the opposite
        List<Order> orders = restaurantManager.get_restaurant(restaurantName).getOrders();
        Assert.assertEquals(0, orders.size());
        /*
        Order order = orders.get(0);
        Assert.assertEquals(restaurantName, order.get_restaurant_name());
        List<Menu> menus = order.get_menus();
        Assert.assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        Assert.assertEquals(menuName, menu.get_name());
        Assert.assertEquals(menuPrice, menu.get_price(), 0.01);
         */
    }

    @Given("user {string} added to his cart a {string} at {double} from {string}")
    public void user_added_to_his_cart_a_at_from(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager);

        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);

        this.order_id = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @When("user {string} pay his command")
    public void user_pay_his_command(String user_email) {
        orderManager.pay_order(this.order_id, user_email, "7936 3468 9302 8371");
    }

    @Then("The order {string} at {double} from {string} has been transmit to the restaurant")
    public void the_order_at_from_has_been_transmit_to_the_restaurant(String menuName, double menuPrice, String restaurantName) {
        List<Order> orders = restaurantManager.get_restaurant(restaurantName).getOrders();
        Assert.assertEquals(1, orders.size());
        Order order = orders.get(0);
        Assert.assertEquals(restaurantName, order.get_restaurant_name());
        List<Menu> menus = order.get_menus();
        Assert.assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        Assert.assertEquals(menuName, menu.get_name());
        Assert.assertEquals(menuPrice, menu.get_price(), 0.01);
    }
}
