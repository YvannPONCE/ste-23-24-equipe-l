
Feature: Restaurant Opening Hours and Menu

  Background:
    Given I am a Restaurant manager
    And I want to manage Restaurant "chickentacky" scheduler


  Scenario: manage store hours for a day
    When I choose 11:30 as start time as 23:30 as end time for "lundi"
    Then I should see the Restaurant hours 11:30 to 23:30 for "lundi"

  Scenario: manage store hours for a week
    When I choose 11:00 as start time as 00:00 as end time for a week
    Then I should see the Restaurant hours for "lundi" "mardi" "mercredi" "jeudi" "vendredi" "samedi" "dimanche" from 11:00 to 00:00

  Scenario: change restaurant menu
    When I add a new item to the menu:"spicy wings" priced at 6
    And I remove the item "chicken nuggets" from the menu
    Then I should see the updated menu with "spicy wings" priced at 6