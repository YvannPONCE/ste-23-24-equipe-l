Feature: Order Placement with Chosen Time Slot

  Scenario: Customer places an order with a chosen time slot
    Given user "user1@exemple" order at 15:00 at "chickenTacky"
    When user choose a  2 nuggets menu
    Then capacity at this restaurant should be 8


  Scenario: Customer places an order with a chosen time slot that is full
    Given user "user1@exemple" order at 16:00 at "chickenTacky" but the chosen slot is full
    When user order his demand is rejected
    Then we suggest next available slot
