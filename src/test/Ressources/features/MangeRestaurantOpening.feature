Feature: Restaurant Selection for Customer Orders

  Scenario: Displaying Open/Closed Status of Restaurants
    Given the current day is "lundi" and the time is 12:00 PM
    When a user "user1@exemple.com" wants to choose a restaurant to order
    Then we returned a list of opened restaurant and closed restaurant
