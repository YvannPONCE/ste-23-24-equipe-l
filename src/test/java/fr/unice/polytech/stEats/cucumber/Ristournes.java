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
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class Ristournes {
    String restaurant_name;
    Restaurant restaurant;
    RestaurantManager restaurantManager;
    UserManager userManager;
    OrderManager orderManager;
    StatisticsManager statisticsManager;
    DeliveryManager deliveryManager;
    NotificationCenter notificationCenter;
    UUID order_id;
    User user;

    @Given("a new restaurant {string}")
    public void a_new_restaurant(String restaurant_name) {
        this.restaurant_name = restaurant_name;
        this.restaurant = new Restaurant(restaurant_name);
        this.restaurantManager = new RestaurantManager();
        this.restaurantManager.addRestaurant(restaurant);
        this.userManager = new UserManager();
        this.notificationCenter = new NotificationCenter(userManager);
        this.statisticsManager = new StatisticsManager(restaurantManager);
        this.orderManager = new OrderManager(restaurantManager,userManager,statisticsManager, notificationCenter );
        this.deliveryManager = new DeliveryManager(orderManager, userManager, notificationCenter);
        this.orderManager.addDeliveryManager(deliveryManager);
        User user = new User("email", "password", Role.CUSTOMER_STUDENT);
        this.user = user;
        this.userManager.add_user(user);
    }
    @When("User orders for the first time {string} for {double}")
    public void user_orders_for_the_first_time_for(String menu_name, Double menu_price) {
        Order order = new Order(this.restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);
        this.order_id = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
        orderManager.payOrder(this.order_id, user.getEmail(), "7936 3468 9302 8371");
    }
    @Then("User pays {double}")
    public void user_pays(Double menu_price) {
        Double price_paid = this.orderManager.getCurrentOrders(order_id).get_orders(user.getEmail()).get(0).getTotalPrice();
        Assert.assertEquals(menu_price, price_paid);
    }

    @Given("a user orders {int} times from the same restaurant")
    public void a_user_orders_times_from_the_same_restaurant(Integer num_of_orders) {
        this.restaurant_name = "restaurant";
        this.restaurant = new Restaurant(restaurant_name);
        this.restaurantManager = new RestaurantManager();
        this.restaurantManager.addRestaurant(restaurant);
        this.userManager = new UserManager();
        this.notificationCenter = new NotificationCenter(userManager);
        this.statisticsManager = new StatisticsManager(restaurantManager);
        this.orderManager = new OrderManager(restaurantManager,userManager,statisticsManager, notificationCenter );
        this.deliveryManager = new DeliveryManager(orderManager, userManager, notificationCenter);
        this.orderManager.addDeliveryManager(deliveryManager);
        User user = new User("email", "password", Role.CUSTOMER_STUDENT);
        this.user = user;
        this.userManager.add_user(user);
        for (int i = 0; i < num_of_orders; i++) {
            Order order = new Order(this.restaurant_name);
            Menu menu = new Menu("menu", 10.0);
            order.add_menu(menu);
            this.order_id = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
            orderManager.payOrder(this.order_id, user.getEmail(), "7936 3468 9302 8371");
        }
    }
    @When("User orders for the 10th time {string} for {double}")
    public void user_orders_for_the_10th_time_for(String menu_name, Double menu_price) {
        Order order = new Order(this.restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);
        this.order_id = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
        orderManager.payOrder(this.order_id, user.getEmail(), "7936 3468 9302 8371");
    }
    @Then("User recieves discount of {int}% for a period of {int} days")
    public void user_recieves_discount_of_for_a_period_of_days(Integer discount_percentage, Integer discount_period) {
        LocalDate discount_expiration_date = restaurant.getDiscountExpirationDate(user.getEmail());
        double discount_percentage_actual = restaurant.getDiscountPercentage();
        double discount_percentage_expected = discount_percentage/100.0;
        Assert.assertEquals(discount_percentage_expected, discount_percentage_actual, 0.0);
        Assert.assertEquals(LocalDate.now().plusDays(discount_period), discount_expiration_date);
    }

    @Given("a user has a discount of {int}% for a period of {int} days")
    public void a_user_has_a_discount_of_for_a_period_of_days(Integer int1, Integer int2) {
        this.restaurant_name = "restaurant";
        this.restaurant = new Restaurant(restaurant_name);
        this.restaurantManager = new RestaurantManager();
        this.restaurantManager.addRestaurant(restaurant);
        this.userManager = new UserManager();
        this.notificationCenter = new NotificationCenter(userManager);
        this.statisticsManager = new StatisticsManager(restaurantManager);
        this.orderManager = new OrderManager(restaurantManager,userManager,statisticsManager, notificationCenter );
        this.deliveryManager = new DeliveryManager(orderManager, userManager, notificationCenter);
        this.orderManager.addDeliveryManager(deliveryManager);
        User user = new User("email", "password", Role.CUSTOMER_STUDENT);
        this.user = user;
        this.userManager.add_user(user);
        for (int i = 0; i < 11; i++) {
            Order order = new Order(this.restaurant_name);
            Menu menu = new Menu("menu", 10.0);
            order.add_menu(menu);
            this.order_id = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
            orderManager.payOrder(this.order_id, user.getEmail(), "7936 3468 9302 8371");
        }
    }
    @When("User orders {string} for {double}")
    public void user_orders_for(String menu_name, Double menu_price) {
        Order order = new Order(this.restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        order.add_menu(menu);
        this.order_id = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);
        orderManager.payOrder(this.order_id, user.getEmail(), "7936 3468 9302 8371");
    }
    @Then("User pays discounted price of {double}")
    public void user_pays_discounted_price_of(Double discounted_price) {
        Double price_paid = this.orderManager.getCurrentOrders(order_id).get_orders(user.getEmail()).get(0).getTotalPrice();
        Assert.assertEquals(discounted_price, price_paid);
    }
}
