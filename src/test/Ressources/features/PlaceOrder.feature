Feature: place an order

  Background:
    Given order
    And having a chicken nuggets menu
    And a Restaurant "chicketacky" available from 11:00 to 23:00 in store

  Scenario: order is paid and deliveryMan available
    Given available Restaurant "chicketaky"
    And client choose a chicken nuggets menu
    When the client pays the order with credit card amount 45
    Then the order status is placed
    And the amount of credit card became 35
    And the Restaurant manager validate order
    And a deliveryMan was chosen  to pick up the order



  Scenario: order is not placed : invalid payment amount credit card
    Given available Restaurant "chicketaky"
    And client choose a chicken nuggets menu
    When the client pays the order with credit card amount 45 and expiration date 01|05|2030
    Then exception thrown "payment fail"
    And the order is not assigned to a preparation


