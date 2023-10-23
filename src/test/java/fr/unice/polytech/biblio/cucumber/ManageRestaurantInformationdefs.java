package fr.unice.polytech.biblio.cucumber;

import fr.unice.polytech.Enum.Role;
import fr.unice.polytech.Menu;
import fr.unice.polytech.Restaurant;
import fr.unice.polytech.User;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class ManageRestaurantInformationdefs {
    private User restaurantManager;
    private Restaurant restaurant;

    @Given("I am a Restaurant manager")
    public void i_am_a_restaurant_manager() {
        restaurantManager = new User("userManager@gmail.com", "james", Role.CUSTOMER_STAFF);
    }

    @And("I want to manage Restaurant {string} scheduler")
    public void i_want_to_manage_restaurant_scheduler(String name) {
        restaurant = new Restaurant(name);
    }

    @When("I choose {int}:{int} as start time as {int}:{int} as end time for {string}")
    public void i_choose_as_start_time_as_as_end_time_for(Integer int1, Integer int2, Integer int3, Integer int4, String string) {
        String openingTime = String.format("%02d:%02d", int1, int2);
        String closingTime = String.format("%02d:%02d", int3, int4);
        restaurant.getHoraires().setOpeningHours(string, openingTime, closingTime);
    }

    @Then("I should see the Restaurant hours {int}:{int} to {int}:{int} for {string}")
    public void i_should_see_the_restaurant_hours_to_for(Integer int1, Integer int2, Integer int3, Integer int4, String string) {
        String expectedOpeningTime = String.format("%02d:%02d", int1, int2);
        String expectedClosingTime = String.format("%02d:%02d", int3, int4);
        String actualHours = restaurant.getHoraires().getStoreHours(string);
        String expectedHours = expectedOpeningTime + " - " + expectedClosingTime;
        Assertions.assertEquals(actualHours, expectedHours);
    }

    @When("I choose {int}:{int} as start time as {int}:{int} as end time for a week")
    public void i_choose_as_start_time_as_as_end_time_for_a_week(Integer startHour, Integer startMinute, Integer endHour, Integer endMinute) {
        String openingTime = String.format("%02d:%02d", startHour, startMinute);
        String closingTime = String.format("%02d:%02d", endHour, endMinute);

        List<String> daysOfWeek = List.of("lundi", "mardi", "mercredi", "jeudi", "vendredi", "samedi", "dimanche");

        for (String day : daysOfWeek) {
            restaurant.getHoraires().setOpeningHours(day, openingTime, closingTime);
        }
    }

    @Then("I should see the Restaurant hours for {string} {string} {string} {string} {string} {string} {string} from {int}:{int} to {int}:{int}")
    public void i_should_see_the_restaurant_hours_for_from_to(
            String string, String string2, String string3, String string4, String string5, String string6, String string7,
            Integer startHour, Integer startMinute, Integer endHour, Integer endMinute
    ) {
        String[] expectedDays = {string, string2, string3, string4, string5, string6, string7};
        String expectedOpeningTime = String.format("%02d:%02d", startHour, startMinute);
        String expectedClosingTime = String.format("%02d:%02d", endHour, endMinute);

        for (String day : expectedDays) {
            String actualHours = restaurant.getHoraires().getStoreHours(day);
            String expectedHours = expectedOpeningTime + " - " + expectedClosingTime;
            Assertions.assertEquals(actualHours, expectedHours);
        }
    }
}
