Feature: Notifying Restaurant Staff for New Order Sold

  Background:
    Given The application is readyy

  Scenario: Restaurant staff receives notification for a new order sold
    When a customer places a new order through the system
    Then staff members receive a notification for this new order
