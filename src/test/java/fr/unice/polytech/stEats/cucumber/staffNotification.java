package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Menu;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Order;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

public class staffNotification {

    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private UUID orderID;

    @Given("The application is readyy")
    public void the_application_is_ready() {
        restaurantManager = new RestaurantManager();
        userManager = new UserManager();
        userManager.add_user(new User("user2@example.com", "pass"));
        userManager.add_user(new User("staff@example.com", "pass", Role.CUSTOMER_STAFF));
        Restaurant restaurant = new Restaurant("KFC");
        restaurant.AddStaffMember("staff@example.com");
        restaurantManager.addRestaurant(restaurant);
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryManager.addDeliveryLocation("Polytech Nice Hall");
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, deliveryManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
    }

    @When("a customer places a new order through the system")
    public void a_customer_places_a_new_order_through_the_system() {
        Restaurant restaurant = new Restaurant("KFC");
        restaurantManager.addRestaurant(restaurant);

        Menu menu = new Menu("menu 2", 7.5);
        Order order1 = new Order(restaurant.getName());
        order1.add_menu(menu);
        String location = deliveryManager.getLocations().get(0);
        orderID = orderManager.placeOrder("user2@example.com", order1, Locations.HALL_PRINCIPAL, LocalDateTime.now().withHour(13));
        orderManager.payOrders("user2@example.com", "7936 3468 9302 8371");
    }

    @Then("staff members receive a notification for this new order")
    public void staff_members_receive_a_notification_for_this_new_order() {
        Assert.assertEquals(1, userManager.getUser("staff@example.com").getNotifications().size());
        Assert.assertTrue(userManager.getUser("staff@example.com").getNotifications().get(0).getMessage().contains(orderID.toString()));
    }



}
