Feature: Consult statistics


  Scenario:
    Given "manager@gmail.com" is a restaurant manager
    Given his restaurant "kfc" has complete multiple orders
    When i want to consult the volume of orders
    Then i get the number of orders since the begining

  Scenario:
    Given "manager@gmail.com" is a restaurant manager
    Given his restaurant "kfc" has complete multiple orders
    When I want to consult the most popular delivery locations
    Then I see a list of delivery locations and their popularity

  Scenario:
    Given "manager@gmail.com" is a restaurant manager
    Given his restaurant "kfc" has complete multiple orders
    When I want to consult the most popular menus
   Then The manager get the most populars menus