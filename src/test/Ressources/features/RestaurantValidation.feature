Feature: Order Management for Restaurant Staff

  Background:
    Given the restaurant "MyRestaurant" is open
    And there are pending orders:
      | Order Number | Customer Name | Status         |Capmus Location
      | 1            | John Doe      | Awaiting Prep |batiment E
      | 2            | Jane Smith    | Awaiting Prep |batiment D
      | 3            | Alice Johnson | Ready for Pickup |batiment C

  Scenario: Restaurant staff consults orders awaiting preparation
    When the restaurant staff logs in
    And selects "View Orders"
    Then they should see the following orders:
      | Order Number | Customer Name | Status         |Campus Location
      | 1            | John Doe      | Awaiting Prep |batiment E
      | 2            | Jane Smith    | Awaiting Prep |batiment D
    And they should not see the order with status "Ready for Pickup"

  Scenario: Restaurant staff validates orders ready for pickup
    When the restaurant staff logs in
    And selects "View Orders"
    And selects an order with status "Ready for Pickup"
    And and validate Order
    Then the order status should change to "Validated"
    And they should see a success message "Order validated successfully"
    And send notification to deliveryMan

