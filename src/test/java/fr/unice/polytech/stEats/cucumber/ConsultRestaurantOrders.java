package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Menu;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Order;
import fr.unice.polytech.RestaurantManager.RestaurantManagerStaff;
import fr.unice.polytech.UserManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import org.junit.Assert;

import java.text.Normalizer;
import java.util.List;

public class ConsultRestaurantOrders {

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private OrderManager orderManager;
    private StatisticsManager statisticsManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;

    @Given("a restaurant {string}")
    public void a_restaurant(String restaurantName) {
        restaurant = new Restaurant(restaurantName);
        restaurantManager = new RestaurantManager();
        restaurantManager.addRestaurant(restaurant);

        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        orderManager = new OrderManager(restaurantManager,userManager,statisticsManager, notificationCenter );
        deliveryManager = new DeliveryManager(orderManager, userManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
    }
    @Given("the restaurant has complete {int} orders of a {string} menu at {double} euros")
    public void the_restaurant_has_complete_orders_of_a_menu_at_euros(Integer n, String menuName, Double menuPrice) {
        for(int i=0; i<n; ++i)
        {
            Menu menu = new Menu(menuName, menuPrice);
            Order order = new Order(restaurant.getName());
            order.add_menu(menu);

            orderManager.place_order("email", order, Locations.HALL_PRINCIPAL);
            orderManager.pay_user_orders("email", "7936 3468 9302 8371");
        }
    }
    @When("the staff wants to consult the current orders")
    public void the_staff_wants_to_consult_the_current_orders() {
    }
    @Then("they retrieve the list with the {int} orders")
    public void they_retrieve_the_list_with_the_orders(Integer n) {
        RestaurantManagerStaff restaurantManagerStaff = restaurantManager;
        List<Order> orders = restaurantManagerStaff.getCurrentOrders(restaurant.getName());
        Assert.assertEquals(n.intValue(), orders.size());
    }

}
