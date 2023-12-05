Feature: Modify Delivery Location and delivery time

   Background: User has built an order and has not yet paid

   Scenario: Modify Delivery Location
      Given a  has built an order
      And the user has not yet paid
      When the user wants to modify the delivery location
      Then the location is modified
