package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.DeliveryManager.DeliveryManager;
import fr.unice.polytech.DeliveryManager.DeliveryManagerCampusManager;
import fr.unice.polytech.Enum.Role;
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
import io.cucumber.java.mk_latn.No;
import org.junit.Assert;

import java.util.UUID;

public class ManageDelivery {

    private OrderManager orderManager;
    private UserManager userManager;
    User user;
    User deliveryMan;
    private Restaurant restaurant;
    private RestaurantManager restaurantManager;
    private NotificationCenter notificationCenter;
    private DeliveryManagerCampusManager deliveryManagerCampusManager;

    @Given("an administrator user")
    public void an_administrator_user() {
        user = new User("admin", "admin", Role.CAMPUS_MANAGER);
        deliveryMan = new User("deliveryMan", "deliveryMan", Role.DELIVER_MAN);
        userManager=new UserManager();
        userManager.add_user(user);
        restaurantManager = new RestaurantManager();
        notificationCenter = new NotificationCenter(userManager);
        orderManager = new OrderManager(restaurantManager, userManager, new StatisticsManager(restaurantManager), null, notificationCenter);
        deliveryManagerCampusManager = new DeliveryManager(orderManager ,userManager, notificationCenter);
    }

    @When("I want to add a delivery person")
    public void i_want_to_add_a_delivery_person() {
        userManager.addUser(new User(deliveryMan.getEmail(), deliveryMan.getPassword(), Role.DELIVER_MAN));
    }

    @Then("my list of available delivery personnel is increased by {int}")
    public void my_list_of_available_delivery_personnel_is_increased_by(Integer int1) {
        Assert.assertTrue(userManager.getDeliveryMenID().size() >0);
    }

    @Then("the specified delivery person is marked as available")
    public void the_specified_delivery_person_is_marked_as_available() {
        Assert.assertNotNull(deliveryManagerCampusManager.getDeliveryMenAvailability().get(deliveryMan.getEmail()));
    }

    @When("I want to remove a delivery person")
    public void i_want_to_remove_a_delivery_person() {
        userManager.deleteUser(deliveryMan.getEmail());
    }

    @Then("the delivery person disappears from the list of delivery personnel")
    public void the_delivery_person_disappears_from_the_list_of_delivery_personnel() {
        Assert.assertEquals(0, deliveryManagerCampusManager.getDeliveryMenAvailability().size());
    }

    @When("I want to add a delivery location")
    public void i_want_to_add_a_delivery_location() {
        deliveryManagerCampusManager.addDeliveryLocation("Batiment F");
    }

    @Then("my list of delivery locations is increased by {int}")
    public void my_list_of_delivery_locations_is_increased_by(Integer int1) {
        Assert.assertFalse(deliveryManagerCampusManager.getLocations().isEmpty());
        Assert.assertEquals("Batiment F", deliveryManagerCampusManager.getLocations().get(0));
    }

    @When("I want to remove a delivery location")
    public void i_want_to_remove_a_delivery_location() {
        deliveryManagerCampusManager.deleteDeliveryLocation("Batiment F");
    }

    @Then("the location disappears from the list of available locations")
    public void the_location_disappears_from_the_list_of_available_locations() {
        Assert.assertTrue(deliveryManagerCampusManager.getLocations().isEmpty());
    }


}
