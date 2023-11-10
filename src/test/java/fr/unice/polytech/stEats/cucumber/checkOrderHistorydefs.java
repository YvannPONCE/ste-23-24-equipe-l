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
    Restaurant mockRestaurant;
    UUID orderId;
    UserManager mockuserManager;
    User user;
    private Order order;
    String email;
    private StringWriter expectedOutput;
    private String capturedOutput;
    private Order orderSelected;


    @Given("a user {string} with the following order history:")
    public void a_user_with_the_following_order_history(String string, io.cucumber.datatable.DataTable dataTable) {
        List<Map<String, String>> orderData = dataTable.asMaps(String.class, String.class);
        user=new User(string,"james", Role.CUSTOMER_STUDENT);

        for (Map<String, String> row : orderData) {
            String item = row.get("Item");

            double price = Double.parseDouble(row.get("Price"));
            String restaurantName = row.get("Restaurant Name");
            RestaurantManager mockRestaurantManager = Mockito.mock(RestaurantManager.class);
            mockRestaurant = Mockito.mock(Restaurant.class);
            mockuserManager=Mockito.spy(UserManager.class);
            email=string;
            Mockito.when(mockRestaurantManager.get_restaurant(Mockito.anyString())).thenReturn(mockRestaurant);
            mockuserManager.getUserList().add(user);
            Mockito.when(mockRestaurantManager.get_restaurant(Mockito.anyString())).thenReturn(mockRestaurant);
            Mockito.when(mockuserManager.get_order_history(email)).thenReturn(user.getOrderHistory());
            Mockito.when(mockRestaurant.getName()).thenReturn(restaurantName);

            orderManager = new OrderManager(mockRestaurantManager);
            orderManager.userManager =mockuserManager;

            order = new Order(restaurantName);
            order.add_menu(new Menu(item,price));
            Mockito.when(mockRestaurant.getOrders()).thenReturn(Arrays.asList(order));


            orderId = orderManager.place_order(string, order, Locations.HALL_PRINCIPAL);

            orderManager.pay_order(orderId, string, "7936 3468 9302 8371");
            orderManager.validate_order(orderId,string);
            order.setStatus(Status.DELIVERED);
            orderManager.validate_order_receipt(string,order.getId());
        }
    }
    @When("the user wants to view their order history")
    public void the_user_wants_to_view_their_order_history() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));
        mockuserManager.displayOrderHistory(email);
        System.setOut(originalOut);
         capturedOutput = outputStream.toString();


    }
    @Then("the order history is displayed")
    public void the_order_history_is_displayed() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // Écrit la sortie standard
        System.out.println("Restaurant Name: luigi");
        System.out.println("Menu Name: pasta");
        System.out.println("Restaurant Name: chicken tacky");
        System.out.println("Menu Name: chicken nuggets");



        // Obtient la sortie standard capturée
        String capturedOutput = outputStream.toString();


        Assert.assertEquals(capturedOutput,capturedOutput);
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
}


