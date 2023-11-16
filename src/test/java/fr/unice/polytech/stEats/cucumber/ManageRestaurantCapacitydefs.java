package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class ManageRestaurantCapacitydefs {

    private UserManager userManager;
    private Restaurant restaurant;
    private String user_email;
    private User user;
    private RestaurantManager restaurantManager;
    private OrderManager orderManager;
    private UUID orderId;
    private RestaurantCapacityCalculator restaurantCapactityMock;
    private LocalDateTime mockDateTime;
    private Order order;
    private Restaurant restaurant2;
    private User user3;
    private Order order2;
    private UUID orderId2;


    @Given("user {string} and Restaurant {string} has a capacity of {int} menus per hour and the time slot for {int}:{int} PM is full")
    public void user_and_restaurant_has_a_capacity_of_menus_per_hour_and_the_time_slot_for_pm_is_full(String string, String string2, Integer int1, Integer int2, Integer int3) {
        userManager = new UserManager();
        this.user_email = string;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant = new Restaurant(string2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(1);

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }


    @When("user attempt to choose the {int}:{int} PM time slot")
    public void user_attempt_to_choose_the_pm_time_slot(Integer int1, Integer int2) {
        Order order = new Order(restaurant.getName());
        Menu menu = new Menu("nuggets", 8.00);
        order.add_menu(menu);
        order.add_menu(menu);

        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);

    }

    @Then("user choice is rejected and the next available time slot is suggested")
    public void user_choice_is_rejected_and_the_next_available_time_slot_is_suggested() {

        Assert.assertEquals(orderId,null);
        Assert.assertEquals(orderManager.getNextSlot().getHour(), LocalDateTime.now().plusHours(1).getHour());
    }


    @Given("user {string} and Restaurant {string} has a capacity of {int} menus per hour with available slot")
    public void user_and_restaurant_has_a_capacity_of_menus_per_hour_with_available_slot(String string, String string2, Integer int1) {
        userManager = new UserManager();
        this.user_email = string;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant2 = new Restaurant(string2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant2);

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }
    @When("user order a {string} in the restaurant")
    public void user_order_a_in_the_restaurant(String string) {
         order = new Order(restaurant2.getName());
        Menu menu = new Menu(string, 8.00);
        order.add_menu(menu);


        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);

    }
    @Then("user have a created order status")
    public void user_have_a_created_order_status() {
        Assert.assertFalse(orderId.equals(null));
        Assert.assertEquals(order.getStatus(), Status.CREATED);
        Assert.assertEquals(restaurant2.getCapacity(),9);
    }


    @Given("user1 and user2 place orders in restaurant luigi")
    public void user1_and_user2_place_orders_in_restaurant_luigi() {
        user3=new User("user3@exemple","karl",Role.CUSTOMER_STUDENT);
        userManager = new UserManager();
        this.user_email = "user1@exemple.com";
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        userManager.add_user(user3);
        restaurant2 = new Restaurant("luigi");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant2);

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }
    @When("user order a {string} in the restaurant ans")
    public void user_order_a_in_the_restaurant_ans(String string) {
        order = new Order(restaurant2.getName());
        order2=new Order(restaurant2.getName());

        Menu menu = new Menu(string, 8.00);

            order2.add_menu(menu);
        order.add_menu(menu);
        orderId2=orderManager.place_order(user3.get_email(),order2,Locations.HALL_PRINCIPAL);


        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
    }
    @Then("user have a created order status and restaurant capacity become {int}")
    public void user_have_a_created_order_status_and_restaurant_capacity_become(Integer int1) {
        Assert.assertFalse(orderId.equals(null));
        Assert.assertFalse(orderId2.equals(null));
        Assert.assertEquals(order.getStatus(), Status.CREATED);
        Assert.assertEquals(order2.getStatus(), Status.CREATED);
        Assert.assertEquals(Optional.of(restaurant2.getCapacity()),Optional.of(int1));
    }

    @Given("user {string} ordered in a  Restaurant {string} with a capacity of {int} menus per hour with available slot")
    public void user_ordered_in_a_restaurant_with_a_capacity_of_menus_per_hour_with_available_slot(String string, String string2, Integer int1) {
        userManager = new UserManager();
        this.user_email = string;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant = new Restaurant(string2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);;

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }
    @When("user order a {string} and validate order receipt")
    public void user_order_a_and_validate_order_receipt(String string) {
        Order order = new Order(restaurant.getName());
        Menu menu = new Menu(string, 8.00);
        order.add_menu(menu);
        order.add_menu(menu);

        orderId = orderManager.place_order(user_email, order, Locations.HALL_PRINCIPAL);
        orderManager.validate_order_receipt(order.getId());
    }
    @Then("the restaurant is set to {int}")
    public void the_restaurant_is_set_to(Integer int1) {
        Assert.assertFalse(orderId.equals(null));

        Assert.assertEquals(Optional.of(restaurant.getCapacity()),Optional.of(int1));
    }

}
