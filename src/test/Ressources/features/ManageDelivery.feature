Feature: Managing delivery personnel as an administrator

  Background:
    Given an administrator user

  Scenario: Adding a delivery person
    When I want to add a delivery person
    Then my list of available delivery personnel is increased by 1
    And the specified delivery person is marked as available

  Scenario: Removing a delivery person
    When I want to remove a delivery person
    Then the delivery person disappears from the list of delivery personnel

  Scenario: Adding a delivery location
    When I want to add a delivery location
    Then my list of delivery locations is increased by 1

  Scenario: Removing a delivery location
    When I want to remove a delivery location
    Then the location disappears from the list of available locations
