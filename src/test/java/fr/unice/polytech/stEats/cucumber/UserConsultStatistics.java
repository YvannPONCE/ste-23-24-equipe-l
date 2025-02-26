package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.statisticsManager.StatisticManagerOrderManager;
import fr.unice.polytech.statisticsManager.StatisticManagerStudent;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class UserConsultStatistics {

    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;

    @Given("the application is running with two restaurant {string} and {string}")
    public void the_application_is_running_with_two_restaurant_and(String restaurantName1, String restaurantName2) {
        Restaurant restaurant1 = new Restaurant(restaurantName1);
        Restaurant restaurant2 = new Restaurant(restaurantName2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant1);
        restaurantManager.add_restaurant(restaurant2);

        userManager = new UserManager();
        userManager.add_user(new User("melanie@egmail.com", "password", Role.CUSTOMER_STUDENT));
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        StatisticManagerOrderManager statisticManagerOrderManager = statisticsManager;
        orderManager = new OrderManager(restaurantManager, userManager, statisticManagerOrderManager, null, notificationCenter);

        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        userManager.addUser(new User("deliveryManEmail","deliveryManName", Role.DELIVER_MAN));
        orderManager.addDeliveryManager(deliveryManager);
    }
    @Given("User {string} order a {string} at {int} euros at {string}")
    public void user_order_a_at_euros_at(String userEmail, String menuName, Integer menuPrice, String menuRestaurant) {
        Menu menu = new Menu(menuName, menuPrice, MenuType.BASIC_MENU);
        Order order = new Order(menuRestaurant, new ArrayList<Menu>(Arrays.asList(menu)));
        orderManager.placeOrder(userEmail ,order, Locations.HALL_PRINCIPAL);
        orderManager.payOrders(userEmail, "7936 3468 9302 8371");
    }
    @When("when {string} consult her favorites restaurants")
    public void when_consult_her_favorites_restaurants(String string) {
    }

    @Then("{string} sees that her favorite restaurant is {string} following by {string}")
    public void sees_that_her_favorite_restaurant_is_following_by(String userEmail, String restaurantName1, String restaurantName2) {
        StatisticManagerStudent statisticsManagerStudent = statisticsManager;
        HashMap<String, Integer> favoriteRestaurant = statisticsManagerStudent.getFavoriteRestaurant(userEmail);
        Assert.assertTrue(favoriteRestaurant.get(restaurantName1) > favoriteRestaurant.get(restaurantName2));
    }

    @Given("the restaurant {string} has complete {int} orders to Polytech Hall")
    public void the_restaurant_has_complete_orders_to_Polytech_Hall(String restaurantName, Integer numberOfOrders) {
        Menu menu;
        Order order;

        for(int i =0;i<numberOfOrders;++i) {
            menu = new Menu("Bucket", 7.5, MenuType.BASIC_MENU);
            order = new Order(restaurantName, new ArrayList<Menu>(Arrays.asList(menu)));
            orderManager.placeOrder("melanie@egmail.com", order, Locations.HALL_PRINCIPAL);
            orderManager.payOrders("melanie@egmail.com", "7936 3468 9302 8371");
        }
    }
    @When("As a student I want to consult the most popular delivery locations")
    public void as_a_student_i_want_to_consult_the_most_popular_delivery_locations() {
    }

    @Then("As a student I see that Polytech Hall has been choosen {int} times")
    public void as_a_student_i_see_that_polytech_hall_has_been_choosen_times(Integer n) {
        StatisticManagerStudent statisticManagerStudent = statisticsManager;
        Assert.assertEquals(n, statisticManagerStudent.get_popular_locations().get(Locations.HALL_PRINCIPAL));
    }

    @When("when {string} consult her total number of orders")
    public void when_consult_her_total_number_of_orders(String string) {
    }

    @Then("{string} sees that she made {int} orders")
    public void sees_that_she_made_orders(String userEmail, Integer n) {
        StatisticManagerStudent statisticManagerStudent = statisticsManager;
        Assert.assertEquals(n.intValue(), statisticManagerStudent.getOrdersCount());
    }
}
