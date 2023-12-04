Feature: Ristournes des Restaurants

  Scenario: User pays full price for first order on restaurant
    Given a new restaurant "chickenTacky"
    When User orders for the first time "BoxMaster" for 10.0
    Then User pays 10.0

    Scenario: User Orders 10 times from same restaurant
      Given a user orders 9 times from the same restaurant
      When User orders for the 10th time "BoxMaster" for 10.0
      Then User recieves discount of 15% for a period of 15 days

    Scenario: User Benefits from Discount at restaurant
      Given a user has a discount of 15% for a period of 15 days
      When User orders "BoxMaster" for 10.0
      Then User pays discounted price of 8.5