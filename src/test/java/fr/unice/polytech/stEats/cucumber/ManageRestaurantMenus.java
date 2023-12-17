package fr.unice.polytech.stEats.cucumber;

import fr.unice.polytech.Enum.MenuType;
import fr.unice.polytech.Menu;
import fr.unice.polytech.Restaurant.Restaurant;
import fr.unice.polytech.RestaurantManager.RestaurantManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

public class ManageRestaurantMenus {
    private Restaurant restaurant;
    @Given("a restaurant manager for {string}")
    public void a_restaurant_manager_for(String restaurantName) {
        Restaurant restaurant = new Restaurant(restaurantName);
        this.restaurant = restaurant;
    }
    @When("the restaurant manager adds a basic menu named {string} for {int}€")
    public void the_restaurant_manager_adds_a_basic_menu_named_for_€(String menu_name, Integer menu_price) {
        Menu menu = new Menu(menu_name, menu_price, MenuType.BASIC_MENU);
        this.restaurant.addMenu(menu);
    }
    @Then("the basic menu {string} should be added to the restaurant {string} at {int}€")
    public void the_basic_menu_should_be_added_to_the_restaurant_at_€(String menu_name, String restaurant_name, Integer menu_price) {
        Assert.assertEquals(restaurant_name, this.restaurant.getName());
        Menu addedMenu = this.restaurant.getMenu(menu_name);
        Assert.assertEquals(menu_name, addedMenu.getItemName());
        Assert.assertEquals(menu_price, addedMenu.getPrice(), 0.01);
        Assert.assertEquals(MenuType.BASIC_MENU, addedMenu.getMenuType());
    }

    @When("the restaurant manager adds an Afterwork menu named {string}")
    public void the_restaurant_manager_adds_an_afterwork_menu_named(String menu_name) {
        Menu menu = new Menu(menu_name, 100000, MenuType.AFTERWORK_MENU);
        this.restaurant.addMenu(menu);
    }
    @Then("the afterwork menu {string} should be added to the restaurant {string}")
    public void the_afterwork_menu_should_be_added_to_the_restaurant(String menu_name, String restaurant_name) {
        Assert.assertEquals(restaurant_name, this.restaurant.getName());
        Menu addedMenu = this.restaurant.getMenu(menu_name);
        Assert.assertEquals(menu_name, addedMenu.getItemName());
        Assert.assertEquals(0, addedMenu.getPrice(), 0.01);
        Assert.assertEquals(MenuType.AFTERWORK_MENU, addedMenu.getMenuType());
    }

    @When("the restaurant manager adds a buffet menu named {string} for {int}€")
    public void the_restaurant_manager_adds_a_buffet_menu_named_for_€(String menu_name, Integer menu_price) {
        Menu menu = new Menu(menu_name, menu_price, MenuType.BUFFET_MENU);
        this.restaurant.addMenu(menu);
    }
    @Then("the buffet menu {string} should be added to the restaurant {string} for {int}€")
    public void the_buffet_menu_should_be_added_to_the_restaurant_for_€(String menu_name, String restaurant_name, Integer menu_price) {
        Assert.assertEquals(restaurant_name, this.restaurant.getName());
        Menu addedMenu = this.restaurant.getMenu(menu_name);
        Assert.assertEquals(menu_name, addedMenu.getItemName());
        Assert.assertEquals(menu_price, addedMenu.getPrice(), 0.01);
        Assert.assertEquals(MenuType.BUFFET_MENU, addedMenu.getMenuType());
    }
}
