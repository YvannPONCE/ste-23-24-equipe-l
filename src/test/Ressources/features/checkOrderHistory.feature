Feature: Manage Order History

  Background:
    Given a user "user@example.com" with the following order history:
      | Item          | Price  | Restaurant Name |
      | chicken nuggets | 8.50   | chicken tacky   |
      | pasta           | 9.50   | luigi           |

  Scenario: Display Order History
    When the user wants to view their order history
    Then the order history is displayed
  Scenario: Choose order from history
    When user choose a order from history
    Then the new order is selected as new order to place
Scenario: Select order From history
  When the user select order in "chicken tacky"
  Then the order history is displayed with all informations

