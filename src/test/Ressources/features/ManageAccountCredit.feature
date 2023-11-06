Feature: manage account credit

Background:
Given user "user@exemple.com" with 5.00 credit

    Scenario: check user credit
    When user want to check his credit
    Then we get the credit
    Scenario: update user Credit
      When user get a 6.00 refund
      Then user credit become 11.00


