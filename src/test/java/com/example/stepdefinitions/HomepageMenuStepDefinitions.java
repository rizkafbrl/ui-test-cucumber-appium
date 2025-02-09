package com.example.stepdefinitions;

import com.example.support.DriverManager;
import com.example.support.HelperCommons;
import com.example.support.PermissionsHelper;
import com.example.support.WaitTimes;
import com.google.common.collect.ImmutableMap;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

public class HomepageMenuStepDefinitions {
    
    private static class PageElements {
        static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";
        static final String MENU_LIST = BASE_PACKAGE + ":id/menuRV";
        static final String MENU_ITEM_TEXT = BASE_PACKAGE + ":id/itemTV";
        static final String PRODUCT_TITLE = BASE_PACKAGE + ":id/productTV";
        static final String WEB_VIEW_TITLE = BASE_PACKAGE + ":id/webViewTV";
        
        static final String MENU_ITEM_PATTERN = "//android.widget.TextView[@resource-id='" + MENU_ITEM_TEXT + "' and @text='%s']";
        static final String PAGE_TITLE_PATTERN = "//android.widget.TextView[contains(@text, '%s')]";
    }
    
    // Pages that need permission handling - exact names from feature files
    private static class PermissionPages {
        // From @TC_HG_TAP_04
        static final String GEO_LOCATION = "Geo Location";
        // From @TC_HG_TAP_08
        static final String FINGERPRINT = "FingerPrint";
    }
    
    private final AndroidDriver<MobileElement> driver;
    private final HelperCommons helpers;
    private final PermissionsHelper permissions;
    private final WebDriverWait waiter;

    public HomepageMenuStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helpers = new HelperCommons(driver);
        this.permissions = new PermissionsHelper(driver);
        this.waiter = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @Before
    public void prepareTest() {
        helpers.setUp();
    }

    @After
    public void cleanupTest() {
        helpers.tearDown();
    }
    
    @Then("User able to see menu items")
    public void verifyMenuItemsAreVisible() {
        WebElement menuList = waitUntilVisible(By.id(PageElements.MENU_LIST));
        assertThat(menuList.isDisplayed())
            .withFailMessage("Menu items are not visible")
            .isTrue();
    }

    @When("User tap on {string} menu item")
    public void tapMenuItem(String menuItemName) {
        String menuItemPath = String.format(PageElements.MENU_ITEM_PATTERN, menuItemName);
        clickWhenReady(By.xpath(menuItemPath));
    }

    @Then("User should be redirected to {string} page")
    public void verifyCurrentPage(String expectedPage) {
        // Check if this page needs special permission handling
        handlePermissionsIfNeeded(expectedPage);
        
        // Verify the page title is visible
        MobileElement pageTitle = findPageTitle(expectedPage);
        assertThat(pageTitle.isDisplayed())
            .withFailMessage("Page '%s' is not visible", expectedPage)
            .isTrue();

        // Verify menu is closed
        checkMenuIsClosed();
    }

    /**
     * Handles permissions for Geo Location and FingerPrint pages
     * Based on test cases TC_HG_TAP_04 and TC_HG_TAP_08
     */
    private void handlePermissionsIfNeeded(String pageName) {
        // Handle only the two pages that need permissions
        if (pageName.equals(PermissionPages.GEO_LOCATION)) {
            handleLocationPermission();
        }
        else if (pageName.equals(PermissionPages.FINGERPRINT)) {
            handleBiometricPermission();
        }
    }

    /**
     * Handles location permission for TC_HG_TAP_04
     */
    private void handleLocationPermission() {
        try {
            permissions.grantLocationPermission();
            System.out.println("Successfully granted location permission");
        } catch (Exception e) {
            System.out.println("Warning: Failed to grant location permission: " + e.getMessage());
        }
    }

    /**
     * Handles biometric permission for TC_HG_TAP_08
     */
    private void handleBiometricPermission() {
        try {
            // First try regular biometric bypass
            permissions.bypassBiometricAuthentication();
            
            // If biometric prompt is still visible, try hardware key
            if (isBiometricPromptVisible()) {
                driver.executeScript("mobile: pressKey", ImmutableMap.of("keycode", 66));
                
                // Wait up to 5 seconds for prompt to disappear
                new WebDriverWait(driver, 5)
                    .until(ExpectedConditions.invisibilityOfElementLocated(
                        By.xpath("//*[contains(@text, 'Fingerprint')]")));
            }
            
            System.out.println("Successfully handled biometric prompt");
        } catch (Exception e) {
            System.out.println("Warning: Failed to handle biometric prompt: " + e.getMessage());
        }
    }

    private boolean isBiometricPromptVisible() {
        try {
            return !driver.findElements(By.xpath("//*[contains(@text, 'Fingerprint')]")).isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    private MobileElement findPageTitle(String pageTitle) {
        List<By> possibleLocators = Arrays.asList(
            By.xpath(String.format(PageElements.PAGE_TITLE_PATTERN, pageTitle)),
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                PageElements.PRODUCT_TITLE, pageTitle
            )),
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").descriptionContains(\"%s\")",
                PageElements.PRODUCT_TITLE, pageTitle
            )),
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                PageElements.WEB_VIEW_TITLE, pageTitle
            ))
        );

        return possibleLocators.stream()
            .map(locator -> {
                try {
                    return (MobileElement) waitUntilVisible(locator);
                } catch (Exception e) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                "Could not find page title: " + pageTitle));
    }

    private void checkMenuIsClosed() {
        try {
            boolean menuHidden = waiter.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id(PageElements.MENU_LIST)));
            assertThat(menuHidden)
                .withFailMessage("Menu is still visible when it should be closed")
                .isTrue();
        } catch (Exception e) {
            throw new AssertionError("Menu did not close properly", e);
        }
    }

    private WebElement waitUntilVisible(By locator) {
        return waiter.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickWhenReady(By locator) {
        WebElement element = waiter.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}