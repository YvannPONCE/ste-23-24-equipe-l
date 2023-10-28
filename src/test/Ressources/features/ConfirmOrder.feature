Feature: Confirm order


  Scenario:
    Given user "user@exemple.com" added to his cart a "maxibest" at 7.50 from "mcdonald"
    When user "user@exemple.com" pay his command
    Then The order "maxibest" at 7.50 from "mcdonald" has been transmit to the restaurant
