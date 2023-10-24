Feature: Browsing order history

  Scenario:
    Given One restaurant, One menu and one user "user@exemple.com"
    When The user want to add a "maxibest" menu at 7.50 euros from "mcdonald" to his cart
    Then The "maxibest" menu from "mcdonald" is stored in the current order and cost 7.50 euros.

  Scenario:
    Given Two restaurants, two menus and one user "user@exemple.com"
    When The user want to add a "maxibest" menu at 7.50 euros from "mcdonald" to his cart and a "fullchedar" menu at 10.50 euros from "kfc" to his cart
    Then The "maxibest" menu from "mcdonald" is stored in the current order and cost 7.50 euros as well as the "fullchedar" menu at 10.50 euros from "kfc".

  Scenario:
    Given One restaurant, two menus and one user "user@exemple.com"
    When The user want to add a "maxibest" menu at 7.50 euros from "mcdonald" to his cart and a "minibest" menu at 5.50 euros to his cart
    Then The "maxibest" menu from "mcdonald" is stored in the current order and cost 7.50 euros as well as the "minibest" menu at 5.50 euros.

