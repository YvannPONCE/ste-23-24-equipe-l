package fr.unice.polytech.stEats.cucumber;


import fr.unice.polytech.RestaurantManager.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class ManageRestaurantOpeningdefs {


    private RestaurantManager restaurantmanager;
    private Restaurant restaurant1;
    private Restaurant restaurant2;
    private Restaurant restaurant3;
    private Restaurant restaurant4;
    private String day;
    private String time;
    private List<Restaurant> openRestaurants;
    private String currentTime;
    private Integer hour;
    private Integer minutes;
    private List<Restaurant> closedRestaurants;

    @Given("the current day is {string} and the time is {int}:{int} PM")
    public void the_current_day_is_and_the_time_is_pm(String string, Integer int1, Integer int2) {
        day=string;
        hour=int1;
        minutes=int2;
        restaurantmanager=new RestaurantManager();
        restaurant1=new Restaurant("chickenTacky");
        restaurant2=new Restaurant("chineseTay");
        restaurant3=new Restaurant("mcdonald");
        restaurant4=new Restaurant("lima");
        String openingTime = String.format("%02d:%02d", 9, 30);
        String closingTime = String.format("%02d:%02d", 21, 30);
        restaurant1.getHoraires().setOpeningHours(string, openingTime, closingTime);
        restaurant2.getHoraires().setOpeningHours(string, openingTime, closingTime);
        String openingTime2 = String.format("%02d:%02d", 12, 30);
        String closingTime2 = String.format("%02d:%02d", 21, 30);
        restaurant3.getHoraires().setOpeningHours(string, openingTime2, closingTime2);
        restaurant4.getHoraires().setOpeningHours(string, openingTime2, closingTime2);
        restaurantmanager.add_restaurant(restaurant1);
        restaurantmanager.add_restaurant(restaurant2);
        restaurantmanager.add_restaurant(restaurant3);
        restaurantmanager.add_restaurant(restaurant4);
    }
    @When("a user {string} wants to choose a restaurant to order")
    public void a_user_wants_to_choose_a_restaurant_to_order(String string) {

       openRestaurants=restaurantmanager.getRestaurantsByTimeAndDay(hour,minutes,day);
       closedRestaurants=restaurantmanager.getClosedRestaurantsByTimeAndDay(hour,minutes,day);
    }
    @Then("we returned a list of opened restaurant and closed restaurant")
    public void we_returned_a_list_of_opened_restaurant_and_closed_restaurant() {
        List<Restaurant> expectedrestaurants=new ArrayList<>();
        expectedrestaurants.add(restaurant1);
        expectedrestaurants.add(restaurant2);
        List<Restaurant> expectedrestaurants2=new ArrayList<>();
        expectedrestaurants2.add(restaurant3);
        expectedrestaurants2.add(restaurant4);
        Assert.assertEquals(openRestaurants.size(),2);
        Assert.assertEquals(openRestaurants,expectedrestaurants);
        Assert.assertEquals(closedRestaurants.size(),2);
      Assert.assertEquals(closedRestaurants,expectedrestaurants2);

    }


}
