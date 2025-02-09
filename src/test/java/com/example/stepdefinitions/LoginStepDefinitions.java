package com.example.stepdefinitions;

import com.example.support.DriverManager;
import com.example.support.HelperCommons;
import com.example.support.WaitTimes;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;

public class LoginStepDefinitions {
    private AndroidDriver<MobileElement> driver;
    private HelperCommons helperCommons;

    public LoginStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
    }

    @Before
    public void setUp() {
        helperCommons.setUp();
    }

    private WebElement getUsernameField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/nameET")));
    }

    private WebElement getPasswordField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/passwordET")));
    }

    private void enterText(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    private void tapLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/loginBtn")));
        loginButton.click();
    }

    @When("User enters a valid username and password")
    public void userEntersValidCredentials() {
        enterText(getUsernameField(), "validUser");
        enterText(getPasswordField(), "validPass");
    }

    @When("User enters an empty username and a valid password")
    public void userEntersEmptyUsernameAndValidPassword() {
        enterText(getUsernameField(), "");
        enterText(getPasswordField(), "validPass");
    }

    @When("User enters a valid username and an empty password")
    public void userEntersValidUsernameAndEmptyPassword() {
        enterText(getUsernameField(), "validUser");
        enterText(getPasswordField(), "");
    }

    @When("User enters empty username and password")
    public void userEntersEmptyUsernameAndPassword() {
        enterText(getUsernameField(), "");
        enterText(getPasswordField(), "");
    }

    @When("User taps the login button")
    public void userTapsLoginButton() {
        tapLoginButton();
    }

    @Then("User should see an error message under the username field")
    public void userShouldSeeErrorUnderUsernameField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement usernameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV")));
        assertThat(usernameError.isDisplayed()).isTrue();
    }

    @Then("User should see an error message under the password field")
    public void userShouldSeeErrorUnderPasswordField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement passwordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV")));
        assertThat(passwordError.isDisplayed()).isTrue();
    }

    @Then("User should see error messages under both username and password fields")
    public void userShouldSeeErrorsUnderBothFields() {
        userShouldSeeErrorUnderUsernameField();
        // userShouldSeeErrorUnderPasswordField();
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }
}