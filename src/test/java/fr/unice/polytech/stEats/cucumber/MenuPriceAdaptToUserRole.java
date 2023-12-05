package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Menu;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.cs.Ale;
import io.cucumber.java.da.Men;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class MenuPriceAdaptToUserRole {

    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private StatisticsManager statisticsManager;
    private OrderManager orderManager;
    private DeliveryManager deliveryManager;
    private NotificationCenter notificationCenter;
    private Menu basicMenu;
    private Menu discountedMenu;

    @Given("The application is readyyy")
    public void the_application_is_readyyy() {
        restaurantManager = new RestaurantManager();
        Restaurant restaurant = new Restaurant("BK");
        restaurant.addMenu(new Menu("steakHouse", 100));
        restaurantManager.addRestaurant(restaurant);
        userManager = new UserManager();
        notificationCenter = new NotificationCenter(userManager);
        statisticsManager = new StatisticsManager(restaurantManager);
        deliveryManager = new DeliveryManager(userManager, notificationCenter);
        deliveryManager.addDeliveryLocation("Polytech Nice Hall");
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, deliveryManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManager);
    }

    @Given("A registered staffmember")
    public void A_registered_staffmember() {
    }

    @When("the staff member get the menus of a restaurant")
    public void the_staff_member_get_the_menus_of_a_restaurant() {
        List<Menu> menus = restaurantManager.getRestaurant("BK").getListemenu();
        basicMenu = menus.get(0);
        menus = restaurantManager.getRestaurant("BK").getListemenu(Role.CUSTOMER_STAFF);
        discountedMenu = menus.get(0);
    }

    @Then("the prices are {int} percent lower of the normal one")
    public void the_prices_are_percent_lower_of_the_normal_one(Integer discount) {
        Assert.assertEquals(basicMenu.getPrice()*discount.intValue()/100, discountedMenu.getPrice(), 0.01);
    }

    @Given("A registered admin member")
    public void a_registered_admin_member() {
    }

    @When("the admin member get the menus of a restaurant")
    public void the_admin_member_get_the_menus_of_a_restaurant() {
        List<Menu> menus = restaurantManager.getRestaurant("BK").getListemenu();
        basicMenu = menus.get(0);
        menus = restaurantManager.getRestaurant("BK").getListemenu(Role.CUSTOMER_TEACHER);
        discountedMenu = menus.get(0);
    }

    @Given("A registered delivery person")
    public void a_registered_delivery_person() {
    }

    @When("the delivery member get the menus of a restaurant")
    public void the_delivery_member_get_the_menus_of_a_restaurant() {
        List<Menu> menus = restaurantManager.getRestaurant("BK").getListemenu();
        basicMenu = menus.get(0);
        menus = restaurantManager.getRestaurant("BK").getListemenu(Role.DELIVER_MAN);
        discountedMenu = menus.get(0);
    }

}
