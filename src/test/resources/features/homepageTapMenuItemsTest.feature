@homepageNavigateViaMenuItem
Feature: Navigating through the homepage menu items

  @regression @positive @TC_HG_TAP_01
  Scenario: Open Product Catalog from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Catalog" menu item
    Then User should be redirected to "Products" page

  @regression @positive @TC_HG_TAP_02
  Scenario: Open WebView from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "WebView" menu item
    Then User should be redirected to "Webview" page

  @regression @positive @TC_HG_TAP_03
  Scenario: Open QR Code Scanner from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "QR Code Scanner" menu item
    Then User should be redirected to "QR Code Scanner" page

  @regression @positive @TC_HG_TAP_04
  Scenario: Open Geo Location from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Geo Location" menu item
    Then User should be redirected to "Geo Location" page

  @regression @positive @TC_HG_TAP_05
  Scenario: Open Drawing from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Drawing" menu item
    Then User should be redirected to "Drawing" page

  @regression @positive @TC_HG_TAP_06
  Scenario: Open About from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "About" menu item
    Then User should be redirected to "About" page

  @regression @positive @TC_HG_TAP_07
  Scenario: Open Reset App State from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Reset App State" menu item
    Then User should be redirected to "Reset App State" page

  @regression @positive @TC_HG_TAP_08
  Scenario: Open FingerPrint from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "FingerPrint" menu item
    Then User should be redirected to "FingerPrint" page

  @regression @positive @TC_HG_TAP_09
  Scenario: Open Virtual USB from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Virtual USB" menu item
    Then User should be redirected to "Virtual USB" page

  @regression @positive @TC_HG_TAP_10
  Scenario: Open Crash app (debug) from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Crash app (debug)" menu item
    Then User should be redirected to "Crashes" page

  @regression @positive @TC_HG_TAP_11
  Scenario: Open Log In from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page