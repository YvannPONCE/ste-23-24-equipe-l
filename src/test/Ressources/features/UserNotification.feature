

Feature: Send Notification to user When order is validated and delivered

  Scenario: receive notification once the order is validated
    Given user "user1@example.com" ordered from "chickenTacky" a "chicken nuggets" menu
    When the order is validated
    Then user receives a notification with the order information

  Scenario: receive notification once the order is ready
    Given user "user1@example.com" ordered from "chickenTacky"
    When the order is validated
    Then user receives a notification with delivery information

  Scenario: receive notification once the order is delivered
    Given user "user1@example.com" ordered from "chickenTacky"
    When the order is delivered
    Then user receives a notificatin
