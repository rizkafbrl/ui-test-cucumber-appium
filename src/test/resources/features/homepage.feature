Feature: Sorting Homepage Item

  Scenario: Sort products by name in ascending order
    Given I am on the products page
    When I tap on "Name - Ascending" option
    Then the products should be sorted "Name - Ascending"
    And the "Name - Ascending" sort option should show a tick mark

  Scenario: Sort products by name in descending order
    Given I am on the products page
    When I tap on "Name - Descending" option
    Then the products should be sorted "Name - Descending"
    And the "Name - Descending" sort option should show a tick mark

  Scenario: Sort products by price in ascending order
    Given I am on the products page
    When I tap on "Price - Ascending" option
    Then the products should be sorted "Price - Ascending"
    And the "Price - Ascending" sort option should show a tick mark

  Scenario: Sort products by price in descending order
    Given I am on the products page
    When I tap on "Price - Descending" option
    Then the products should be sorted "Price - Descending"
    And the "Price - Descending" sort option should show a tick mark