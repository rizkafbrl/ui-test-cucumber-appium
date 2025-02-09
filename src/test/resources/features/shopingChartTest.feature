@userShopingCart
Feature: Shopping Cart 
  
  @regression @positive @TC_SC_01
  Scenario: User Able To Access Shopping Cart Page
    Given User on homepage
    When User taps on the shopping cart icon
    Then User should be navigated to the shopping cart page
  
  @regression @negative @TC_SC_02
  Scenario: User with empty cart should see an empty shopping cart message
    Given User on homepage
    When User taps on the shopping cart icon
    Then User should be navigated to the shopping cart page
    When User has no items in the cart
    Then User should see an empty shopping cart message
  
  @regression @negative @TC_SC_03
  Scenario: User with empty cart should see and able to activate the "Go Shopping" button
    Given User on homepage
    When User taps on the shopping cart icon
    Then User should be navigated to the shopping cart page
    When User has no items in the cart
    Then User should see an empty shopping cart message
    Then User should see the "Go Shopping" button
    And User should be able to tap the "Go Shopping" button
    Then User should be redirected to "Products" page
  
  @regression @negative @TC_SC_04
  Scenario: User without cart items should not see item count on homepage cart icon
    Given User on homepage
    When User has no items in the cart
    Then The cart item counter should not be visible
