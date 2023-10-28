Feature: Confirm order

  Background:
    given a registered user with a cart full of items

  Scenario: user successfully confirms order of one item
    Given the order number "1234" containing menu item "BoxMain" from "Chicken Tacky" of user "user@etu.unice.fr" is not confirmed yet
    When The user wants to confirm his order of "BoxMain"
    And The user pays for his order
    And The Payment is successful
    Then The status of the order "1234" passes from Processing to paid

  Scenario: User successfully confirms order of many items
    Given the cart
    | Restaurant | Menu Item |
    | Chicken Tacky | BoxMain |
    | Chicken Tacky | BoxDessert |
    | Chicken Tacky | BoxDrink |
    | Chicken Tacky | BoxSide |
    When The user wants to confirm his order
    And The user pays for his order
    And The payment is successful
    Then The status of the order "1234" passes from Processing to paid

  Scenario: user confirms order but payment fails
    Given the order number "1234" containing menu item "BoxMain" from "Chicken Tacky" of user "user@etu.unice.fr" is not confirmed yet
    Given the order number "1234" of user "user@etu.unice.fr" is not confirmed yet
    When The user wants to confirm his order "1234"
    And The user pays for his order
    And The payment fails
    Then The status of the order "1234" Stays Processing


  Scenario: basic confirmation
    Given user "user@exemple.com" added to his cart a "maxibest" at 7.50 from "mcdonald"
    When user "user@exemple.com" pay his command
    Then The order "maxibest" at 7.50 from "mcdonald" has been transmit to the restaurant

