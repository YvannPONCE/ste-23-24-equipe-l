

Feature: Manage Restaurant Capacity

  Scenario: Attempt to choose the 1:00 PM time slot
    Given user "user1@example.com" and Restaurant "chickentacky" has a capacity of 10 menus per hour and the time slot for 1:00 PM is full
    When user attempt to choose the 1:00 PM time slot
    Then user choice is rejected and the next available time slot is suggested

  Scenario: Attempt to order in a available slot
      Given user "user1@example.com" and Restaurant "luigi" has a capacity of 10 menus per hour with available slot
      When user order a "pasta" in the restaurant
      Then user have a created order status

  Scenario: Attempt to order in a available slot with two users
    Given user1 and user2 place orders in restaurant luigi
    When user order a "pasta" in the restaurant ans
    Then user have a created order status and restaurant capacity become 8

  Scenario: show update of retaurant capacity after receipt validation
    Given user "user4@example.com" ordered in a  Restaurant "mario" with a capacity of 10 menus per hour with available slot
    When user order a "pasta" and validate order receipt
    Then the restaurant is set to 8

