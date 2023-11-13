Feature: Complete multi order

  Background:
    Given restaurants "Mcdonald" and "kfc" use st-eats and one delivery man "Alberto@gmail.com"

  Scenario: manage store hours for a day
    Given the user "maxime@gmail.com" order a "Maxibestof" at "Mcdonald" for 7.50 euros
    Given the user "magali@gmail.com" order a "nugets5" at "kfc" for 5.50 euros on his friend order
    And the user "maxime@gmail.com" pay his order
    And the user "magali@gmail.com" pay his order in second
    And The simple order is marked ready by the restaurant "Mcdonald"
    And The simple order is marked ready by the restaurant "kfc" in second
    When user "maxime@gmail.com" confirm the delivery
    And delivery man "Alberto@gmail.com" confirm the delivery
    Then The group order is marked as closed