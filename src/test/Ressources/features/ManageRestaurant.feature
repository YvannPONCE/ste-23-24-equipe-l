Feature: Managing restaurant partnerships by admins

  Scenario: adding a restaurant to the restaurant manager
    Given a restaurant "chickenTacky" that is always closed and that offers "BoxMain" for 9.5 euros exists
    When Admin adds the restaurant "chickenTacky" to the restaurant manager
    Then the restaurant "chickenTacky" should be in the restaurant manager with menu "BoxMain" for 9.5 euros

  Scenario: removing a restaurant from the restaurant manager
    Given the restaurant "chickenTacky" is in the restaurant manager with menu "BoxMain" for 9.5 euros
    When I remove the restaurant "chickenTacky" from the restaurant manager
    Then the restaurant "chickenTacky" should not be in the restaurant manager