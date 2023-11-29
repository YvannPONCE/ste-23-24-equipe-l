Feature: Order cancellation as a customer

  Background:
    Given "test@test.com" is a customer
    And "test@test.com" has placed an order with "bigmac" at 7.5 euros from "mcdonald"

  Scenario: Successful cancellation within the specified timeframe
    When I request to cancel my order
    Then my order is successfully cancelled
    And I am refunded for my cancelled order

  Scenario: Attempting cancellation on an order in preparation
    When I request to cancel my order
    Then the cancellation is denied
    And my order remains unchanged