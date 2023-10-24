package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.Order;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DeliveryValidation {



    @Given("The campus user {string} has confirmed receipt of their order")
    public void the_campus_user_has_confirmed_receipt_of_their_order(String string, Order order) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("The delivery man {string} wants to validate the delivery in turn")
    public void the_delivery_man_wants_to_validate_the_delivery_in_turn(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The order statue updates as delivered")
    public void the_order_statue_updates_as_delivered() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }
}
