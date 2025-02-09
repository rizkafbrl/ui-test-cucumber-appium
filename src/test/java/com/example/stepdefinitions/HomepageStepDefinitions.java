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

import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class HomepageStepDefinitions {
    private static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";

    // Element IDs
    private static final String SORT_BUTTON_ID = BASE_PACKAGE + ":id/sortIV";
    private static final String SORT_TEXT_ID = BASE_PACKAGE + ":id/sortTV";
    private static final String NAME_ASC_ID = BASE_PACKAGE + ":id/nameAscCL";
    private static final String NAME_DESC_ID = BASE_PACKAGE + ":id/nameDesCL";
    private static final String PRICE_ASC_ID = BASE_PACKAGE + ":id/priceAscCL";
    private static final String PRICE_DESC_ID = BASE_PACKAGE + ":id/priceDesCL";
    private static final String MENU_BUTTON_ID = BASE_PACKAGE + ":id/menuIV";
    private static final String PRODUCT_ITEM_XPATH = "//android.view.ViewGroup[contains(@resource-id, 'productItem')]";
    private static final String PRODUCT_NAME_RELATIVE_XPATH = ".//android.widget.TextView";

    // Sort options
    private static final String SORT_NAME_ASC = "Name - Ascending";
    private static final String SORT_NAME_DESC = "Name - Descending";
    private static final String SORT_PRICE_ASC = "Price - Ascending";
    private static final String SORT_PRICE_DESC = "Price - Descending";
    
    private final AndroidDriver<MobileElement> driver;
    private final HelperCommons helperCommons;
    private final WebDriverWait mediumWait;
    private final WebDriverWait quickWait;

    public HomepageStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
        this.mediumWait = new WebDriverWait(driver, WaitTimes.MEDIUM_WAIT);
        this.quickWait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    @Before
    public void setUp() {
        helperCommons.setUp();
    }

    @When("User tap on sorting button")
    public void userTapOnSortingButton() {
        clickElement(By.id(SORT_BUTTON_ID));
    }

    @Then("User able to see filter widget on homepage")
    public void userAbleToSeeFilterWidgetOnHomepage() {
        WebElement sortByText = waitForVisibility(By.id(SORT_TEXT_ID));
        assertThat(sortByText.isDisplayed())
            .withFailMessage("Filter widget is not displayed on homepage")
            .isTrue();
    }

    @When("User tap on {string} option")
    public void userTapOnSortOption(String option) {
        By locator = getSortOptionLocator(option);
        if (locator != null) {
            clickElement(locator);
        } else {
            throw new IllegalArgumentException("Unknown sort option: " + option);
        }
    }

    @Then("the products should be sorted with {string}")
    public void verifyProductsSorting(String sortOrder) {
        List<String> productNames = getProductNames();
        List<String> expectedOrder = getSortedProductNames(productNames, sortOrder);
        
        assertThat(productNames)
            .withFailMessage("Products are not correctly sorted by " + sortOrder)
            .isEqualTo(expectedOrder);
    }

    @Then("the filter widget should be closed")
    public void verifyFilterWidgetClosed() {
        boolean isFilterWidgetClosed = quickWait.until(
            ExpectedConditions.invisibilityOfElementLocated(By.id(SORT_TEXT_ID))
        );
        assertThat(isFilterWidgetClosed)
            .withFailMessage("Filter widget is still visible")
            .isTrue();
    }

    @When("User tap on menu button")
    public void userTapOnMenuButton() {
        clickElement(By.id(MENU_BUTTON_ID));
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }

    // Helper methods
    private By getSortOptionLocator(String option) {
        switch(option) {
            case SORT_NAME_ASC:
                return By.id(NAME_ASC_ID);
            case SORT_NAME_DESC:
                return By.id(NAME_DESC_ID);
            case SORT_PRICE_ASC:
                return By.id(PRICE_ASC_ID);
            case SORT_PRICE_DESC:
                return By.id(PRICE_DESC_ID);
            default:
                return null;
        }
    }

    private List<String> getProductNames() {
        List<MobileElement> products = driver.findElements(By.xpath(PRODUCT_ITEM_XPATH));
        return products.stream()
            .map(product -> product.findElement(By.xpath(PRODUCT_NAME_RELATIVE_XPATH)).getText())
            .collect(Collectors.toList());
    }

    private List<String> getSortedProductNames(List<String> originalNames, String sortOrder) {
        List<String> sortedNames = new ArrayList<>(originalNames);
        Collections.sort(sortedNames);
        if (sortOrder.contains("Descending")) {
            Collections.reverse(sortedNames);
        }
        return sortedNames;
    }

    private WebElement waitForVisibility(By locator) {
        return mediumWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickElement(By locator) {
        WebElement element = quickWait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}