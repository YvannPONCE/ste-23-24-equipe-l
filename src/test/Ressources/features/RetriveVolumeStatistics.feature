
Feature: Retrieve volume statistics by hours and restaurant

  Background:
    Given The application is ready

  Scenario: manage store hours for a day
    Given The restaurant "MCDO" has complete 2 orders of "BIGMAC" at 12 h et 13 h
    Given The restaurant "BK" has complete 1 orders of "STEACKHOUSE" at 13 h
    Then The restaurant manager "Igor" can see 2 orders in "MCDO" and 1 order for "BK"

  Scenario: manage store hours for a week
    Given The restaurant "MCDO" has complete 2 orders of "BIGMAC" at 12 h et 13 h
    Given The restaurant "BK" has complete 1 orders of "STEACKHOUSE" at 13 h
    Then a user can see 2 orders at 13 and 1 order at 12