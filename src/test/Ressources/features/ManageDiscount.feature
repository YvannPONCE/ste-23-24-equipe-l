Feature: Placing an Order at Restaurant "chickentacky"  with Discounts and Future Credits

  Background:
    Given the discount threshold "n" is set to 10
    And the discount rate "r%" is set to 15
    And  user "user@exemple.com" with 0.00 credit

  Scenario: user place an order at Restaurant chickentacky  and receives a discount
    When the user selects "Menu" and adds 12 items ton his  order
    And proceeds to checkout
    Then he  should see a discount of 15% applied to the order total
    And he should pay and get additional credit

  Scenario: User places an order with sub-orders and receives discounts
    Given the user is on the Restaurant X website
    When the user selects "Menu" and adds 6 items to their order
    And they create a sub-order with 5 items
    And proceeds to checkout
    Then they should see a discount of 15% applied to both the main order and the sub-order
    And the order status should be "Pending"



  Scenario: User places a smaller order and does not receive a discount
    Given the user is on the Restaurant X website
    When the user selects "Menu" and adds 8 items to their order
    And proceeds to checkout
    Then they should not see a discount applied to the order total
    And the order status should be "Pending"