Feature: Placing an Order at Restaurant "chickentacky"  with Discounts and Future Credits

  Background:
    Given the discount threshold "n" is set to 10
    And the discount rate "r%" is set to 15
    And the user has a credit balance of $0.00

  Scenario: James place an order at Restaurant chickentacky  and receives a discount
    Given the user is on the Restaurant menu
    When the user selects "Menu" and adds 12 items ton his  order
    And proceeds to checkout
    Then he  should see a discount of 15% applied to the order total
    And the order status should be "Pending"


  Scenario: User places an order with sub-orders and receives discounts
    Given the user is on the Restaurant "chickentacky"
    When the user selects "Menu" and adds 6 items to their order
    And they create a sub-order with 5 items
    And proceeds to checkout
    Then they should see a discount of 15% applied to both the main order and the sub-order
    And the order status should be "Pending"

  Scenario: User places an order and receives a credit for future orders
    Given the user has a credit balance of $0.00
    And the user previously received a 15% discount on their order
    When the user places a new order
    Then they should see a credit of 15% applied to the new order's total
    And the order status should be "Pending"
    And their credit balance should be $15.00

  Scenario: User places an order with multiple credits
    Given the user has a credit balance of $15.00
    And the user previously received discounts of 15% and 10% on previous orders
    When the user places a new order
    Then they should see a credit of 25% (15% + 10%) applied to the new order's total
    And the order status should be "Pending"
    And their credit balance should be $25.00

  Scenario: User places a smaller order and does not receive a discount
    Given the user is on the Restaurant "chickentacky"
    When the user selects "Menu" and adds 8 items to their order
    And proceeds to checkout
    Then they should not see a discount applied to the order total
    And the order status should be "Pending"
