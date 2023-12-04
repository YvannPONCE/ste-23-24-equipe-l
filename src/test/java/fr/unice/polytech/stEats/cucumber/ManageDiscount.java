package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
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

import java.util.UUID;

import static java.lang.Math.round;
import static org.junit.Assert.assertEquals;

public class ManageDiscount {
    OrderManager orderManager;
    User user1;
    User user2;
    UUID order_id;
    String CreditCard="7936 3468 9302 8371";
 OrderAmountCalculator orderAmountCalculator;
    private Order order;
    private UUID orderId;
    private Double expected=0.0;
    private User user3;
    private Order order2;
    private Order order3;
    private UUID orderId2;
    private Double expected2;
    private User user4;
    private Order order4;
    private UUID orderId3;

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private NotificationCenter notificationCenter;


    @Given("user {string} with {double} credit and the discount threshold is set to {int}")
    public void user_with_credit_and_the_discount_threshold_is_set_to(String string, Double double1, Integer int1) {
        restaurant = new Restaurant("chickenTacky" );
        restaurantManager = new RestaurantManager();
        userManager = new UserManager();
        restaurantManager.add_restaurant(restaurant);
        restaurant.setCapacity(16);
        user1=new User(string,"john",Role.CUSTOMER_STUDENT);
        userManager.add_user(user1);
        notificationCenter = new NotificationCenter(userManager);
        StatisticsManager statisticsManager = new StatisticsManager(restaurantManager);
        orderManager = new OrderManager(restaurantManager, userManager, statisticsManager, null, notificationCenter);
        order = new Order("chickenTacky");
    }
    @When("the user selects {string} and adds {int} items ton his  order")
    public void the_user_selects_and_adds_items_ton_his_order(String string, Integer int1) {
        Menu menu=new Menu("chickennuggets",8.00);
        for(int i=0;i<int1;i++) {
            order.add_menu(menu);
        }

        orderId=orderManager.placeOrder(user1.getEmail(), order, Locations.HALL_PRINCIPAL);

        orderAmountCalculator =new OrderAmountCalculator(orderManager.getCurrentOrders(order_id),orderManager.userManager);
        orderAmountCalculator.setItemCountThreshold(16);
        orderManager.payOrders(user1.getEmail(),CreditCard);

        }

    @Then("he  should see a discount of {int}% applied to the order total and get additional credit")
    public void he_should_see_a_discount_of_applied_to_the_order_total_and_get_additional_credit(Integer int1) {
            double expectedOrderTotal = order.getTotalPrice() ;
            assertEquals(96.0, expectedOrderTotal, 0.001);

        expected=user1.getCredit();
        Assert.assertEquals(14.4, expected,0.001);


    }


    @Given("One restaurant, two menu, two users {string} and {string} waiting in {string} with {double} credit")
    public void one_restaurant_two_menu_two_users_and_waiting_in_with_credit(String string, String string2, String string3, Double double1) {
        user2=new User(string,"elodie",Role.CUSTOMER_STUDENT);
        user3=new User(string2,"james",Role.CUSTOMER_STUDENT);
        restaurant = new Restaurant("chickentacky" );
        Restaurant restaurant2 = new Restaurant("Mcdon");
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(restaurant);
        restaurantManager.add_restaurant(restaurant2);
        restaurant.setCapacity(10);
        notificationCenter = new NotificationCenter(userManager);
        orderManager=new OrderManager(restaurantManager,new UserManager(), new StatisticsManager(restaurantManager), notificationCenter);
        orderManager.userManager.getUserList().add(user2);
        orderManager.userManager.getUserList().add(user3);
        order3=new Order("Mcdon");

    }
    @When("The first user add a {int} {string} menu at {double} euros from {string} to deliver at {string}")
    public void the_first_user_add_a_menu_at_euros_from_to_deliver_at(Integer int1, String string, Double double1, String string2, String string3) {
        order2 = new Order(string2);
        restaurantManager.add_restaurant(new Restaurant(string2));
        Menu menu=new Menu(string,double1);
        for(int i=0;i<int1;i++){
            order2.add_menu(menu);
        }
        orderId2=orderManager.placeOrder(user2.getEmail(),order2,Locations.HALL_PRINCIPAL);
    }
    @When("The second join {int} a {string} menu at {double} euros from {string} to his friend command")
    public void the_second_join_a_menu_at_euros_from_to_his_friend_command(Integer int1, String string, Double double1, String string2) {
       order3= new Order(string2);
        restaurantManager.add_restaurant(new Restaurant(string2));
        Menu menu=new Menu(string,double1);
        for(int i=0;i<int1;i++){
            order3.add_menu(menu);
        }
     orderManager.placeOrder(user3.getEmail(),order3, orderId2);
        orderManager.payOrders(user2.getEmail(),CreditCard);
        orderManager.payOrders(user3.getEmail(),CreditCard);
    }
    @Then("Both users can get discount after paying they have additional credit depending on their order amount")
    public void both_users_can_get_discount_after_paying_they_have_additional_credit_depending_on_their_order_amount() {
        double expectedOrderTotal = order2.getTotalPrice() ;
        assertEquals(45.0, expectedOrderTotal, 0.001);
        double expectedOrderTotal2 = order3.getTotalPrice() ;
        assertEquals(105.0, expectedOrderTotal2, 0.001);
        expected=user2.getCredit();
        Assert.assertEquals(6.75, expected,0.001);
        expected2=user3.getCredit();
        Assert.assertEquals(15.75, expected2,0.001);
    }
    @Given("user3 {string} with {double} credit")
    public void user3_with_credit(String string, Double double1) {
        user4=new User(string,"elodie",Role.CUSTOMER_STUDENT);
        user4.setCredit(double1);
        orderManager.userManager.getUserList().add(user4);



    }
    @When("user add {int} {string} at {double} from {string} to deliver at {string}")
    public void user_add_at_from_to_deliver_at(Integer int1, String string, Double double1, String string2, String string3) {
        restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(new Restaurant(string2));

        restaurant.setCapacity(10);
        order4=new Order(string2);
       Menu menu=new Menu(string,double1);
       for(int i=0;i<int1;i++){
           order4.add_menu(menu);
       }
       orderId3=orderManager.placeOrder(user4.getEmail(),order4,Locations.HALL_PRINCIPAL);
       orderManager.payOrders(user4.getEmail(),CreditCard);
    }
    @Then("he   should not receive a  a discount of {int}% and his credit should stay {double}")
    public void he_should_not_receive_a_a_discount_of_and_his_credit_should_stay(Integer int1, Double double1) {

        double expectedOrderTotal3= order4.getTotalPrice() ;
        assertEquals(50.0, expectedOrderTotal3, 0.001);
        expected=user4.getCredit();
        Assert.assertEquals(expected, 5.0,0.001);
    }


}

