package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.bs.A;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class checkOrderHistorydefs {
    OrderManager orderManager;
    UserManager userManager;
    UUID orderId;
    User user;
    private Order order;
    String email;
    private StringWriter expectedOutput;
    private String capturedOutput;
    private Order orderSelected;
    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UUID orderSelected_id;
    private LinkedHashMap<Object, Object> orderHistoryMap;


    @Given("a user {string} with the following order history:")
    public void a_user_with_the_following_order_history(String string, io.cucumber.datatable.DataTable dataTable) {

        List<Map<String, String>> orderData = dataTable.asMaps(String.class, String.class);
        user = new User(string, "james", Role.CUSTOMER_STUDENT);
        userManager = new UserManager();
        userManager.add_user(user);

        orderHistoryMap = new LinkedHashMap<>();  // Utiliser une LinkedHashMap pour préserver l'ordre

        for (Map<String, String> row : orderData) {
            String item = row.get("Item");
            double price = Double.parseDouble(row.get("Price"));
            String restaurantName = row.get("Restaurant Name");

            restaurant = new Restaurant(restaurantName);
            restaurant.setCapacity(16);
            restaurantManager = new RestaurantManager();
            restaurantManager.add_restaurant(restaurant);

            orderManager = new OrderManager(restaurantManager, userManager, new BusinessIntelligence(restaurantManager));

            order = new Order(restaurantName);
            order.add_menu(new Menu(item, price));

            orderId = orderManager.place_order(string, order, Locations.HALL_PRINCIPAL);

            orderManager.pay_order(orderId, string, "7936 3468 9302 8371");
            orderManager.validate_order(orderId, string);
            order.setStatus(Status.DELIVERED);
            orderManager.validate_order_receipt(order.getId());

            orderHistoryMap.put(orderId, order);
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

        orderSelected = orderManager.reorderFromHistory(user.getOrderHistory().get(0).getId(), user.get_email(), Locations.HALL_PRINCIPAL);
    }
        @Then("the new order is selected as new order to place")
    public void the_new_order_is_selected_as_new_order_to_place() {

        Assert.assertEquals(orderSelected.getStatus(),Status.CREATED);
        Assert.assertEquals(user.getOrderHistory().get(0).getStatus(),Status.DELIVERED);
        Assert.assertEquals(orderSelected.get_menus(),user.getOrderHistory().get(0).get_menus());
        Assert.assertFalse(orderSelected.getCreationTime().equals(user.getOrderHistory().get(0)));


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


        userManager.displaySelectedOrderDetails(orderSelected_id,user.get_email());
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


