Feature: Add order to group order

  Scenario: User have the same tastes
    Given One restaurant, One menu, two users "user1@exemple.com" and "user2@exemple.com"
    When The first user add a "maxibest" menu at 7.50 euros from "mcdonald"
    And  The second user add a "maxibest" menu at 7.50 euros from "mcdonald"
    Then Two "maxibest" menu from "mcdonald" are stored in the current order and cost 7.50 euros each.

  Scenario: Users have different tastes
    Given One restaurant, two menu, two users "user1@exemple.com" and "user2@exemple.com"
    When The first user add a "maxibest" menu at 7.50 euros from "mcdonald"
    And  The second user add a "maxibestplusplus" menu at 15.00 euros from "mcdonald"
    Then Both users can see "maxibest" and "maxibestplusplus" menus in the cart at 7.50 and 15.00.