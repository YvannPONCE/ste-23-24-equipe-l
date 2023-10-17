Feature: place an order

  Background:
    Given order
    And having a chicken nuggets menu
    And a Restaurant "chickentacky" available from 11:00 to 23:00 in store

  Scenario: order is paid and deliveryMan available
    Given available Restaurant "chickentacky"
    And client choose a chicken nuggets menu
    Then the order status is placed
    And the Restaurant manager validate order
    And a deliveryMan was chosen  to pick up the order



  Scenario: order is not placed : invalid payment amount credit card
    Given available Restaurant "chicketaky"
    And client choose a chicken nuggets menu
    When the client pays the order with credit card
    Then exception thrown "payment fail"
    And the order is not assigned to a preparation


