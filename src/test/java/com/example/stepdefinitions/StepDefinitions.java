package com.example.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class StepDefinitions {

    private AndroidDriver<MobileElement> driver;

    @Given("I launch the app")
    public void iLaunchTheApp() throws MalformedURLException, URISyntaxException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "emulator-5554");
        capabilities.setCapability("app", System.getProperty("user.dir") + "/apps/mda-2.2.0-25.apk");
        capabilities.setCapability("automationName", "UiAutomator2");
        capabilities.setCapability("appPackage", "com.saucelabs.mydemoapp.android");
        capabilities.setCapability("appActivity", "com.saucelabs.mydemoapp.android.view.activities.SplashActivity");

        driver = new AndroidDriver<>(new URI("http://localhost:4723/").toURL(), capabilities);
    }

    @Then("I should see the title {string}")
    public void iShouldSeeTheTitle(String expectedTitle) {
        MobileElement element = driver.findElementByAccessibilityId("title");
        String actualTitle = element.getText();
        assertEquals(expectedTitle, actualTitle);
        driver.quit();
    }
}