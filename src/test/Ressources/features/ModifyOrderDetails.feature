Feature: Modify Delivery Location and delivery time

   Background: User has ordered from mcdonald's  and has not yet paid

   Scenario: Modify Delivery Location
      Given User "user1@exemple.com" ordered a menu nuggets menu from mcdonald's the delivery Location was HALL_PRINCIPAL
      When the user wants to modify the delivery location to     BATIMENT_E,
      Then the location is modified

   Scenario: Modify Delivery Location but the order is paid
      Given User "user1@exemple.com" ordered a menu chicken burger  menu from quick mcdothe delivery Location was HALL_PRINCIPAL
      When the user wants to modify the delivery location to     BATIMENT_A,
      Then the request is rejected
   Scenario: Modify Delivery time
      Given User "user1@exemple.com" ordered a menu chicken burger  menu from quick at  deliverytime 12:00
      When the user wants to modify the chosen slot 13:30
      Then the time is modified
   Scenario: Modify Delivery time but the order is paid
      Given User "user1@exemple.com" ordered a menu chicken burger  menu from quick2 at  deliverytime 12:00
      When user try to modify the chosen slot 13:00
      Then the modification is rejected