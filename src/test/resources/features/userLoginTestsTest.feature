@userLogin
Feature: User login functionality

  @regression @positive @TC_USR_LG_01
  Scenario: User able to login with valid credentials
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page
    When User enters a valid username and password
    And User taps the login button
    Then User should be redirected to "Products" page

  @regression @negative @TC_USR_LG_02
  Scenario: User unable to login if username is empty
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page
    When User enters an empty username and a valid password
    And User taps the login button
    Then User should see an error message under the username field

  @regression @negative @TC_USR_LG_03
  Scenario: User unable to login if password is empty
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page
    When User enters a valid username and an empty password
    And User taps the login button
    Then User should see an error message under the password field

  @regression @negative @TC_USR_LG_04
  Scenario: User unable to login if both username and password are empty
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page
    When User enters empty username and password
    And User taps the login button
    Then User should see error messages under both username and password fields
