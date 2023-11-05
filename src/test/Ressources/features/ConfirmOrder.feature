Feature: Confirm order

  Scenario: User successfully confirms order of many items
    Given a user "user@example.com" added to their cart many items from many restaurants with many prices
      | Restaurant    | Menu Item  | Price |
      | Chicken Tacky | BoxMain    | 7.50  |
      | Chicken Tacky | BoxDessert | 2.50  |
      | Chicken Tacky | BoxDrink   | 1.50  |
      | Mcdonald      | maxibest   | 7.50  |
    When the user pays their order
    Then all the orders will be transmitted to the restaurant



  Scenario: user confirms order but payment fails
    Given a user "user@example.com" added to their cart a "maxibest" at 7.50 from "mcdonald"
    When the user pays their order and payment fails
    Then the order will not be transmitted to the restaurant


  Scenario: basic confirmation
    Given user "user@exemple.com" added to his cart a "maxibest" at 7.50 from "mcdonald"
    When user "user@exemple.com" pay his command
    Then The order "maxibest" at 7.50 from "mcdonald" has been transmit to the restaurant

