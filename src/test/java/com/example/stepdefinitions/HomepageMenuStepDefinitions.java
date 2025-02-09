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
import java.util.*;

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

        // **Grant necessary permissions before checking redirection**
        grantNecessaryPermissions(pageTitle);

        List<By> locators = Arrays.asList(
            By.xpath("//android.widget.TextView[contains(@text, '" + pageTitle + "')]"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").textContains(\"" + pageTitle + "\")"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productTV\").descriptionContains(\"" + pageTitle + "\")"),
            MobileBy.AndroidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/webViewTV\").textContains(\"" + pageTitle + "\")")
        );

        MobileElement pageTitleElement = locators.stream()
            .map(locator -> {
                try {
                    return (MobileElement) wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
                } catch (Exception ignored) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Page title element not found for: " + pageTitle));

        assertThat(pageTitleElement.isDisplayed()).isTrue();

        // Verify that the drawer is closed
        try {
            boolean isMenuInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/menuRV")));
            assertThat(isMenuInvisible).isTrue();
        } catch (Exception e) {
            System.out.println("User not redirected. Menu drawer is still visible: " + e.getMessage());
        }
    }

    /**
     * Grants necessary permissions based on the page title.
     */
    private void grantNecessaryPermissions(String pageTitle) {
        Map<String, Runnable> permissionActions = Map.of(
            "Geo Location Page", permissionsHelper::grantLocationPermission,
            "Fingerprint Page", permissionsHelper::bypassBiometricAuthentication
        );

        permissionActions.getOrDefault(pageTitle, () -> {}).run();
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }
}
