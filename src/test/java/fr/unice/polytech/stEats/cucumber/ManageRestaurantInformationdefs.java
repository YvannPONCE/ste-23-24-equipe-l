package fr.unice.polytech.stEats.cucumber;

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
import java.util.Optional;

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
    @When("I add a new item to the menu:{string} priced at {int}")
    public void i_add_a_new_item_to_the_menu_priced_at(String string, Integer int1) {
        restaurant.getListemenu().add(new Menu("chicken nuggets", 8));
        Menu menuwings=new Menu(string,int1);
        restaurant.getListemenu().add(menuwings);

    }
    @And("I remove the item {string} from the menu")
    public void i_remove_the_item_from_the_menu(String string) {
        for (Menu menu : restaurant.getListemenu()){
            if(menu.getItemName().equals(string)){



                
                restaurant.getListemenu().remove(menu);
            }
        }



    }
    @Then("I should see the updated menu with {string} priced at {int}")
    public void i_should_see_the_updated_menu_with_priced_at(String string2, Integer int1) {
        Assertions.assertEquals(restaurant.getListemenu().get(0).getItemName(),string2);
        Assertions.assertEquals(Optional.of(Integer.valueOf((int) restaurant.getListemenu().get(0).getPrice())), Optional.of(int1));

    }
}
