Feature: Placing an Order at Restaurant "chickenTacky"  with Discounts and Future Credits

  Background:
   Given  user "user@exemple2.com" with 0.00 credit and the discount threshold is set to 10

  Scenario: user place an order at Restaurant chickenTacky  and receives a discount
    When the user selects "Menu" and adds 12 items ton his  order
    Then he  should see a discount of 15% applied to the order total and get additional credit

  Scenario: Group order of more than 10 item both users got the discount
    Given One restaurant, two menu, two users "user1@exemple.com" and "user2@exemple.com" waiting in "hall principale" with 0.00 credit
    When The first user add a 6 "maxibest" menu at 7.50 euros from "mcdonald" to deliver at "hall principale"
    And  The second join 7 a "maxibestplusplus" menu at 15.00 euros from "mcdonald" to his friend command
    Then Both users can get discount after paying they have additional credit depending on their order amount


  Scenario: user  place an order at Restaurant chickenTacky  and don't receive a discount
    Given user3 "user3@exemple.com" with 5.00 credit
    When user add 5 "chickenwings" at 10.00 from "chickenTacky" to deliver at "hall principale"
    Then he   should not receive a  a discount of 15% and his credit should stay 0.00
