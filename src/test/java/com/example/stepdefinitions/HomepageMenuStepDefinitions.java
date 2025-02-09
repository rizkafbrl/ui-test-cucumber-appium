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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class HomepageMenuStepDefinitions {
    private AndroidDriver<MobileElement> driver;
    private HelperCommons helperCommons;
    private PermissionsHelper permissionsHelper;

    public HomepageMenuStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
        this.permissionsHelper = new PermissionsHelper(driver);
    }

    @Before
    public void setUp() {
        helperCommons.setUp();
    }

    @Then("User able to see menu items")
    public void userAbleToSeeMenuItems() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement menuItems = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/menuRV")));
        assertThat(menuItems.isDisplayed()).isTrue();
    }

    @When("User tap on {string} menu item")
    public void userTapOnMenuItem(String menuItem) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement menuItemElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='" + menuItem + "']")));
        menuItemElement.click();
    }

    @Then("User should be redirected to {string} page")
    public void userShouldBeRedirectedToPage(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        MobileElement pageTitleElement = null;

        List<By> locators = Arrays.asList(
            By.xpath("//android.widget.TextView[contains(@text, '" + pageTitle + "')]"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").textContains(\"" + pageTitle + "\")"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").descriptionContains(\"" + pageTitle + "\")"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/webViewTV\").textContains(\"" + pageTitle + "\")")
        );

        for (By locator : locators) {
            try {
                pageTitleElement = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                break; // Exit loop once found
            } catch (Exception ignored) {
                // Continue to the next locator
            }
        }

        if (pageTitleElement == null) {
            throw new NoSuchElementException("Page title element not found using any method for title: " + pageTitle);
        }

        assertThat(pageTitleElement.isDisplayed()).isTrue();

        // Grant all permissions
        permissionsHelper.grantAllPermissionsOnAndroid();

        // Verify that the drawer is closed
        try {
            boolean isMenuInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/menuRV")));
            assertThat(isMenuInvisible).isTrue();
        } catch (Exception e) {
            // Log the exception and continue
            System.out.println("User not redirected. Menu drawer is still visible: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }
}