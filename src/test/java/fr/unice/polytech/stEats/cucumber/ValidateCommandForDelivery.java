package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
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

public class ValidateCommandForDelivery {


    OrderManager orderManager;
    DeliveryManager deliveryManager;
    UUID orderId;

    private Restaurant restaurant;
    private NotificationCenter notificationCenter;
    private RestaurantManager restaurantManager;
    private  UserManager userManager;


    @Given("user {string} as order a {string} at {double} at {string} and as paid his command.")
    public void user_as_order_a_at_at_and_as_paid_his_command(String user_email, String menu_name, Double menu_price, String restaurant_name) {
        userManager = new UserManager();

        notificationCenter = new NotificationCenter(userManager);

        restaurant = new Restaurant(restaurant_name );
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);



        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), null, notificationCenter);
        orderManager.userManager.add_user(new User(user_email,"rrr", Role.CUSTOMER_STUDENT));
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);



        Order order = new Order(restaurant_name);
        order.add_menu(new Menu(menu_name, menu_price, MenuType.BASIC_MENU));

        userManager.addUser(new User("Albert@gmail.com","Albert", Role.DELIVER_MAN));
        orderId = orderManager.placeOrder(user_email, order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders( user_email, "7936 3468 9302 8371");
    }
    @When("the restaurant has finish preprared the order")
    public void the_restaurant_has_finish_preprared_the_order() {
        orderManager.processingOrder(orderId, restaurant.getName());
        orderManager.setOrderReady(orderId, restaurant.getName());
    }
    @Then("The status of the order of {string} has change to READY.")
    public void the_status_of_the_order_of_has_change_to_ready(String user_email) {
        List<Order> orders = orderManager.getCurrentOrders(orderId, user_email);

        Assert.assertEquals(Status.READY, orders.get(0).getOrderState().getStatus());
    }
}
