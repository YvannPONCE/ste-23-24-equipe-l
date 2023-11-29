Feature: Confirm order


  Scenario:
    Given the user with email "melanie@exemple.com" and a password of "password"
    When the user signin to the App
    Then the user can login and get the acess to orders