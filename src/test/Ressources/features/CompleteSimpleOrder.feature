
Feature: Complete simple order

  Background:
    Given a restaurant "Mcdonald" use stEats and a delivery man "Alberto@gmail.com"

  Scenario: manage store hours for a day
    Given user "maxime@gmail.com" order a "Maxibestof" at "Mcdonald" for 7.50 euros
    And "maxime@gmail.com" pay his order
    And The order is marked ready by the restaurant "Mcdonald"
    When The user "maxime@gmail.com" confirm the delivery
    And The delivery man "Alberto@gmail.com" confirm the delivery
    Then The order is marked as closed