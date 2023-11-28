Feature: User consult his statistics

  Background:
    Given the application is running with two restaurant "KFC" and "Mcdonald"

  Scenario: User consult his favorite restaurant
    Given User "melanie@egmail.com" order a "bigTasty" at 7 euros at "Mcdonald"
    And  User "melanie@egmail.com" order a "KFcBucket" at 10 euros at "KFC"
    And User "melanie@egmail.com" order a "bigTasty" at 7 euros at "Mcdonald"
    When when "melanie@egmail.com" consult her favorites restaurants
    Then "melanie@egmail.com" sees that her favorite restaurant is "Mcdonald" following by "KFC"

  Scenario: User consult the best locations
    Given the restaurant "KFC" has complete 2 orders to Polytech Hall
    When As a student I want to consult the most popular delivery locations
    Then As a student I see that Polytech Hall has been choosen 2 times

  Scenario: User consult the total number of command
    Given User "melanie@egmail.com" order a "bigTasty" at 7 euros at "Mcdonald"
    And  User "melanie@egmail.com" order a "KFcBucket" at 10 euros at "KFC"
    And User "melanie@egmail.com" order a "bigTasty" at 7 euros at "Mcdonald"
    When when "melanie@egmail.com" consult her total number of orders
    Then "melanie@egmail.com" sees that she made 3 orders