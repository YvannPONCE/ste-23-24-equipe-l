package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.UUID;

public class OrderBuffet {
    private User user;
    private Restaurant restaurant;
    private OrderManager orderManager;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private String menu_name;
    private UUID orderId;
    @Given("a restaurant that offers the buffet menu {string} for {double}€")
    public void a_restaurant_that_offers_the_buffet_menu_for_€(String menu_name, Double menu_price) {
        Restaurant restaurant = new Restaurant("restaurant");
        restaurant.addMenu(new Menu(menu_name, menu_price, MenuType.BUFFET_MENU));
        this.restaurant = restaurant;
        this.restaurantManager = new RestaurantManager();
        this.restaurantManager.add_restaurant(this.restaurant);
        this.statisticsManager = new StatisticsManager(this.restaurantManager);
        this.userManager = new UserManager();
        this.notificationCenter = new NotificationCenter(this.userManager);
        OrderManager orderManager = new OrderManager(this.restaurantManager, this.userManager, this.statisticsManager, this.deliveryManager, this.notificationCenter);
        this.orderManager = orderManager;
        this.menu_name = menu_name;
    }

    @Given("a customer user {string}")
    public void a_customer_user(String user_email) {
        this.user = new User(user_email, "password", Role.CUSTOMER_STUDENT);
        this.userManager.add_user(this.user);
    }
    @When("the user places an order for the {string} menu")
    public void the_user_places_an_order_for_the_menu(String menu_name) {
        Order order = new Order(this.restaurant.getName());
        order.add_menu(this.restaurant.getMenu(menu_name));
        this.orderId = this.orderManager.placeOrder(this.user.getEmail(), order, Locations.HALL_PRINCIPAL);
        this.orderManager.payOrders(this.user.getEmail(), "7936 3468 9302 8371");
    }

    @Then("the order should be placed, and the user should be charged {double}€")
    public void the_order_should_be_placed_and_the_user_should_be_charged_€(Double menu_price) {
        GroupOrder order = this.orderManager.getCurrentOrders(orderId);
        Assert.assertEquals(order.getOrders(this.user.getEmail()).get(0).getRestaurantName(), this.restaurant.getName());
        Assert.assertEquals(order.getOrders(this.user.getEmail()).get(0).getMenus().get(0).getMenuType(), MenuType.BUFFET_MENU);
        Assert.assertEquals(order.getOrderState().getStatus(), Status.PAID);
        Assert.assertEquals(menu_price, order.getOrders(this.user.getEmail()).get(0).getTotalPrice(), 0.001);
    }
}
