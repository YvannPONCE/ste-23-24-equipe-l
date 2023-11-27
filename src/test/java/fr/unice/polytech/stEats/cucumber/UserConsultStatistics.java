package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
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

    @Given("the application is running with two restaurant {string} and {string}")
    public void the_application_is_running_with_two_restaurant_and(String restaurantName1, String restaurantName2) {
        Restaurant restaurant1 = new Restaurant(restaurantName1);
        Restaurant restaurant2 = new Restaurant(restaurantName2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant1);
        restaurantManager.add_restaurant(restaurant2);

        userManager = new UserManager();
        statisticsManager = new StatisticsManager(restaurantManager);
        StatisticManagerOrderManager statisticManagerOrderManager = statisticsManager;
        orderManager = new OrderManager(restaurantManager, userManager, statisticManagerOrderManager);

        deliveryManager = new DeliveryManager(orderManager, orderManager.userManager);
        deliveryManager.addDeliveryman("deliveryManEmail","deliveryManName");
        orderManager.addDeliveryManager(deliveryManager);
    }
    @Given("User {string} order a {string} at {int} euros at {string}")
    public void user_order_a_at_euros_at(String userEmail, String menuName, Integer menuPrice, String menuRestaurant) {
        Menu menu = new Menu(menuName, menuPrice);
        Order order = new Order(menuRestaurant, new ArrayList<Menu>(Arrays.asList(menu)));
        orderManager.place_order(userEmail ,order, Locations.HALL_PRINCIPAL);
        orderManager.pay_user_orders(userEmail, "7936 3468 9302 8371");
    }
    @When("when {string} consult her favorites restaurants")
    public void when_consult_her_favorites_restaurants(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("{string} sees that her favorite restaurant is {string} following by {string}")
    public void sees_that_her_favorite_restaurant_is_following_by(String userEmail, String restaurantName1, String restaurantName2) {
        StatisticManagerStudent statisticsManagerStudent = statisticsManager;
        HashMap<String, Integer> favoriteRestaurant = statisticsManagerStudent.getFavoriteRestaurant(userEmail);
        Assert.assertTrue(favoriteRestaurant.get(restaurantName1) > favoriteRestaurant.get(restaurantName2));
    }
}
