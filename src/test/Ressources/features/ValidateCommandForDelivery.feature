Feature: Add menu to cart

  Scenario:
    Given user "user@exemple.com" as order a "maximenu" at 7.50 at "mcdonald" and as paid his command.
    When the restaurant has finish preprared the order
    Then The status of the order of "user@exemple.com" has change to READY.
