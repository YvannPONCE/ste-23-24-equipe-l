Feature: Send Notification to delivery When order is ready
  Scenario: receive notification once the order is ready
    Given  user "user1@exemple.com" ordered from "chickenTacky"
  When the order is ready and restaurant validate the order for delivery
  Then the delivery lan receives a  notification with the user informations