Feature: Menu price adapt to user role

  Background:
    Given  The application is readyyy

  Scenario: Menu price for staff members are 15% less expensive
    Given A registered staffmember
    When the staff member get the menus of a restaurant
    Then the prices are 15 percent lower of the normal one

  Scenario: Menu price for admin members are 20% less expensive
    Given A registered admin member
    When the admin member get the menus of a restaurant
    Then the prices are 20 percent lower of the normal one

  Scenario: Menu price for delivery members are 10% less expensive
    Given A registered delivery person
    When the delivery member get the menus of a restaurant
    Then the prices are 10 percent lower of the normal one
