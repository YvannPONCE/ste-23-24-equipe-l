Feature: Validate Order Receipt


  Scenario: Validate Order Receipt
    Given user "user@exemple.com" as order a "chickennuggets" at 8.50 at "chickentacky" and as paid his commad
    And the order was delivered
    When I confirm the receipt
    Then the order is added to the order history
