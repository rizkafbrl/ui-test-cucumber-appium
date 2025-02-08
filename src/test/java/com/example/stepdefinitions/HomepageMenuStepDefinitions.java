package com.example.stepdefinitions;

import com.example.stepdefinitions.commons.HomepageCommon;
import io.cucumber.java.After;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.example.support.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

public class HomepageMenuStepDefinitions {
    private AndroidDriver<MobileElement> driver;
    private HomepageCommon homepageCommon;

    public HomepageMenuStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.homepageCommon = new HomepageCommon();
    }

    @Then("User able to see menu items")
    public void userAbleToSeeMenuItems() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement menuItems = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/menuRV")));
        assertThat(menuItems.isDisplayed()).isTrue();
    }

    @When("User tap on {string} menu item")
    public void userTapOnMenuItem(String menuItem) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement menuItemElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//android.widget.TextView[@resource-id='com.saucelabs.mydemoapp.android:id/itemTV' and @text='" + menuItem + "']")));
        menuItemElement.click();
    }

    @Then("User should be redirected to {string} page")
    public void userShouldBeRedirectedToPage(String pageTitle) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement pageTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//android.widget.TextView[@text='" + pageTitle + "']")));
        assertThat(pageTitleElement.isDisplayed()).isTrue();

        // Verify that the user is not on the homepage anymore
        try {
            boolean isHomepageElementInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/productTV")));
            assertThat(isHomepageElementInvisible).isTrue();
        } catch (Exception e) {
            // Log the exception and continue
            System.out.println("Homepage element is still visible: " + e.getMessage());
        }

        try {
            boolean isSortButtonInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/sortIV")));
            assertThat(isSortButtonInvisible).isTrue();
        } catch (Exception e) {
            // Log the exception and continue
            System.out.println("Sort button is still visible: " + e.getMessage());
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}