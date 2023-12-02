package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import fr.unice.polytech.OrderManager.OrderManagerConnectedUser;
import fr.unice.polytech.OrderManager.OrderManager;
import org.junit.Assert;


public class Login {

    private User user;
    private UserManager userManager;

    @Given("the user with email {string} and a password of {string}")
    public void the_user_with_email_and_a_password_of(String userEmail, String userPassword) {
        user = new User(userEmail, userPassword);
        userManager = new UserManager();
        OrderManagerConnectedUser orderManager = new OrderManager(null, null, null, null);
        userManager.addOrderManager(orderManager);

    }
    @When("the user signin to the App")
    public void the_user_signin_to_the_app() {
        userManager.addUser(user);
    }

    @Then("the user can login and get the acess to orders")
    public void the_user_can_login_and_get_the_acess_to_orders() {
        OrderManagerConnectedUser orderManagerConnectedUser = userManager.logIn(user.getEmail(), user.getPassword());
        Assert.assertNotNull(orderManagerConnectedUser);
    }
}
