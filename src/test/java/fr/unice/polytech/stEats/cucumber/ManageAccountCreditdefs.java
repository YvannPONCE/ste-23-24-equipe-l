package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.User;
import fr.unice.polytech.UserManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.Optional;
import java.util.UUID;





public class ManageAccountCreditdefs {

    UUID orderId;
    UserManager mockuserManager;
    User user;
    private Double userCredit;


    @Given("user {string} with {double} credit")
    public void user_with_credit(String string, Double double1) {
        user=new User(string,"james", Role.CUSTOMER_STUDENT);
        user.setCredit(double1);
    }
    @When("user want to check his credit")
    public void user_want_to_check_his_credit() {
        userCredit=user.getCredit();
    }
    @Then("we get the credit")
    public void we_get_the_credit() {
        Assert.assertEquals(Optional.ofNullable(userCredit),Optional.of(5.00));
    }
    @When("user get a {double} refund")
    public void user_get_a_refund(Double double1) {

        user.addCredit(double1);

    }
    @Then("user credit become {double}")
    public void user_credit_become(Double double1) {
        Assert.assertEquals(Optional.ofNullable(user.getCredit()),Optional.of(double1));

    }

}
