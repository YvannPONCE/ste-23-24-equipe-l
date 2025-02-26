package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.OrderManager.OrderManagerStaff;
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

public class ConfirmOrder {

    OrderManager orderManager;
    RestaurantManager restaurantManager;
    NotificationCenter notificationCenter;
    UserManager userManager;
    UUID order_id;
    UUID order_id_1;
    UUID order_id_2;

    @Given("a user {string} added to their cart a {string} at {double} from {string}")
    public void a_user_added_to_their_cart_a_at_from(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, null, notificationCenter);
        orderManager.userManager.add_user(new User(user_email,"rrr", Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price, MenuType.BASIC_MENU);
        order.add_menu(menu);

        this.order_id = orderManager.placeOrder(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @When("the user {string} pays their order and payment fails")
    public void the_user_pays_their_order_and_payment_fails(String user_email) {
        orderManager.payOrders(user_email, "7936 3468 9302 8871");
    }
    @Then("the order {string} at {double} from {string} will not be transmitted to the restaurant")
    public void the_order_will_not_be_transmitted_to_the_restaurant(String menuName, Double menuPrice, String restaurantName) {
        OrderManagerStaff orderManagerStaff = orderManager;
        List<Order> orders = orderManagerStaff.getCurrentOrders(restaurantName);
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
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, notificationCenter);
        orderManager.userManager.add_user(new User(user_email,"rrr", Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price, MenuType.BASIC_MENU);
        order.add_menu(menu);

        this.order_id = orderManager.placeOrder(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @When("user {string} pay his command")
    public void user_pay_his_command(String user_email) {
        orderManager.payOrders(user_email, "7936 3468 9302 8371");
    }

    @Then("The order {string} at {double} from {string} has been transmit to the restaurant")
    public void the_order_at_from_has_been_transmit_to_the_restaurant(String menuName, double menuPrice, String restaurantName) {

        List<Order> orders = orderManager.getCurrentOrders(restaurantName);
        Assert.assertEquals(1, orders.size());
        Order order = orders.get(0);
        Assert.assertEquals(restaurantName, order.getRestaurantName());
        List<Menu> menus = order.getMenus();
        Assert.assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        Assert.assertEquals(menuName, menu.getItemName());
        Assert.assertEquals(menuPrice, menu.getPrice(), 0.01);
    }

    @Given("a user {string} added to his cart a {string} at {double} from {string} and {string} at {double} from {string}")
    public void a_user_added_to_his_cart_a_at_from_and_at_from(String user_email, String menu_name_1, Double menu_price_1, String restaurant_name_1, String menu_name_2, Double menu_price_2, String restaurant_name_2) {
        Restaurant restaurant1 = new Restaurant(restaurant_name_1);
        Restaurant restaurant2 = new Restaurant(restaurant_name_2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant1);
        restaurantManager.add_restaurant(restaurant2);
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, null, notificationCenter);
        orderManager.userManager.add_user(new User(user_email,"rrr", Role.CUSTOMER_STUDENT));
        Order order_1 = new Order(restaurant_name_1);
        Order order_2 = new Order(restaurant_name_2);
        Menu menu_1 = new Menu(menu_name_1, menu_price_1, MenuType.BASIC_MENU);
        Menu menu_2 = new Menu(menu_name_2, menu_price_2, MenuType.BASIC_MENU);
        order_1.add_menu(menu_1);
        order_2.add_menu(menu_2);


        this.order_id_1 = orderManager.placeOrder(user_email, order_1, Locations.HALL_PRINCIPAL);
        this.order_id_2 = orderManager.placeOrder(user_email, order_2, Locations.HALL_PRINCIPAL);
    }
    @When("the user {string} pays their order")
    public void the_user_pays_their_order(String user_email) {
        orderManager.payOrders(user_email, "7936 3468 9302 8371");
    }
    @Then("the order {string} at {double} from {string} and the order {string} at {double} from {string} will be transmitted to the restaurants")
    public void the_order_at_from_and_the_order_at_from_will_be_transmitted_to_the_restaurants(String menu_name_1, Double menu_price_1, String restaurant_name_1, String menu_name_2, Double menu_price_2, String restaurant_name_2) {
        List<Order> orders = orderManager.getCurrentOrders(restaurant_name_1);
        Assert.assertEquals(1, orders.size());
        Order order = orders.get(0);
        Assert.assertEquals(restaurant_name_1, order.getRestaurantName());
        List<Menu> menus = order.getMenus();
        Assert.assertEquals(1, menus.size());
        Menu menu = menus.get(0);
        Assert.assertEquals(menu_name_1, menu.getItemName());
        Assert.assertEquals(menu_price_1, menu.getPrice(), 0.01);

        List<Order> orders_2 =  orderManager.getCurrentOrders(restaurant_name_2);
        Assert.assertEquals(1, orders_2.size());
        Order order_2 = orders_2.get(0);
        Assert.assertEquals(restaurant_name_2, order_2.getRestaurantName());
        List<Menu> menus_2 = order_2.getMenus();
        Assert.assertEquals(1, menus_2.size());
        Menu menu_2 = menus_2.get(0);
        Assert.assertEquals(menu_name_2, menu_2.getItemName());
        Assert.assertEquals(menu_price_2, menu_2.getPrice(), 0.01);
    }

}
