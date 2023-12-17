Feature: Ordering a Buffer

  Background:
    Given a restaurant that offers the buffet menu "Buffet Chicken Beef TexMex" for 50.0€

  Scenario: ordering an AfterWork
    Given a customer user "user@example.com"
    When the user places an order for the "Buffet Chicken Beef TexMex" menu
    Then the order should be placed, and the user should be charged 50.0€
