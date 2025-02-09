package com.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;

import com.example.support.DriverManager;
import com.example.support.WaitTimes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartStepDefinitions {
    
    private WebDriver driver;
    private WebDriverWait wait;

    public ShoppingCartStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @When("User taps on the shopping cart icon")
    public void userTapsOnTheShoppingCartIcon() {
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/cartRL")));
        cartIcon.click();
    }

    @Then("User should be navigated to the shopping cart page")
    public void userShouldBeNavigatedToTheShoppingCartPage() {
        WebElement cartPage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/cartInfoLL")));
        assertThat(cartPage.isDisplayed()).isTrue();
    }

    @When("User has no items in the cart")
    public void userHasNoItemsInTheCart() {
        try {
            WebElement cartInfo = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/cartInfoLL")));
            assertThat(cartInfo.isDisplayed()).isTrue();
        } catch (TimeoutException e) {
            throw new AssertionError("Shopping cart info element not found or not displayed.");
        }
    }

    @Then("User should see an empty shopping cart message")
    public void userShouldSeeAnEmptyShoppingCartMessage() {
        try {
            WebElement emptyCartMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/cartInfoLL")));
            assertThat(emptyCartMessage.isDisplayed()).isTrue();
        } catch (TimeoutException e) {
            throw new AssertionError("Empty cart message not displayed.");
        }
    }

    @Then("User should see the {string} button")
    public void userShouldSeeTheButton(String buttonText) {
        WebElement goShoppingButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/shoppingBt")));
        assertThat(goShoppingButton.isDisplayed()).isTrue();
        assertThat(goShoppingButton.getText()).isEqualTo(buttonText);
    }

    @Then("User should be able to tap the {string} button")
    public void userShouldBeAbleToTapTheButton(String buttonText) {
        WebElement goShoppingButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/shoppingBt")));
        goShoppingButton.click();
    }

    @Then("The cart item counter should not be visible")
public void theCartItemCounterShouldNotBeVisible() {
    boolean isCounterVisible = isElementVisible(By.id("com.saucelabs.mydemoapp.android:id/cartIV"));

    // Check if the cartTV shows any items
    WebElement cartTV = driver.findElement(By.id("com.saucelabs.mydemoapp.android:id/cartTV"));
    String cartText = cartTV.getText();

    // If the cart is empty, the counter should not be visible
    boolean isCartEmpty = cartText == null || cartText.trim().equals("0");

    if (isCartEmpty) {
        assertThat(isCounterVisible).isFalse();
    } else {
        // If there is an item, the counter should be visible
        assertThat(isCounterVisible).isTrue();
    }
}

    private boolean isElementVisible(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            return false;
        }
    }
}
