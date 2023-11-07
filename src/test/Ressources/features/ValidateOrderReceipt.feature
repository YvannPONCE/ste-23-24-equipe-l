Feature: Validate Order Receipt


  Scenario: Validate Order Receipt
    Given user "user@exemple.com" as order a "chickennuggets" at 8.50 at "chickentacky" and as paid his command
    When user "user@exemple.com" confirm the receipt
    Then the order is marked as delivered
