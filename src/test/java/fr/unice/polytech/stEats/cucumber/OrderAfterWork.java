package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
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
import io.cucumber.java.zh_tw.假如;
import org.junit.Assert;

import java.util.UUID;

public class OrderAfterWork {
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

    @Given("a restaurant that offers the afterwork menu {string}")
    public void a_restaurant_that_offers_the_afterwork_menu(String menu_name) {
        Restaurant restaurant = new Restaurant("restaurant");
        restaurant.addMenu(new Menu(menu_name, 0.0, MenuType.AFTERWORK_MENU));
        this.restaurant = restaurant;
        this.restaurantManager = new RestaurantManager();
        this.restaurantManager.add_restaurant(this.restaurant);
        this.statisticsManager = new StatisticsManager(this.restaurantManager);
        this.userManager = new UserManager();
        this.notificationCenter = new NotificationCenter(this.userManager);
        OrderManager orderManager = new OrderManager(null, null, null, null, null);
        this.orderManager = orderManager;
        this.menu_name = menu_name;
    }
    @Given("a user {string}")
    public void a_user(String user_email) {
        this.user = new User(user_email, "password", Role.CUSTOMER_STUDENT);
        this.userManager.add_user(this.user);
    }
    @When("the user places an order for the afterwork for {int} people")
    public void the_user_places_an_order_for_the_afterwork_for_people(Integer numberOfParticipants) {
        Order order = new Order(this.restaurant.getName());
        order.add_menu(this.restaurant.getMenu(this.menu_name));
        this.orderId = this.orderManager.placeOrder(this.user, order, numberOfParticipants);
    }
    @Then("the order should be placed and its status should be processing")
    public void the_order_should_be_placed_and_its_status_should_be_processing() {
        GroupOrder order = this.orderManager.getCurrentOrders(orderId);
        Assert.assertEquals(order.getOrders(this.user.getEmail()).get(0).getRestaurantName(), this.restaurant.getName());
        Assert.assertEquals(order.getOrders(this.user.getEmail()).get(0).getMenus().get(0).getMenuType(), MenuType.AFTERWORK_MENU);
        Assert.assertEquals(order.getOrderState().getStatus(), Status.PROCESSING);
        Assert.assertEquals(0, order.getOrders(this.user.getEmail()).get(0).getTotalPrice(), 0.001);
    }
}
