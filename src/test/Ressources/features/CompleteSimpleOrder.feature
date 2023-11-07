
Feature: Complete simple order

  Scenario: manage store hours for a day
    Given user "maxime@gmail.com" order a "Maxibestof" at "Mcdonald" for 7.50 euros
    And "maxime@gmail.com" pay his order
    And The order is marked ready by the restaurant "Mcdonald"
    When The user "maxime@gmail.com" confirm the delivery
    And The delivery man "deliver@gmail.com" confirm the delivery
    Then The order is marked as closed