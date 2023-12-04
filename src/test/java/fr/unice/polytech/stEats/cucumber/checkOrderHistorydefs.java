package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import fr.unice.polytech.NotificationCenter.NotificationCenter;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;
import fr.unice.polytech.statisticsManager.StatisticsManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.*;

public class checkOrderHistorydefs {
    OrderManager orderManager;
    UserManager userManager;
    UUID orderId;
    User user;
    private Order order;
    private String capturedOutput;
    private Order orderSelected;
    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UUID orderSelected_id;
    private DeliveryManager deliveryManger;
    private NotificationCenter notificationCenter;
    private Order oldOrder;


    @Given("a user {string} with the following order history:")
    public void a_user_with_the_following_order_history(String string, io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> orderData = dataTable.asMaps(String.class, String.class);
        userManager = new UserManager();
        restaurantManager = new RestaurantManager();
        user = new User(string, "james", Role.CUSTOMER_STUDENT);
        userManager.add_user(user);
        user = new User("deliver", "pass", Role.DELIVER_MAN);
        userManager.add_user(user);
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), null, notificationCenter);
        deliveryManger = new DeliveryManager(userManager, notificationCenter);
        orderManager.addDeliveryManager(deliveryManger);

        for (Map<String, String> row : orderData) {
            String item = row.get("Item");
            double price = Double.parseDouble(row.get("Price"));
            String restaurantName = row.get("Restaurant Name");

            restaurant = new Restaurant(restaurantName);
            restaurant.setCapacity(16);
            restaurantManager.add_restaurant(restaurant);

            notificationCenter = new NotificationCenter(userManager);

            order = new Order(restaurantName);
            order.add_menu(new Menu(item, price));

            orderId = orderManager.placeOrder(user.getEmail(), order, Locations.HALL_PRINCIPAL);

            orderManager.payOrder(orderId, user.getEmail(), "7936 3468 9302 8371");
            orderManager.processingOrder(orderId, restaurant.getName());
            orderManager.setOrderReady(orderId, order.getRestaurant_name());
            deliveryManger.validateOrder(orderId);
        }
    }

    @When("the user wants to view their order history")
    public void the_user_wants_to_view_their_order_history() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        System.setOut(originalOut);
        capturedOutput = outputStream.toString();


    }
     @Then("the order history is displayed")
        public void the_order_history_is_displayed() {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream originalOut = System.out;
            System.setOut(new PrintStream(outputStream));


            userManager.displayOrderHistory("user@example.com");
            String capturedOutput = outputStream.toString();
            StringBuilder expectedOutputBuilder = new StringBuilder();
            expectedOutputBuilder.append("Restaurant Name: chicken tacky\nMenu Name: chicken nuggets\n");
            expectedOutputBuilder.append("Restaurant Name: luigi\nMenu Name: pasta\n");
            String expectedOutput = expectedOutputBuilder.toString();
            capturedOutput = capturedOutput.replaceAll("\\s", "");
            expectedOutput = expectedOutput.replaceAll("\\s", "");

            Assert.assertEquals(expectedOutput, expectedOutput);
        }


    @When("user choose a order from history")
    public void user_choose_a_order_from_history() {
        String restaurantName = user.getOrderHistory().keySet().iterator().next();
        this.oldOrder = user.getOrderHistory().get(restaurantName).get(0);
        UUID  orderID = orderManager.reorderFromHistory(this.oldOrder.getId(), user.getEmail(), Locations.HALL_PRINCIPAL);
    }
    @Then("the new order is selected as new order to place")
    public void the_new_order_is_selected_as_new_order_to_place() {

        Assert.assertEquals(orderSelected.getOrderState().getStatus(),Status.CREATED);
        Assert.assertEquals(this.oldOrder.getOrderState().getStatus(),Status.CLOSED);
        Assert.assertEquals(orderSelected.getMenus(),this.oldOrder.getMenus());
        Assert.assertFalse(orderSelected.getCreation_time().equals(this.oldOrder));


    }

    @When("the user select order in {string}")
    public void the_user_select_order_in(String string) {
       orderSelected_id=orderId;

    }
    @Then("the order history is displayed with all informations")
    public void the_order_history_is_displayed_with_all_informations() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));


        userManager.displaySelectedOrderDetails(orderSelected_id,user.getEmail());
        String capturedOutput = outputStream.toString();
        StringBuilder expectedOutputBuilder = new StringBuilder();
        expectedOutputBuilder.append("Selected Order Details:\n");
        expectedOutputBuilder.append("Order Items:\n");
        expectedOutputBuilder.append("  - pasta,Price: 9.5\n"); // Ajoutez d'autres détails de la commande si nécessaire
        String expectedOutput = expectedOutputBuilder.toString();
        capturedOutput = capturedOutput.replaceAll("\\s", "");
        expectedOutput = expectedOutput.replaceAll("\\s", "");

        Assert.assertEquals(expectedOutput, capturedOutput);
    }
}


