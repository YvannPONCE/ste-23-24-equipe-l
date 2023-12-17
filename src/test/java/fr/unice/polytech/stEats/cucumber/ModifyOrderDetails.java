package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.Exception.OrderAlreadyPaidException;
import fr.unice.polytech.Menu;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Order;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

public class ModifyOrderDetails {

    private UserManager userManager;
    private User user;
    private RestaurantManager restaurantManager;
    private NotificationCenter notificationCenter;
    private OrderManager orderManager;
    private Restaurant restaurant;
    private UUID orderId;
    private Order order;
    private OrderAlreadyPaidException caughtException;
    private LocalTime time;
    private LocalDateTime localDateTime;
    private Menu menu;


    @Given("User {string} ordered a menu nuggets menu from mcdonald's the delivery Location was HALL_PRINCIPAL")
    public void user_ordered_a_menu_nuggets_menu_from_mcdonald_s_the_delivery_location_was_hall_principal(String userEmail) {
        userManager = new UserManager();
        user = new User(userEmail, userEmail);
        userManager.add_user(user);
        restaurantManager = new RestaurantManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), null, notificationCenter);
        restaurant = new Restaurant("mcdonalds");
        restaurant.setCapacity(10);
        restaurantManager.add_restaurant(restaurant);

        Menu menu = new Menu("nuggets", 7.50);
        order = new Order(restaurant.getName());
        order.add_menu(menu);

        orderId = orderManager.placeOrder(userEmail, order, Locations.HALL_PRINCIPAL);

    }

    @When("the user wants to modify the delivery location to     BATIMENT_E,")
    public void the_user_wants_to_modify_the_delivery_location_to_batiment_e() throws OrderAlreadyPaidException {
        orderManager.modifyOrderLocation(orderId, user.getEmail(), Locations.BATIMENT_E, order);

    }


    @Then("the location is modified")
    public void the_location_is_modified() {
        assertEquals(order.getOrderState().getStatus(), Status.CREATED);
        Assert.assertEquals(orderManager.getCurrentOrders(orderId).getDeliveryLocation(), Locations.BATIMENT_E);
    }

    @Given("User {string} ordered a menu chicken burger  menu from quick mcdothe delivery Location was HALL_PRINCIPAL")
    public void user_ordered_a_menu_chicken_burger_menu_from_quick_mcdothe_delivery_location_was_hall_principal(String userEmail) {
        userManager = new UserManager();
        user = new User(userEmail, userEmail);
        userManager.add_user(user);
        restaurantManager = new RestaurantManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), null, notificationCenter);
        restaurant = new Restaurant("quick");
        restaurant.setCapacity(10);
        restaurantManager.add_restaurant(restaurant);

        Menu menu = new Menu("chicken burger", 7.50);
        order = new Order(restaurant.getName());
        order.add_menu(menu);

        orderId = orderManager.placeOrder(userEmail, order, Locations.HALL_PRINCIPAL);
    }

    @When("the user wants to modify the delivery location to     BATIMENT_A,")
    public void the_user_wants_to_modify_the_delivery_location_to_batiment_a() throws OrderAlreadyPaidException {
        orderManager.payOrders(user.getEmail(), "7936 3468 9302 8371");

        // Attempt to modify the order
        try {
            orderManager.modifyOrderLocation(orderId, user.getEmail(), Locations.BATIMENT_A, order);
            fail("Expected OrderAlreadyPaidException to be thrown");
        } catch (OrderAlreadyPaidException e) {
            caughtException = e;
        }

    }

    @Then("the request is rejected")
    public void the_request_is_rejected() {
        assertEquals(order.getOrderState().getStatus(), Status.PAID);
        assertNotNull("Expected an OrderAlreadyPaidException to be caught", caughtException);
        assertEquals("Order with ID " + orderId + " has already been paid for and cannot be modified.", caughtException.getMessage());

    }
    @Given("User {string} ordered a menu chicken burger  menu from quick at  deliverytime {int}:{int}")
    public void user_ordered_a_menu_chicken_burger_menu_from_quick_at_deliverytime(String userEmail, Integer hours, Integer minutes) {
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);

        user=new User(userEmail,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant = new Restaurant("quick");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        time = LocalTime.of(hours, minutes);
        order = new Order(restaurant.getName());
        menu=new Menu("chicken burger",8.5);
        order.add_menu(menu);

        localDateTime = LocalDateTime.of(LocalDate.now(), time);

        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), notificationCenter);
        orderId = orderManager.placeOrder(userEmail, order, Locations.HALL_PRINCIPAL,localDateTime);
    }
    @When("the user wants to modify the chosen slot {int}:{int}")
    public void the_user_wants_to_modify_the_chosen_slot(Integer hours, Integer minutes) throws OrderAlreadyPaidException {
        time = LocalTime.of(hours, minutes);
        localDateTime = LocalDateTime.of(LocalDate.now(), time);

        orderManager.modifyOrderTime(orderId,user.getEmail(),localDateTime);

    }
    @Then("the time is modified")
    public void the_time_is_modified() {
        assertEquals(order.getOrderState().getStatus(), Status.CREATED);
     assertEquals(orderManager.getCurrentOrders(orderId).getDeliveryTime(),localDateTime);
    }

    @Given("User {string} ordered a menu chicken burger  menu from quick2 at  deliverytime {int}:{int}")
    public void user_ordered_a_menu_chicken_burger_menu_from_quick2_at_deliverytime(String userEmail, Integer hours, Integer minutes) {
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);

        user=new User(userEmail,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant = new Restaurant("quick");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        time = LocalTime.of(hours, minutes);
        order = new Order(restaurant.getName());
        menu=new Menu("chicken burger",8.5);
        order.add_menu(menu);

        localDateTime = LocalDateTime.of(LocalDate.now(), time);

        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), notificationCenter);
        orderId = orderManager.placeOrder(userEmail, order, Locations.HALL_PRINCIPAL,localDateTime);
    }
    @When("user try to modify the chosen slot {int}:{int}")
    public void user_try_to_modify_the_chosen_slot(Integer hours, Integer minutes) {
        time = LocalTime.of(hours, minutes);
        localDateTime = LocalDateTime.of(LocalDate.now(), time);


        orderManager.payOrders(user.getEmail(), "7936 3468 9302 8371");

        try {
            orderManager.modifyOrderTime(orderId,user.getEmail(),localDateTime);
            fail("Expected OrderAlreadyPaidException to be thrown");
        } catch (OrderAlreadyPaidException e) {
            caughtException = e;
        }


    }
    @Then("the modification is rejected")
    public void the_modification_is_rejected() {
        assertEquals(order.getOrderState().getStatus(), Status.PAID);
        assertNotNull("Expected an OrderAlreadyPaidException to be caught", caughtException);
        assertEquals("Order with ID " + orderId + " has already been paid for and cannot be modified.", caughtException.getMessage());

    }


}

