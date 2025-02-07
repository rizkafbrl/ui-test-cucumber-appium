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

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class HomepageStepDefinitions {
    private AndroidDriver<MobileElement> driver;

    public HomepageStepDefinitions() {
        this.driver = DriverManager.getDriver();
    }

    @Given("User on homepage")
    public void userOnHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/productTV")));
        String actualTitle = element.getText();
        assertEquals("Products", actualTitle);
    }

    @When("User tap on sorting button")
    public void userTapOnSortingButton() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement sortButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("com.saucelabs.mydemoapp.android:id/sortIV")));
        sortButton.click();
    }

    @Then("User able to see filter widget on homepage")
    public void userAbleToSeeFilterWidgetOnHomepage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement sortByText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/sortTV")));
        assertThat(sortByText.isDisplayed()).isTrue();
    }

    @When("User tap on {string} option")
    public void userTapOnSortOption(String option) {
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

    @Then("the products should be sorted with {string}")
    public void verifyProductsSorting(String sortOrder) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        List<MobileElement> products = driver.findElements(By.xpath("//android.view.ViewGroup[contains(@resource-id, 'productItem')]"));
        List<String> productNames = products.stream()
                .map(product -> product.findElement(By.xpath(".//android.widget.TextView")).getText())
                .collect(Collectors.toList());

        List<String> sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames);
        if (sortOrder.contains("Descending")) {
            Collections.reverse(sortedNames);
        }

        assertThat(productNames).isEqualTo(sortedNames);
    }

    @Then("the filter widget should be closed")
    public void verifyFilterWidgetClosed() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        boolean isFilterWidgetClosed = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("com.saucelabs.mydemoapp.android:id/sortTV")));
        assertThat(isFilterWidgetClosed).isTrue();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}