package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.BusinessIntelligence;
import fr.unice.polytech.DeliveryManager;
import fr.unice.polytech.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import fr.unice.polytech.OrderManager.OrderManager;

public class ConsultRestaurantOrders {

    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private UserManager userManager;
    private BusinessIntelligence businessIntelligence;
    private OrderManager orderManager;

    @Given("a restaurant {string}")
    public void a_restaurant(String restaurantName) {
        restaurant = new Restaurant(restaurantName);
        restaurantManager = new RestaurantManager();
        restaurantManager.addRestaurant(restaurant);

        userManager = new UserManager();
        businessIntelligence = new BusinessIntelligence();
        orderManager = new OrderManager(restaurantManager,userManager,businessIntelligence );
        deliveryManager = new DeliveryManager(orderManager, userManager);
        orderManager.addDeliveryManager(deliveryManager);
    }
    @Given("the restaurant has complete {int} orders of a {string} menu at {double} euros")
    public void the_restaurant_has_complete_orders_of_a_menu_at_euros(Integer int1, String string, Double double1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @When("the staff wants to consult the current orders")
    public void the_staff_wants_to_consult_the_current_orders() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
    @Then("they retrieve the list with the {int} orders")
    public void they_retrieve_the_list_with_the_orders(Integer int1) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

}
