package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;

import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;
import java.util.UUID;

public class AddMenuToCart {

    OrderManager orderManager = new OrderManager(null, null, null);

    User user;
    UUID order_id;
    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UserManager userManager;

    @Given("One restaurant, One menu and one user {string}")
    public void one_restaurant_one_menu_and_one_user(String user_email) {
        this.user = new User(user_email, user_email, Role.CUSTOMER_STUDENT);
        userManager = new UserManager();

        userManager.add_user(user);
    }

    @When("The user want to add a {string} menu at {double} euros from {string} to his cart")
    public void the_user_want_to_add_a_menu_to_his_cart(String menu_name, double menu_price, String restaurant_name){
        restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager));
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        order_id = orderManager.place_order(user.get_email(), order, Locations.HALL_PRINCIPAL);
    }

    @Then("The {string} menu from {string} is stored in the current order and cost {double} euros.")
    public void the_selected_menus_are_stored_in_the_current_order(String menu_name, String restaurant_name, double menu_price) {
        List<Order>  orders = orderManager.getCurrentOrders(order_id, user.get_email());
        Order order = orders.get(0);
        List<Menu> menus = order.get_menus();
        Menu menu = menus.get(0);

        Assert.assertEquals(1, orders.size());
        Assert.assertEquals(1, menus.size());
        Assert.assertEquals(order.get_restaurant_name(), restaurant_name);
        Assert.assertEquals(menu_name, menu.get_name());
        Assert.assertEquals(menu_price, menu.get_price(), 0.01);
    }


    @Given("Two restaurants, two menus and one user {string}")
    public void two_restaurants_two_menus_and_one_user(String user_email) {
        this.user = new User(user_email, user_email,Role.CUSTOMER_STUDENT);
        userManager = new UserManager();

        userManager.add_user(user);
    }

    @When("The user want to add a {string} menu at {double} euros from {string} to his cart and a {string} menu at {double} euros from {string} to his cart")
    public void the_user_want_to_add_a_menu_at_euros_from_to_his_cart_and_a_menu_at_euros_from_to_his_cart(String menu_name, double menu_price, String restaurant_name, String menu_name_2, double menu_price_2, String restaurant_name_2) {
        restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager));
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price));
        order_id = orderManager.place_order(user.get_email(), order, Locations.HALL_PRINCIPAL);

        Order order_2 = new Order(restaurant_name_2);
        order_2.add_menu(new Menu(menu_name_2, menu_price_2));
        orderManager.place_order(user.get_email(), order_2, Locations.HALL_PRINCIPAL, order_id);

    }

    @Then("The {string} menu from {string} is stored in the current order and cost {double} euros as well as the {string} menu at {double} euros from {string}.")
    public void the_menu_from_is_stored_in_the_current_order_and_cost_euros_as_well_as_the_menu_at_euros_from(String menu_name, String restaurant_name, double menu_price, String menu_name_2, double menu_price_2, String restaurant_name_2) {
        List<Order>  orders = orderManager.getCurrentOrders(order_id, user.get_email());

        Order order = orders.get(0);
        List<Menu> menus_1 = order.get_menus();
        Menu menu_1 = menus_1.get(0);

        Order order_2 = orders.get(1);
        List<Menu> menus_2 = order_2.get_menus();
        Menu menu_2 = menus_2.get(0);

        Assert.assertEquals(2, orders.size());
        Assert.assertEquals(1, menus_1.size());
        Assert.assertEquals(restaurant_name, order.get_restaurant_name());
        Assert.assertEquals(menu_name, menu_1.get_name());
        Assert.assertEquals(menu_price, menu_1.get_price(), 0.01);

        Assert.assertEquals(restaurant_name_2, order_2.get_restaurant_name());
        Assert.assertEquals(menu_name_2, menu_2.get_name());
        Assert.assertEquals(menu_price_2, menu_2.get_price(), 0.01);
    }



    @Given("One restaurant, two menus and one user {string}")
    public void one_restaurant_two_menus_and_one_user(String user_email) {
        this.user = new User(user_email, user_email,Role.CUSTOMER_STUDENT);
        userManager = new UserManager();

        userManager.add_user(user);
    }
    @When("The user want to add a {string} menu at {double} euros from {string} to his cart and a {string} menu at {double} euros to his cart")
    public void the_user_want_to_add_a_menu_at_euros_from_to_his_cart_and_a_menu_at_euros_to_his_cart(String menu_name_1, Double menu_price_1, String restaurant_name, String menu_name_2, Double menu_price_2) {
        restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager) {
        });
        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name_1, menu_price_1));
        order_id = orderManager.place_order(user.get_email(), order, Locations.HALL_PRINCIPAL);

        Order order_2 = new Order(restaurant_name);
        order_2.add_menu(new Menu(menu_name_2, menu_price_2));
        orderManager.place_order(user.get_email(), order_2, Locations.HALL_PRINCIPAL, order_id);
    }
    @Then("The {string} menu from {string} is stored in the current order and cost {double} euros as well as the {string} menu at {double} euros.")
    public void the_menu_from_is_stored_in_the_current_order_and_cost_euros_as_well_as_the_menu_at_euros(String menu_name_1, String restaurant_name, Double menu_price_1, String menu_name_2, Double menu_price_2) {
        List<Order>  orders = orderManager.getCurrentOrders(order_id, user.get_email());

        Order order = orders.get(0);
        List<Menu> menus = order.get_menus();
        Menu menu_1 = menus.get(0);
        Menu menu_2 = menus.get(1);


        Assert.assertEquals(1, orders.size());
        Assert.assertEquals(2, menus.size());
        Assert.assertEquals(order.get_restaurant_name(), restaurant_name);

        Assert.assertEquals(menu_name_1, menu_1.get_name());
        Assert.assertEquals(menu_price_1, menu_1.get_price(), 0.01);

        Assert.assertEquals(menu_name_2, menu_2.get_name());
        Assert.assertEquals(menu_price_2, menu_2.get_price(), 0.01);
    }




}
