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

  Scenario: User rates the DeliveryGuy
    Given delivery personnel "deliveryGuy" logs into the delivery application
    When an order is attribute to the delivery man with user "user"
    When an order is attribute to the delivery man with user "user"
    And  user "user" enters a rating to "deliveryGuy" for the user of 4
    And user "user" enters a rating to "deliveryGuy" for the user of 3
    Then the rating by the delivery personnel is added to the reviews of user "deliveryGuy" with 3.5

  Scenario: User rates the Restaurant
    Given Restaurant "Restaurant" is disponible
    When an order is made in "Restaurant"
    When  an order is made in "Restaurant"
    And  user "user" enters a rating to "Restaurant" for the user of 4
    And user "user" enters a rating to "Restaurant" for the user of 3
    Then the rating by the user is added to the reviews of restaurant "Restaurant" with 3.5
