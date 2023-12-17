Feature: manage restaurant menus

  Background:
    Given a restaurant manager for "Restaurant 1"

  Scenario: add a basic menu
    When the restaurant manager adds a basic menu named "Menu 1" for 10€
    Then the basic menu "Menu 1" should be added to the restaurant "Restaurant 1" at 10€

  Scenario: add an Afterwork menu
    When the restaurant manager adds an Afterwork menu named "Menu 2"
    Then the afterwork menu "Menu 2" should be added to the restaurant "Restaurant 1"

  Scenario: add a buffet menu
    When the restaurant manager adds a buffet menu named "Menu 3" for 20€
    Then the buffet menu "Menu 3" should be added to the restaurant "Restaurant 1" for 20€