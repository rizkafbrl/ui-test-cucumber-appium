package com.example.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.*;
import com.example.support.DriverManager;
import com.example.support.WaitTimes;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartStepDefinitions {
    private static final String CART_LAYOUT_ID = "com.saucelabs.mydemoapp.android:id/cartRL";
    private static final String CART_INFO_ID = "com.saucelabs.mydemoapp.android:id/cartInfoLL";
    private static final String CART_COUNTER_ID = "com.saucelabs.mydemoapp.android:id/cartTV";
    private static final String SHOPPING_BUTTON_ID = "com.saucelabs.mydemoapp.android:id/shoppingBt";
    
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ShoppingCartStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @When("User taps on the shopping cart icon")
    public void userTapsOnTheShoppingCartIcon() {
        WebElement cartIcon = wait.until(ExpectedConditions.elementToBeClickable(By.id(CART_LAYOUT_ID)));
        cartIcon.click();
    }

    @Then("User should be navigated to the shopping cart page")
    public void userShouldBeNavigatedToTheShoppingCartPage() {
        WebElement cartPage = waitForElement(By.id(CART_INFO_ID));
        assertThat(cartPage.isDisplayed())
            .withFailMessage("Shopping cart page is not displayed")
            .isTrue();
    }

    @When("User has no items in the cart")
    public void userHasNoItemsInTheCart() {
        // Only verify that the cart counter shows no items
        assertThat(getCartItemCount())
            .withFailMessage("Cart should be empty but shows items")
            .isZero();
    }

    @Then("User should see an empty shopping cart message")
    public void userShouldSeeAnEmptyShoppingCartMessage() {
        WebElement emptyCartMessage = waitForElement(By.id(CART_INFO_ID));
        assertThat(emptyCartMessage.isDisplayed())
            .withFailMessage("Empty cart message is not displayed")
            .isTrue();
    }

    @Then("User should see the {string} button")
    public void userShouldSeeTheButton(String buttonText) {
        WebElement goShoppingButton = waitForElement(By.id(SHOPPING_BUTTON_ID));
        assertThat(goShoppingButton.isDisplayed())
            .withFailMessage("Shopping button is not displayed")
            .isTrue();
        assertThat(goShoppingButton.getText())
            .withFailMessage("Shopping button text doesn't match expected: " + buttonText)
            .isEqualTo(buttonText);
    }

    @Then("User should be able to tap the {string} button")
    public void userShouldBeAbleToTapTheButton(String buttonText) {
        WebElement goShoppingButton = wait.until(ExpectedConditions.elementToBeClickable(By.id(SHOPPING_BUTTON_ID)));
        goShoppingButton.click();
    }

    @Then("The cart item counter should not be visible")
    public void theCartItemCounterShouldNotBeVisible() {
        assertThat(hasCartCounterValue())
            .withFailMessage("Cart counter should not show any value when cart is empty")
            .isFalse();
    }

    private WebElement waitForElement(By locator) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new AssertionError("Element not found or not visible: " + locator, e);
        }
    }

    private boolean hasCartCounterValue() {
        try {
            WebElement cartCounter = driver.findElement(By.id(CART_COUNTER_ID));
            String countText = cartCounter.getText();
            return countText != null && !countText.trim().isEmpty();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private int getCartItemCount() {
        try {
            WebElement cartCounter = driver.findElement(By.id(CART_COUNTER_ID));
            String countText = cartCounter.getText().trim();
            return countText.isEmpty() ? 0 : Integer.parseInt(countText);
        } catch (NoSuchElementException | NumberFormatException e) {
            return 0;
        }
    }
}