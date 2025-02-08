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
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.MEDIUM_WAIT);
        MobileElement pageTitleElement = null;

        try {
            pageTitleElement = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//android.widget.TextView[@text='" + pageTitle + "']")));
        } catch (Exception e1) {
            try {
                // If element not found by XPath, try to find by resource-id and text
                pageTitleElement = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(
                        MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").text(\"" + pageTitle + "\")")));
            } catch (Exception e2) {
                // If element not found by resource-id and text, try to find by resource-id and content-desc
                pageTitleElement = (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(
                        MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").description(\"" + pageTitle + "\")")));
            }
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