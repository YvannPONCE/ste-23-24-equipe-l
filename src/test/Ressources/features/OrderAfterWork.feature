Feature: Ordering an AfterWork

  Background:
    Given a restaurant that offers the afterwork menu "AfterWork tequila jeudi 18h"

  Scenario: ordering an AfterWork
    Given a user "user@example.com"
    When the user places an order for the afterwork for 12 people
    Then the order should be placed and its status should be processing
