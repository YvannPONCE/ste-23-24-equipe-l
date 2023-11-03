Feature: Display Order History

  Background:
    Given a user "user@example.com" with the following order history:
      | Item          | Price  | Restaurant Name |
      | chicken nuggets | 8.50   | chicken tacky   |
      | pasta           | 9.50   | luigi           |

  Scenario: Display Order History
    When the user wants to view their order history
    Then the order history is displayed
