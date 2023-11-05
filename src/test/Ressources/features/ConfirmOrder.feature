Feature: Confirm order

  Scenario: user confirms order but payment fails
    Given a user "user@example.com" added to their cart a "maxibest" at 7.50 from "mcdonald"
    When the user "user@example.com" pays their order and payment fails
    Then the order "maxibest" at 7.50 from "mcdonald" will not be transmitted to the restaurant


  Scenario: basic confirmation
    Given user "user@exemple.com" added to his cart a "maxibest" at 7.50 from "mcdonald"
    When user "user@exemple.com" pay his command
    Then The order "maxibest" at 7.50 from "mcdonald" has been transmit to the restaurant

