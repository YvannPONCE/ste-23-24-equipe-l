Feature: Validate Delivery

  Background: An order has been sent for delivery with the status ready

  Scenario:
    Given The campus user "user@exemple.com" has confirmed receipt of their order
    And The delivery man "deliveryman@exemple.com" is assigned to this order
    When The delivery man "deliveryman@exemple.com" wants to validate the delivery in turn
    Then The order statue of "user@exemple.com" updates as closed
    And The delivery man "deliveryman@exemple.com" become available for an new delivery