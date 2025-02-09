package com.example.stepdefinitions.commons;

import com.example.support.DriverManager;
import com.example.support.HelperCommons;
import com.example.support.WaitTimes;
import io.cucumber.java.en.Given;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.assertj.core.api.Assertions.assertThat;

public class HomepageCommon {

    private static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";
    private static final String PRODUCT_TITLE_ID = BASE_PACKAGE + ":id/productTV";
    private static final String EXPECTED_TITLE = "Products";

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait wait;
    private final HelperCommons helperCommons;

    public HomepageCommon() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
        this.wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @Given("User on homepage")
    public void userOnHomepage() {
        verifyPageTitle(PRODUCT_TITLE_ID, EXPECTED_TITLE);
    }

    private void verifyPageTitle(String elementId, String expectedTitle) {
        WebElement titleElement = waitForVisibility(By.id(elementId));
        String actualTitle = titleElement.getText();
        assertThat(actualTitle)
            .withFailMessage("Expected title to be '%s', but was '%s'", expectedTitle, actualTitle)
            .isEqualTo(expectedTitle);
    }

    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}
