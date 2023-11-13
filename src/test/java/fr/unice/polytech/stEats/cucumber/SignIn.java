package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class SignIn {

    UserManager userManager;
    String user_email;
    String user_password;

    @Given("a user with email {string} and a password of {string}")
    public void a_user_with_email_and_a_password_of(String user_email, String user_password) {
        this.user_email = user_email;
        this.user_password = user_password;

        userManager = new UserManager();
    }
    @When("the user signin")
    public void the_user_signin() {
        userManager.signIn(user_email, user_password);
    }
    @Then("the user is present in the user base")
    public void the_user_is_present_in_the_user_base() {
        Assert.assertNotEquals(null, userManager.get_user(user_email));
    }
}
