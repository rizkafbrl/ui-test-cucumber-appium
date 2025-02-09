package com.example.stepdefinitions;

import com.example.support.DriverManager;
import com.example.support.HelperCommons;
import com.example.support.PermissionsHelper;
import com.example.support.WaitTimes;
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
        // The base package name for the Android app
        static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";
        
        // Menu-related element IDs
        static final String MENU_LIST = BASE_PACKAGE + ":id/menuRV";
        static final String MENU_ITEM_TEXT = BASE_PACKAGE + ":id/itemTV";
        static final String PRODUCT_TITLE = BASE_PACKAGE + ":id/productTV";
        static final String WEB_VIEW_TITLE = BASE_PACKAGE + ":id/webViewTV";
        
        // Common XPath patterns
        static final String MENU_ITEM_PATTERN = "//android.widget.TextView[@resource-id='" + MENU_ITEM_TEXT + "' and @text='%s']";
        static final String PAGE_TITLE_PATTERN = "//android.widget.TextView[contains(@text, '%s')]";
    }
    
    /**
     * Special pages that require additional permissions
     */
    private static class SpecialPages {
        static final String LOCATION_PAGE = "Geo Location Page";
        static final String BIOMETRIC_PAGE = "Fingerprint Page";
    }
    
    // Core components
    private final AndroidDriver<MobileElement> driver;
    private final HelperCommons helpers;
    private final PermissionsHelper permissions;
    private final WebDriverWait waiter;
    private final Map<String, Runnable> pagePermissions;

    public HomepageMenuStepDefinitions() {
        // Initialize core components
        this.driver = DriverManager.getDriver();
        this.helpers = new HelperCommons(driver);
        this.permissions = new PermissionsHelper(driver);
        this.waiter = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        
        // Set up permission requirements for special pages
        this.pagePermissions = new HashMap<>();
        pagePermissions.put(SpecialPages.LOCATION_PAGE, permissions::grantLocationPermission);
        pagePermissions.put(SpecialPages.BIOMETRIC_PAGE, permissions::bypassBiometricAuthentication);
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
            .withFailMessage("The menu items should be visible but are not")
            .isTrue();
    }

    @When("User tap on {string} menu item")
    public void tapMenuItem(String menuItemName) {
        String menuItemPath = String.format(PageElements.MENU_ITEM_PATTERN, menuItemName);
        clickWhenReady(By.xpath(menuItemPath));
    }

    @Then("User should be redirected to {string} page")
    public void verifyCurrentPage(String expectedPage) {
        // Handle any special permissions the page might need
        handlePagePermissions(expectedPage);
        
        // Find and verify the page title
        MobileElement pageTitle = findPageTitle(expectedPage);
        assertThat(pageTitle.isDisplayed())
            .withFailMessage("Expected to see page titled '%s' but it's not visible", expectedPage)
            .isTrue();

        // Make sure the menu has closed properly
        checkMenuIsClosed();
    }

    /**
     * Tries multiple ways to find the page title element
     */
    private MobileElement findPageTitle(String pageTitle) {
        List<By> possibleLocators = Arrays.asList(
            // Try finding by exact text match
            By.xpath(String.format(PageElements.PAGE_TITLE_PATTERN, pageTitle)),
            
            // Try finding by product title
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                PageElements.PRODUCT_TITLE, pageTitle
            )),
            
            // Try finding by description
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").descriptionContains(\"%s\")",
                PageElements.PRODUCT_TITLE, pageTitle
            )),
            
            // Try finding in web view
            MobileBy.AndroidUIAutomator(String.format(
                "new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                PageElements.WEB_VIEW_TITLE, pageTitle
            ))
        );

        // Try each locator until we find the element
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
                "Could not find the page title: " + pageTitle));
    }

    /**
     * Handles any special permissions required for specific pages
     */
    private void handlePagePermissions(String pageName) {
        if (pagePermissions.containsKey(pageName)) {
            pagePermissions.get(pageName).run();
        }
    }

    /**
     * Verifies that the menu drawer has closed properly
     */
    private void checkMenuIsClosed() {
        try {
            boolean menuHidden = waiter.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id(PageElements.MENU_LIST)));
            assertThat(menuHidden)
                .withFailMessage("The menu should be closed but is still visible")
                .isTrue();
        } catch (Exception e) {
            throw new AssertionError("The menu didn't close properly", e);
        }
    }

    /**
     * Waits for an element to be visible and returns it
     */
    private WebElement waitUntilVisible(By locator) {
        return waiter.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Waits for an element to be clickable and clicks it
     */
    private void clickWhenReady(By locator) {
        WebElement element = waiter.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}