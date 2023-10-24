Feature: Validate Delivery

  Background: An order has been sent for delivery with the status ready

  Scenario:
    Given The campus user "user@exemple.com" has confirmed receipt of their order
    When The delivery man "deliveryman@exemple.com" wants to validate the delivery in turn
    Then The order statue updates as closed