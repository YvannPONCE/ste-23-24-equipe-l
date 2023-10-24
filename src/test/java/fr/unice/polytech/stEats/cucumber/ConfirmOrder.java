package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.*;
import fr.unice.polytech.Enum.Locations;
import fr.unice.polytech.Enum.Status;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.UUID;

public class ConfirmOrder {
    OrderManager orderManager = new OrderManager();
    User user;
    UUID order_id;

    PaymentSystem paymentSystem = new PaymentSystem();

    @Given("the cart")
    public void the_cart(Order order) {
        String user_email = "user@user.com";
        this.user = new User(user_email, user_email);
        throw new io.cucumber.java.PendingException();
    }

    @When("The user wants to confirm his order")
    public void the_user_wants_to_confirm_his_order() {
        // Write code here that turns the phrase above into concrete actions
        Order order = new Order("restaurant_name");
        order.add_menu(new Menu("BoxMain", 9999999));
        order_id = orderManager.place_order(user.get_email(), order, Locations.HALL_PRINCIPAL);
        throw new io.cucumber.java.PendingException();
    }

    @When("The user pays for his order")
    public void the_user_pays_for_his_order() {
        boolean paid = paymentSystem.pay("7936 3468 9302 8371", "john quinonas", "12/27", "123");
        throw new io.cucumber.java.PendingException();
    }

    @When("The payment is successful")
    public void the_payment_is_successful() {
        // Write code here that turns the phrase above into concrete actions
        throw new io.cucumber.java.PendingException();
    }

    @Then("The status of the order {string} passes from Processing to paid")
    public void the_status_of_the_order_passes_from_processing_to_paid(String string) {
        List<Order> ordr = orderManager.get_current_orders(order_id, "user@user.user");
        ordr.get(0).setStatus(Status.PAID);
        throw new io.cucumber.java.PendingException();
    }

}
