

Feature: Send Notification to user When order is validated and delivered

  Scenario: receive notification once the order is validated
    Given user "user1@example.com" ordered from "chickenTacky" a "chicken nuggets" menu
    When the order is validated
    Then user receives a notification with the order information

  Scenario: receive notification once the order is ready
    Given registred user  "user2@example.com" ordered from "chickenTacky" and
    When the order is ready for delivery
    Then user receives a notification with delivery information

  Scenario: receive notification once the order is delivered
    Given user "user1@example.com" ordered from "chickenTacky" and the order was delivered
    When user validate  order  receipt
    Then user receives a notificatin
