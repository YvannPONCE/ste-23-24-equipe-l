package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.Menu;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.List;

public class ManageRestaurant {
    private Restaurant global_restaurant;
    private RestaurantManager global_restaurant_manager;
    @Given("a restaurant {string} that is always closed and that offers {string} for {double} euros exists")
    public void a_restaurant_that_is_always_closed_and_that_offers_for_euros_exists(String restaurant_name, String menu_name, Double menu_price) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        restaurant.getListemenu().add(menu);
        this.global_restaurant = restaurant;
    }
    @When("Admin adds the restaurant {string} to the restaurant manager")
    public void admin_adds_the_restaurant_to_the_restaurant_manager(String string) {
        RestaurantManager restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(global_restaurant);
        this.global_restaurant_manager = restaurantManager;
    }

    @Then("the restaurant {string} should be in the restaurant manager with menu {string} for {double} euros")
    public void the_restaurant_should_be_in_the_restaurant_manager_with_menu_for_euros(String restaurant_name, String menu_name, Double menu_price) {
        List<Restaurant> restaurants = global_restaurant_manager.getRestaurants();
        Assert.assertEquals(1, restaurants.size());
        Restaurant restaurant = restaurants.get(0);
        Assert.assertEquals(restaurant_name, restaurant.getName());
        Menu menu = restaurant.getListemenu().get(0);
        Assert.assertEquals(menu_name, menu.getItemName());
        Assert.assertEquals(menu_price, menu.getPrice(), 0.01);
    }

    @Given("the restaurant {string} is in the restaurant manager with menu {string} for {double} euros")
    public void the_restaurant_is_in_the_restaurant_manager_with_menu_for_euros(String restaurant_name, String menu_name, Double menu_price) {
        Restaurant restaurant = new Restaurant(restaurant_name);
        Menu menu = new Menu(menu_name, menu_price);
        restaurant.getListemenu().add(menu);
        this.global_restaurant = restaurant;
        RestaurantManager restaurantManager = new RestaurantManager();
        restaurantManager.add_restaurant(global_restaurant);
        this.global_restaurant_manager = restaurantManager;
    }
    @When("I remove the restaurant {string} from the restaurant manager")
    public void i_remove_the_restaurant_from_the_restaurant_manager(String restaurant_name) {
        this.global_restaurant_manager.remove_restaurant(restaurant_name);
    }
    @Then("the restaurant {string} should not be in the restaurant manager")
    public void the_restaurant_should_not_be_in_the_restaurant_manager(String restaurant_name) {
        Assert.assertNull(this.global_restaurant_manager.getRestaurant(restaurant_name));
    }
}
