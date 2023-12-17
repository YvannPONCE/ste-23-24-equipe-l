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
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.HashMap;
import java.util.UUID;

public class ConsultStatistics {

    UserManager userManager;
    Restaurant restaurant;
    RestaurantManager restaurantManager;
    OrderManager orderManager;
    DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    User manager;
    private StatisticsManager statisticsManager;

    @Given("{string} is a restaurant manager")
    public void is_a_restaurant_manager(String restaurantManagerEmail) {
        userManager = new UserManager();
        manager =  new User(restaurantManagerEmail, restaurantManagerEmail, null, Role.RESTAURANT_MANAGER);
        userManager.add_user(manager);
    }
    @Given("his restaurant {string} has complete multiple orders")
    public void his_restaurant_has_complete_multiple_orders(String restaurantName) {
        restaurant = new Restaurant(restaurantName);
        restaurant.addMenu(new Menu("bigMac", 7.5, MenuType.BASIC_MENU));
        Restaurant restaurant2 = new Restaurant("Second restaurant");
        restaurant2.addMenu(new Menu("tartare", 15.5, MenuType.BASIC_MENU));
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        statisticsManager = new StatisticsManager(restaurantManager);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);

        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);

        User customer1 = new User("customer1@gmail.com", "password" ,Role.CUSTOMER_STUDENT);
        User customer2 = new User("customer1@gmail.com", "password" ,Role.CUSTOMER_STUDENT);

        Order order1 = new Order(restaurantName);
        Order order2 = new Order(restaurantName);
        Order order3 = new Order(restaurantName);

        order1.add_menu(new Menu("bigMac", 7.5, MenuType.BASIC_MENU));
        order2.add_menu(new Menu("bigMac", 7.5, MenuType.BASIC_MENU));
        order3.add_menu(new Menu("tartare", 15.5, MenuType.BASIC_MENU));
            orderManager.userManager.add_user(customer1);
            orderManager.userManager.add_user(customer2);
        UUID orderId1 = orderManager.placeOrder(customer1.getEmail() ,order1, Locations.HALL_PRINCIPAL);
        UUID orderId2 = orderManager.placeOrder(customer2.getEmail() ,order2, Locations.BATIMENT_E);
        UUID orderId3 = orderManager.placeOrder(customer1.getEmail() ,order3, Locations.BATIMENT_E);

        orderManager.payOrders(customer1.getEmail(), "7936 3468 9302 8371");
        orderManager.payOrders(customer2.getEmail(), "7936 3468 9302 8371");
        orderManager.payOrders(customer1.getEmail(), "7936 3468 9302 8371");

    }
    @When("i want to consult the volume of orders")
    public void i_want_to_consult_the_volume_of_orders() {

    }
    @Then("i get the number of orders since the begining")
    public void i_get_the_number_of_orders_since_the_begining() {
        Assert.assertEquals(3, statisticsManager.getOrdersCount());
    }

    @When("I want to consult the most popular delivery locations")
    public void i_want_to_consult_the_most_popular_delivery_locations() {

    }

    @Then("I see a list of delivery locations and their popularity")
    public void i_see_a_list_of_delivery_locations_and_their_popularity() {
        Assert.assertEquals(6, statisticsManager.get_popular_locations().size());
        Assert.assertEquals(2, statisticsManager.get_popular_locations().get(Locations.BATIMENT_E).intValue());
        Assert.assertEquals(1, statisticsManager.get_popular_locations().get(Locations.HALL_PRINCIPAL).intValue());
    }

    @When("I want to consult the most popular menus")
    public void i_want_to_consult_the_most_popular_menus() {
    }

    @Then("The manager get the most populars menus")
    public void the_manager_get_the_most_populars_menus() {
        HashMap<Menu, Integer> menuStatistics = statisticsManager.get_popular_menus(restaurant.getName());
        Assert.assertEquals(2, menuStatistics.size());
        Assert.assertEquals(2, menuStatistics.get(new Menu("bigMac", 7.5, MenuType.BASIC_MENU)).intValue());
        Assert.assertEquals(1, menuStatistics.get(new Menu("tartare", 15.5, MenuType.BASIC_MENU)).intValue());
    }


}
