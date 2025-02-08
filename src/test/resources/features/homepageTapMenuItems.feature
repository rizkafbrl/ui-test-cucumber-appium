Feature: Navigating through the homepage menu items

  Scenario: Open Product Catalog from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Product" Catalog menu item
    Then User should be redirected to "Catalog" page

  Scenario: Open WebView from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Webview" menu item
    Then User should be redirected to "WebView" page

  Scenario: Open QR Code Scanner from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "QR Code Scanner" menu item
    Then User should be redirected to "QR Code Scanner" page

  Scenario: Open Geo Location from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Geo Location" menu item
    Then User should be redirected to "Geo Location" page

  Scenario: Open Drawing from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Drawing" menu item
    Then User should be redirected to "Drawing" page

  Scenario: Open About from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "About" menu item
    Then User should be redirected to "About" page

  Scenario: Open Reset App State from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Reset App State" menu item
    Then User should be redirected to "Reset App State" page

  Scenario: Open FingerPrint from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "FingerPrint" menu item
    Then User should be redirected to "FingerPrint" page

  Scenario: Open Virtual User from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Virtual User" menu item
    Then User should be redirected to "Virtual User" page

  Scenario: Open Crash app (debug) from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Crash app (debug)" menu item
    Then User should be redirected to "Crash app (debug)" page

  Scenario: Open Log In from homepage menu
    Given User on homepage
    When User tap on menu button
    Then User able to see menu items
    When User tap on "Log In" menu item
    Then User should be redirected to "Login" page