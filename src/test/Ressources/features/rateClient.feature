Feature: Rate client

  Background:
    Given the application is fully working

  Scenario: Delivery personnel rates the customer
    Given delivery personnel "deliveryGuy" logs into the delivery application
    When an order is attribute to the delivery man with user "user"
    And delivery personnel "deliveryGuy" enters a rating to "user" for the user of 4
    Then the rating by the delivery personnel is added to the reviews of user "user" with 4.0

  Scenario: Delivery personnel rates the customer
    Given delivery personnel "deliveryGuy" logs into the delivery application
    Given delivery personnel "deliveryGuy2" logs into the delivery application
    When an order is attribute to the delivery man with user "user"
    When an order is attribute to the delivery man with user "user"
    And delivery personnel "deliveryGuy" enters a rating to "user" for the user of 4
    And delivery personnel "deliveryGuy2" enters a rating to "user" for the user of 3
    Then the rating by the delivery personnel is added to the reviews of user "user" with 3.5
