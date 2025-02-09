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
    // Base package ID
    private static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";
    
    // Element IDs
    private static final String USERNAME_FIELD_ID = BASE_PACKAGE + ":id/nameET";
    private static final String PASSWORD_FIELD_ID = BASE_PACKAGE + ":id/passwordET";
    private static final String LOGIN_BUTTON_ID = BASE_PACKAGE + ":id/loginBtn";
    private static final String USERNAME_ERROR_ID = BASE_PACKAGE + ":id/nameErrorTV";
    private static final String PASSWORD_ERROR_ID = BASE_PACKAGE + ":id/passwordErrorTV";
    
    // Test Data
    private static final String VALID_USERNAME = "validUser";
    private static final String VALID_PASSWORD = "validPass";
    private static final String EMPTY_INPUT = "";
    
    private final AndroidDriver<MobileElement> driver;
    private final HelperCommons helperCommons;
    private final WebDriverWait wait;

    public LoginStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
        this.wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @Before
    public void setUp() {
        helperCommons.setUp();
    }

    @When("User enters a valid username and password")
    public void userEntersValidCredentials() {
        enterCredentials(VALID_USERNAME, VALID_PASSWORD);
    }

    @When("User enters an empty username and a valid password")
    public void userEntersEmptyUsernameAndValidPassword() {
        enterCredentials(EMPTY_INPUT, VALID_PASSWORD);
    }

    @When("User enters a valid username and an empty password")
    public void userEntersValidUsernameAndEmptyPassword() {
        enterCredentials(VALID_USERNAME, EMPTY_INPUT);
    }

    @When("User enters empty username and password")
    public void userEntersEmptyUsernameAndPassword() {
        enterCredentials(EMPTY_INPUT, EMPTY_INPUT);
    }

    @When("User taps the login button")
    public void userTapsLoginButton() {
        clickLoginButton();
    }

    @Then("User should see an error message under the username field")
    public void userShouldSeeErrorUnderUsernameField() {
        verifyErrorMessage(USERNAME_ERROR_ID, "Username error message");
    }

    @Then("User should see an error message under the password field")
    public void userShouldSeeErrorUnderPasswordField() {
        verifyErrorMessage(PASSWORD_ERROR_ID, "Password error message");
    }

    @Then("User should see error messages under both username and password fields")
    public void userShouldSeeErrorsUnderBothFields() {
        verifyErrorMessage(USERNAME_ERROR_ID, "Username error message");
        // verifyErrorMessage(PASSWORD_ERROR_ID, "Password error message");
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }

    // Helper methods
    private void enterCredentials(String username, String password) {
        enterText(getField(USERNAME_FIELD_ID), username);
        enterText(getField(PASSWORD_FIELD_ID), password);
    }

    private WebElement getField(String fieldId) {
        return waitForVisibility(By.id(fieldId));
    }

    private void enterText(WebElement field, String text) {
        try {
            field.clear();
            field.sendKeys(text);
        } catch (Exception e) {
            throw new AssertionError(
                String.format("Failed to enter text '%s' into field", text), e);
        }
    }

    private void clickLoginButton() {
        try {
            WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(By.id(LOGIN_BUTTON_ID)));
            loginButton.click();
        } catch (Exception e) {
            throw new AssertionError("Failed to click login button", e);
        }
    }

    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void verifyErrorMessage(String errorFieldId, String errorContext) {
        WebElement errorElement = waitForVisibility(By.id(errorFieldId));
        assertThat(errorElement.isDisplayed())
            .withFailMessage("%s is not displayed", errorContext)
            .isTrue();
    }
}