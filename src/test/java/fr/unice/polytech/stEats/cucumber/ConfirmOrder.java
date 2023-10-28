package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.da.Men;
import org.junit.Assert;
import org.mockito.internal.matchers.Or;

import java.util.List;
import java.util.UUID;

public class ConfirmOrder {

    OrderManager orderManager;
    RestaurantManager restaurantManager;
    User user;
    UUID order_id;
    PaymentSystem paymentSystem;

    @Given("the cart")
    public void the_cart(Order order) {
        String user_email = "user@user.com";
        this.user = new User(user_email, user_email, Role.CUSTOMER_STUDENT);
        throw new io.cucumber.java.PendingException();
    }

    @When("The user wants to confirm his order")
    public void the_user_wants_to_confirm_his_order() {
        // Write code here that turns the phrase above into concrete actions
        Order order = new Order("restaurant_name");
        order.add_menu(new Menu("BoxMain", 9999999));
        order_id = orderManager.place_order(user.get_email(), order, Locations.HALL_PRINCIPAL);
        throw new io.cucumber.java.PendingException();
    }

    @When("The user pays for his order")
    public void the_user_pays_for_his_order() {
        boolean paid = paymentSystem.pay("7936 3468 9302 8371", "john quinonas", "12/27", "123");
        throw new io.cucumber.java.PendingException();
    }

    @When("The payment is successful")
    public void the_payment_is_successful() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The status of the order {string} passes from Processing to paid")
    public void the_status_of_the_order_passes_from_processing_to_paid(String string) {
        List<Order> ordr = orderManager.get_current_orders(order_id, "user@user.user");
        ordr.get(0).setStatus(Status.PAID);
        throw new io.cucumber.java.PendingException();
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
        orderManager.pay_order(this.order_id, user_email);
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
