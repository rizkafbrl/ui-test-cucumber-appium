package com.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import com.example.support.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private AndroidDriver<MobileElement> driver;

    @Given("I launch the app")
    public void iLaunchTheApp() {
        DriverManager.initializeDriver();
        driver = DriverManager.getDriver();
    }

    @Then("I should see the title {string}")
    public void iShouldSeeTheTitle(String expectedTitle) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/productTV")));
        String actualTitle = element.getText();
        assertEquals(expectedTitle, actualTitle);
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}