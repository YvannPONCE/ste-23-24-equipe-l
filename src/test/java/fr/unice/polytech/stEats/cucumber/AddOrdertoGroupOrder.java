package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import io.cucumber.java.bs.A;
import io.cucumber.java.da.Men;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class AddOrdertoGroupOrder {

    OrderManager orderManager;
    User user1;
    User user2;
    UUID order_id;

    @Given("One restaurant, One menu, two users {string} and {string}")
    public void one_restaurant_one_menu_two_users_and(String user_email_1, String user_email_2) {
        BusinessIntelligence businessIntelligence = new BusinessIntelligence(new RestaurantManager());
        orderManager = new OrderManager(new RestaurantManager(), new UserManager(), businessIntelligence);
        user1 = new User(user_email_1, user_email_1, Role.CUSTOMER_STUDENT);
        user2 = new User(user_email_2, user_email_2, Role.CUSTOMER_STUDENT);

    }
    @When("The first user add a {string} menu at {double} euros from {string} to deliver at {string}")
    public void the_first_user_add_a_menu_at_euros_from_mcdonald(String menu_name, Double menu_price, String restaurant_name, String delivery_location) {
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        order_id = orderManager.place_order(user1.get_email(), order, Locations.HALL_PRINCIPAL);
    }
    @When("The second user add a {string} menu at {double} euros from {string}")
    public void the_second_user_add_a_menu_at_euros_from_mcdonald(String menu_name, Double menu_price, String restaurant_name) {
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        orderManager.place_order(user2.get_email(), order, Locations.HALL_PRINCIPAL, order_id);
    }
    @Then("Two {string} menu from {string} are stored in the current order and cost {double} euros each.")
    public void two_menu_from_are_stored_in_the_current_order_and_cost_euros_each(String menu_name, String restaurant_name, double menu_price) {
        List<Order> user_1_orders = orderManager.get_current_orders(order_id, user1.get_email());
        List<Order> user_2_orders = orderManager.get_current_orders(order_id, user2.get_email());

        Assert.assertEquals(1, user_1_orders.size());
        Assert.assertEquals(1, user_2_orders.size());

        Order user_1_order = user_1_orders.get(0);
        Order user_2_order = user_2_orders.get(0);

        Assert.assertEquals(restaurant_name, user_1_order.get_restaurant_name());
        Assert.assertEquals(restaurant_name, user_2_order.get_restaurant_name());

        List<Menu> user_1_menus = user_1_order.get_menus();
        List<Menu> user_2_menus = user_2_order.get_menus();

        Menu user_1_menu = user_1_menus.get(0);
        Menu user_2_menu = user_2_menus.get(0);

        Assert.assertEquals(1, user_1_menus.size());
        Assert.assertEquals(1, user_2_menus.size());

        Assert.assertEquals(menu_name, user_1_menu.get_name());
        Assert.assertEquals(menu_name, user_2_menu.get_name());

        Assert.assertEquals(menu_price, user_1_menu.get_price(), 0.01);
        Assert.assertEquals(menu_price, user_2_menu.get_price(), 0.01);

    }



    @Given("One restaurant, two menu, two users {string} and {string} waiting in {string}")
    public void one_restaurant_two_menu_two_users_and(String user_email_1, String user_email_2, String delivery_location) {
        BusinessIntelligence businessIntelligence = new BusinessIntelligence(new RestaurantManager());
        orderManager = new OrderManager(new RestaurantManager(), new UserManager(), businessIntelligence);
        user1 = new User(user_email_1, user_email_1, Role.CUSTOMER_STUDENT);
        user2 = new User(user_email_2, user_email_2, Role.CUSTOMER_STUDENT);
    }

    @When("The second join a {string} menu at {double} euros from {string} to his friend command")
    public void the_second_join_a_menu_at_euros_from(String menu_name, Double menu_price, String restaurant_name) {
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        orderManager.place_order(user2.get_email(), order, Locations.HALL_PRINCIPAL, order_id);
    }

    @Then("Both users can see {string} and {string} menus in the cart at {double} and {double} delivered to {string}.")
    public void both_users_can_see_and_menus_in_the_cart(String menu_user_1, String menu_user_2, double user_1_menu_price, double user_2_menu_price, String delivery_location) {
        GroupOrder group_order = orderManager.get_current_orders(order_id);

        Assert.assertEquals(Locations.HALL_PRINCIPAL, group_order.get_delivery_location());

        List<Order> user_1_orders = group_order.get_orders(user1.get_email());
        List<Order> user_2_orders = group_order.get_orders(user2.get_email());

        Order user_1_order = user_1_orders.get(0);
        Order user_2_order = user_2_orders.get(0);

        List<Menu> user_1_menus = user_1_order.get_menus();
        List<Menu> user_2_menus = user_2_order.get_menus();

        Menu user_1_menu = user_1_menus.get(0);
        Menu user_2_menu = user_2_menus.get(0);

        Assert.assertEquals(1, user_1_menus.size());
        Assert.assertEquals(1, user_2_menus.size());

        Assert.assertEquals(menu_user_1, user_1_menu.get_name());
        Assert.assertEquals(menu_user_2, user_2_menu.get_name());

        Assert.assertEquals(user_1_menu_price, user_1_menu.get_price(), 0.01);
        Assert.assertEquals(user_2_menu_price, user_2_menu.get_price(), 0.01);
    }

}
