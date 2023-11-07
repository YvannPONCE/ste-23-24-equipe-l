Feature: Confirm order


  Scenario:
    Given a user with email "myemail@example.com" and a password of "password"
    When the user signin
    Then the user is present in the user base
