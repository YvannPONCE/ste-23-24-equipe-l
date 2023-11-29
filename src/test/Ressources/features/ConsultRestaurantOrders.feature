Feature: Consult restaurant orders

  Scenario:
    Given a restaurant "KFC"
    And the restaurant has complete 2 orders of a "bucket" menu at 7.5 euros
    When the staff wants to consult the current orders
    Then they retrieve the list with the 2 orders