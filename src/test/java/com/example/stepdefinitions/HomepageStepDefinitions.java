package com.example.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.example.support.DriverManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HomepageStepDefinitions {

    private AndroidDriver<MobileElement> driver;

    public HomepageStepDefinitions() {
        this.driver = DriverManager.getDriver();
    }

    @Given("I am on the products page")
    public void iAmOnProductsPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement productsPageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/productsPage")));
        assertThat(productsPageElement.isDisplayed()).isTrue();
    }

    @When("I tap on {string} option")
    public void iTapSortOption(String option) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        switch(option) {
            case "Name - Ascending":
                WebElement nameAscElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/nameAscCL")));
                nameAscElement.click();
                break;
            case "Name - Descending":
                WebElement nameDescElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/nameDesCL")));
                nameDescElement.click();
                break;
            case "Price - Ascending":
                WebElement priceAscElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/priceAscCL")));
                priceAscElement.click();
                break;
            case "Price - Descending":
                WebElement priceDescElement = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/priceDesCL")));
                priceDescElement.click();
                break;
            default:
                System.out.println("Unknown option: " + option);
                break;
        }
    }

    @Then("the products should be sorted {string}")
    public void verifyProductsSorting(String sortOrder) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<MobileElement> products = driver.findElements(By.id("com.saucelabs.mydemoapp.android:id/productItem"));
        List<String> productNames = products.stream()
                                            .map(MobileElement::getText)
                                            .collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames);

        if (sortOrder.equals("Descending")) {
            Collections.reverse(sortedNames);
        }

        assertThat(productNames).isEqualTo(sortedNames);
    }

    @Then("the {string} sort option should show a tick mark")
    public void verifySortOptionTick(String option) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        switch(option) {
            case "Name - Ascending":
                WebElement tickNameAscElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/tickNameAscIV")));
                assertThat(tickNameAscElement.isDisplayed()).isTrue();
                break;
            case "Name - Descending":
                WebElement tickNameDescElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/tickNameDesIV")));
                assertThat(tickNameDescElement.isDisplayed()).isTrue();
                break;
            case "Price - Ascending":
                WebElement tickPriceAscElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/tickPriceAscIV")));
                assertThat(tickPriceAscElement.isDisplayed()).isTrue();
                break;
            case "Price - Descending":
                WebElement tickPriceDescElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/tickPriceDesIV")));
                assertThat(tickPriceDescElement.isDisplayed()).isTrue();
                break;
            default:
                System.out.println("Unknown option: " + option);
                break;
        }
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}