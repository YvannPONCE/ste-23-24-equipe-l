package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.MenuType;
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
    private UUID orderSelectedID;
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

            oldOrder = new Order(restaurantName);
            oldOrder.add_menu(new Menu(item, price, MenuType.BASIC_MENU));
            orderId = orderManager.placeOrder(user.getEmail(), oldOrder, Locations.HALL_PRINCIPAL);

            orderManager.payOrders(user.getEmail(), "7936 3468 9302 8371");
            orderManager.processingOrder(orderId, restaurant.getName());
            orderManager.setOrderReady(orderId, oldOrder.getRestaurantName());
            deliveryManger.validateOrder(orderId);
        }
    }

    @When("user choose a order from history")
    public void user_choose_a_order_from_history() {
        String restaurantName = user.getOrderHistory().keySet().iterator().next();
        orderId = orderManager.reorderFromHistory(this.oldOrder.getId(), user.getEmail(), Locations.HALL_PRINCIPAL);
    }
    @Then("the new order is selected as new order to place")
    public void the_new_order_is_selected_as_new_order_to_place() {
        Order orderSelected = orderManager.getCurrentOrders(orderId).getOrders(user.getEmail()).get(0);

        Assert.assertEquals(Status.CREATED, orderSelected.getOrderState().getStatus());
        Assert.assertEquals(this.oldOrder.getMenus(), orderSelected.getMenus());
        Assert.assertFalse(orderSelected.getCreation_time().equals(this.oldOrder));


    }

    @When("the user select order in {string}")
    public void the_user_select_order_in(String string) {
       orderSelectedID=orderId;

    }
    @Then("the order history is displayed with all informations")
    public void the_order_history_is_displayed_with_all_informations() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));


        userManager.displaySelectedOrderDetails(orderSelectedID,user.getEmail());
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


