package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.RatingManager.RatingManager;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
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

public class rateClient {

    private RestaurantManager restaurantManager;
    private RatingManager ratingManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;

    @Given("the application is fully working")
    public void the_application_is_fully_working() {
        ratingManager = new RatingManager();
        restaurantManager = new RestaurantManager();
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryManager.addDeliveryLocation("Polytech Nice Hall");
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, deliveryManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
        System.out.println("App ready");
    }

    @Given("delivery personnel {string} logs into the delivery application")
    public void delivery_personnel_logs_into_the_delivery_application(String deliveryMan) {
        userManager.add_user(new User(deliveryMan, deliveryMan, Role.DELIVER_MAN));
    }

    @When("an order is attribute to the delivery man with user {string}")
    public void an_order_is_attribute_to_the_delivery_man_with_user(String user) {
        restaurantManager.addRestaurant(new Restaurant("kfc"));
        userManager.add_user(new User(user, user, Role.CUSTOMER_STUDENT));
        Order order = new Order("kfc");
        order.add_menu(new Menu("chicken", 12));
        UUID orderId = orderManager.placeOrder(user ,order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders(user, "7936 3468 9302 8371");
        orderManager.processingOrder(orderId, "kfc");
        orderManager.setOrderReady(orderId,"kfc");
    }

    @When("delivery personnel {string} enters a rating to {string} for the user of {double}")
    public void delivery_personnel_enters_a_rating_to_for_the_user_of(String deliveryMan, String user, Double rate) {
        GroupOrder groupOrder = deliveryManager.getOrder(deliveryMan);
        String username = groupOrder.getUsers().get(0);
        ratingManager.rateUser(username, rate);
    }

    @Then("the rating by the delivery personnel is added to the reviews of user {string} with {int}")
    public void the_rating_by_the_delivery_personnel_is_added_to_the_reviews_of_user_with(String user, Integer rate) {
        Assert.assertEquals(rate, ratingManager.getRate(user));
    }

    @Then("the rating by the delivery personnel is added to the reviews of user {string} with {double}")
    public void the_rating_by_the_delivery_personnel_is_added_to_the_reviews_of_user_with(String user, Double rate) {
        Assert.assertEquals(rate, ratingManager.getRate(user));
    }


}
