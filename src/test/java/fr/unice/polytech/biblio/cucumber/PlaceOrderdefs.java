package fr.unice.polytech.biblio.cucumber;

import fr.unice.polytech.Order;
import fr.unice.polytech.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class PlaceOrderdefs {
    User user;
    Order order ;

    @Given("order")
    public void order() {
         user =new User("user1@gmail.com","user");
        throw new io.cucumber.java.PendingException();
    }

    @Given("having a chicken nuggets menu")
    public void having_a_chicken_nuggets_menu() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("a Restaurant {string} available from {int}:{int} to {int}:{int} in store")
    public void a_restaurant_available_from_to_in_store(String string, Integer int1, Integer int2, Integer int3, Integer int4) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("available Restaurant {string}")
    public void available_restaurant(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Given("client choose a chicken nuggets menu")
    public void client_choose_a_chicken_nuggets_menu() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the order status is placed")
    public void the_order_status_is_placed() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the Restaurant manager validate order")
    public void the_restaurant_manager_validate_order() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("a deliveryMan was chosen  to pick up the order")
    public void a_delivery_man_was_chosen_to_pick_up_the_order() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @When("the client pays the order with credit card")
    public void the_client_pays_the_order_with_credit_card() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("exception thrown {string}")
    public void exception_thrown(String string) {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("the order is not assigned to a preparation")
    public void the_order_is_not_assigned_to_a_preparation() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }


}
