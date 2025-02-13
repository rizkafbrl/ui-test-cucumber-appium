@homepageSortingItem
Feature: Navigating Sorting Widget And Sorting Button Items

  @regression @positive @TC_HG_SRT_01
  Scenario: Sort products by name in ascending order on homepage
    Given User on homepage
    When User tap on sorting button
    Then User able to see filter widget on homepage
    When User tap on "Name - Ascending" option
    Then the products should be sorted with "Name - Ascending"
    And the filter widget should be closed

  @regression @positive @TC_HG_SRT_02
  Scenario: Sort products by name in descending order on homepage
    Given User on homepage
    When User tap on sorting button
    Then User able to see filter widget on homepage
    When User tap on "Name - Descending" option
    Then the products should be sorted with "Name - Descending"
    And the filter widget should be closed

  @regression @positive @TC_HG_SRT_03
  Scenario: Sort products by price in ascending order on homepage
    Given User on homepage
    When User tap on sorting button
    Then User able to see filter widget on homepage
    When User tap on "Price - Ascending" option
    Then the products should be sorted with "Price - Ascending"
    And the filter widget should be closed

  @regression @positive @TC_HG_SRT_04
  Scenario: Sort products by price in descending order on homepage
    Given User on homepage
    When User tap on sorting button
    Then User able to see filter widget on homepage
    When User tap on "Price - Descending" option
    Then the products should be sorted with "Price - Descending"
    And the filter widget should be closed