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

    // Common method to find username and password fields
    private WebElement getUsernameField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/nameET")));
    }

    private WebElement getPasswordField() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/passwordET")));
    }

    // Common method to clear and enter text into fields
    private void enterText(WebElement field, String text) {
        field.clear();
        field.sendKeys(text);
    }

    // Common method to tap the login button
    private void tapLoginButton() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/loginBtn")));
        loginButton.click();
    }

    // Common method to check if user is redirected to the homepage
    private void verifyHomepageRedirect() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement homepageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/homepage")));
        MobileElement homepage = (MobileElement) homepageElement; // Cast to MobileElement
        assertThat(homepage.isDisplayed()).isTrue();
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

    @Then("User should be redirected to the homepage")
    public void userShouldBeRedirectedToHomepage() {
        verifyHomepageRedirect();
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
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement usernameError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/nameErrorTV")));
        WebElement passwordError = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/passwordErrorTV")));
        assertThat(usernameError.isDisplayed()).isTrue();
        assertThat(passwordError.isDisplayed()).isTrue();
    }

    @When("User is on the homepage")
    public void userIsOnHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement homepageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/homepage")));
        assertThat(homepageElement.isDisplayed()).isTrue();
    }

    @When("User taps on the menu button")
    public void userTapsOnMenuButton() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement menuButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/menuBtn")));
        menuButton.click();
    }

    @Then("User should be able to see menu items")
    public void userShouldSeeMenuItems() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement menuItem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/menuItem")));
        assertThat(menuItem.isDisplayed()).isTrue();
    }

    @When("User taps on {string} menu item")
    public void userTapsMenuItem(String menuItemName) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//android.widget.TextView[@text='" + menuItemName + "']")));
        menuItem.click();
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }
}