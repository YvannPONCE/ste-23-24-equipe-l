package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Menu;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Order;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import fr.unice.polytech.statisticsManager.StatisticManagerRestaurant;
import fr.unice.polytech.statisticsManager.StatisticManagerStudent;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.time.LocalDateTime;

public class RetriveVolumeStatistics {

    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;

    @Given("The application is ready")
    public void the_application_is_ready() {
        restaurantManager = new RestaurantManager();
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryManager.addDeliveryLocation("Polytech Nice Hall");
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, deliveryManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
    }
    @Given("The restaurant {string} has complete {int} orders of {string} at {int} h et {int} h")
    public void the_restaurant_has_complete_orders_of_at_h_et_h(String restaurantName, Integer numberOfOrders, String menuName, Integer hour1, Integer hour2) {
        userManager.add_user(new User("user@example.com", "pass"));
        Restaurant restaurant = new Restaurant(restaurantName);
        restaurantManager.addRestaurant(restaurant);

        Menu menu = new Menu(menuName, 7.5);
        for(int i=0 ; i<numberOfOrders; ++i)
        {
            Order order1 = new Order(restaurantName);
            Order order2 = new Order(restaurantName);
            order1.add_menu(menu);
            order2.add_menu(menu);
            orderManager.placeOrder("user@example.com", order1, Locations.HALL_PRINCIPAL, LocalDateTime.now().withHour(hour1));
            orderManager.placeOrder("user@example.com", order2, Locations.HALL_PRINCIPAL, LocalDateTime.now().withHour(hour2));
        }
        orderManager.payOrders("user@example.com", "7936 3468 9302 8371");
    }
    @Given("The restaurant {string} has complete {int} orders of {string} at {int} h")
    public void the_restaurant_has_complete_orders_of_at_h(String restaurantName, Integer numberOfOrders, String menuName, Integer hour) {
        userManager.add_user(new User("user2@example.com", "pass"));
        Restaurant restaurant = new Restaurant(restaurantName);
        restaurantManager.addRestaurant(restaurant);

        Menu menu = new Menu(menuName, 7.5);
        for(int i=0 ; i<numberOfOrders; ++i)
        {
            Order order1 = new Order(restaurantName);
            order1.add_menu(menu);
            orderManager.placeOrder("user2@example.com", order1, Locations.HALL_PRINCIPAL, LocalDateTime.now().withHour(hour));
        }
        orderManager.payOrders("user2@example.com", "7936 3468 9302 8371");
    }
    @Then("The restaurant manager {string} can see {int} orders in {string} and {int} order for {string}")
    public void the_restaurant_manager_can_see_orders_in_and_order_for(String manergerName, Integer numberOfOrders1, String restaurantName1, Integer numberOfOrders2, String restaurantName2) {
        StatisticManagerRestaurant statisticManagerRestaurant = statisticsManager;
        Assert.assertEquals(numberOfOrders1.intValue(), statisticManagerRestaurant.getVolumeByRestaurant(restaurantName1));
        Assert.assertEquals(numberOfOrders2.intValue(), statisticsManager.getVolumeByRestaurant(restaurantName2));
    }
    @Then("a user can see {int} orders at {int} and {int} order at {int}")
    public void a_user_can_see_orders_at_and_order_at(Integer numberOfOrders1, Integer hours1, Integer numberOfOrders2, Integer hours2) {
        StatisticManagerStudent statisticManagerStudent = statisticsManager;
        Assert.assertEquals(numberOfOrders1.intValue(), statisticManagerStudent.getVolumeByHour(LocalDateTime.now().withHour(hours1)));
        Assert.assertEquals(numberOfOrders2.intValue(), statisticManagerStudent.getVolumeByHour(LocalDateTime.now().withHour(hours2)));
    }

}
