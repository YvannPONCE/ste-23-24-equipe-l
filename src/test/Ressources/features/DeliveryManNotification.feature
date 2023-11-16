Feature: Send Notification to delivery When order is ready
  Scenario: receive notification once the order is ready
    Given delivery man and user "user1@exemple.com" ordered from "chickenTacky"
  When the order is ready and restaurant market in the system
  Then the delivery lan receives a  notification with the user informations