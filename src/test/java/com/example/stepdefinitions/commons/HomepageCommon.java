package com.example.stepdefinitions.commons;

import io.cucumber.java.en.Given;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.example.support.DriverManager;

import static org.junit.Assert.assertEquals;

public class HomepageCommon {
    private AndroidDriver<MobileElement> driver;

    public HomepageCommon() {
        this.driver = DriverManager.getDriver();
    }

    @Given("User on homepage")
    public void userOnHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, 30);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/productTV")));
        String actualTitle = element.getText();
        assertEquals("Products", actualTitle);
    }
}