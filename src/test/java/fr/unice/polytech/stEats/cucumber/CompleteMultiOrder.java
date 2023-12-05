package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
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

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class CompleteMultiOrder {

    private OrderManager orderManager;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private  User deliveryMan;
    UUID groupeOrderId;

    @Given("restaurants {string} and {string} use st-eats and one delivery man {string}")
    public void restaurants_and_use_st_eats_and_one_delivery_man(String restaurantName1, String restaurantName2, String deliveryManName) {
        restaurant1 = new Restaurant(restaurantName1);
        restaurant2 = new Restaurant(restaurantName2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant1);
        restaurantManager.add_restaurant(restaurant2);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager),new NotificationCenter(userManager));
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryMan = new User(deliveryManName,"albert", Role.DELIVER_MAN);
        userManager.addUser(deliveryMan);
        orderManager.addDeliveryManager(deliveryManager);

    }
    @Given("the user {string} order a {string} at {string} for {double} euros")
    public void the_user_order_a_at_for_euros(String userEmail, String menuName, String restaurantName, Double menuPrice) {
        userManager.add_user( new User(userEmail,userEmail, Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurantName);
        Menu menu = new Menu(menuName, menuPrice);
        order.add_menu(menu);

        groupeOrderId = orderManager.placeOrder(userEmail, order, Locations.HALL_PRINCIPAL);
    }

    @Given("the user {string} order a {string} at {string} for {double} euros on his friend order")
    public void the_user_order_a_at_for_euros_on_his_friend_order(String userEmail, String menuName, String restaurantName, Double menuPrice) {
        userManager.add_user( new User(userEmail,userEmail, Role.CUSTOMER_STUDENT));
        Order order = new Order(restaurantName);
        Menu menu = new Menu(menuName, menuPrice);
        order.add_menu(menu);

        orderManager.placeOrder(userEmail, order, this.groupeOrderId);

        Assert.assertTrue(orderManager.getCurrentOrders(groupeOrderId).getGlobalOrders().containsKey(userEmail));
    }

    @Given("the user {string} pay his order")
    public void the_user_pay_his_order(String userEmail) {
        orderManager.payOrders(userEmail, "7936 3468 9302 8371");
        Assert.assertNotEquals(Status.PAID, orderManager.getCurrentOrders(this.groupeOrderId).getOrderState().getStatus());
    }
    @Given("the user {string} pay his order in second")
    public void the_user_pay_his_order_in_second(String userEmail) {
        orderManager.payOrders(userEmail, "7936 3468 9302 8371");
        assertEquals(Status.PAID, orderManager.getCurrentOrders(this.groupeOrderId).getOrderState().getStatus());
    }
    @Given("The simple order is marked ready by the restaurant {string}")
    public void the_simple_order_is_marked_ready_by_the_restaurant(String restaurantName) {
        orderManager.processingOrder(groupeOrderId, restaurant1.getName());
        orderManager.processingOrder(groupeOrderId, restaurant2.getName());
        orderManager.setOrderReady(groupeOrderId, restaurantName);
        Assert.assertNotEquals(Status.READY, orderManager.getCurrentOrders(this.groupeOrderId).getOrderState().getStatus());
    }
    @Given("The simple order is marked ready by the restaurant {string} in second")
    public void the_simple_order_is_marked_ready_by_the_restaurant_in_second(String restaurantName) {
        orderManager.setOrderReady(groupeOrderId, restaurantName);
        Assert.assertEquals(Status.READY, orderManager.getCurrentOrders(this.groupeOrderId).getOrderState().getStatus());
    }
    @When("user {string} confirm the delivery")
    public void user_confirm_the_delivery(String userEmail) {

        deliveryManager.validateOrder(deliveryMan.getEmail());
        Assert.assertEquals(Status.DELIVERED, orderManager.getCurrentOrders(this.groupeOrderId).getOrderState().getStatus());
    }
    @When("delivery man {string} confirm the delivery")
    public void delivery_man_confirm_the_delivery(String deliveryManName) {

        deliveryManager.validateOrder(deliveryManName);
    }
    @Then("The group order is marked as closed")
    public void the_group_order_is_marked_as_closed() {
        assertEquals(true, deliveryManager.getDeliveryMenAvailability().get(deliveryMan.getEmail()));
    }
}
