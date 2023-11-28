package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.RestaurantManager.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

public class ManageCapacityatachoosenSlot {


    private UserManager userManager;
    private String user_email;
    private User user;
    private Restaurant restaurant2;
    private RestaurantManager restaurantManager;
    private OrderManager orderManager;
    private LocalTime time;
    private Order order;
    private UUID orderId;
    private LocalDateTime localDateTime;

    @Given("user {string} order at {int}:{int} at {string}")
    public void user_order_at_at(String string, Integer int1, Integer int2, String string2) {
        userManager = new UserManager();
        this.user_email = string;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant2 = new Restaurant(string2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant2);
         time = LocalTime.of(int1, int2);

       localDateTime = LocalDateTime.of(LocalDate.now(), time);

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }
    @When("user choose a  {int} nuggets menu")
    public void user_choose_a_nuggets_menu(Integer int1) {
        order = new Order(restaurant2.getName());
        Menu menu = new Menu("chicken nuggets", 8.00);
        order.add_menu(menu);
        order.add_menu(menu);


        orderId = orderManager.place_order_slot(user_email, order, Locations.HALL_PRINCIPAL, LocalDateTime.from(localDateTime));
    }
    @Then("capacity at this restaurant should be {int}")
    public void capacity_at_this_restaurant_should_be(Integer int1) {
        Assert.assertEquals(Optional.of(restaurant2.getHourlyCapacity(LocalDateTime.from(localDateTime).getHour())),Optional.of(int1));
    }

    @Given("user {string} order at {int}:{int} at {string} but the chosen slot is full")
    public void user_order_at_at_but_the_chosen_slot_is_full(String string, Integer int1, Integer int2, String string2) {
        userManager = new UserManager();
        this.user_email = string;
        user=new User(user_email,"john", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        restaurant2 = new Restaurant(string2);
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant2);
        restaurant2.setHourlyCapacity(16,0);
        time = LocalTime.of(int1, int2);

        localDateTime = LocalDateTime.of(LocalDate.now(), time);

        orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));
    }
    @When("user order his demand is rejected")
    public void user_order_his_demand_is_rejected() {
        order = new Order(restaurant2.getName());
        Menu menu = new Menu("chicken nuggets", 8.00);
        order.add_menu(menu);
        order.add_menu(menu);
        orderId = orderManager.place_order_slot(user_email, order, Locations.HALL_PRINCIPAL, LocalDateTime.from(localDateTime));


    }
    @Then("we suggest next available slot")
    public void we_suggest_next_available_slot() {
        Assert.assertEquals(orderId,null);
        Assert.assertEquals(orderManager.getNextSlot().getHour(), 17);
    }


}
