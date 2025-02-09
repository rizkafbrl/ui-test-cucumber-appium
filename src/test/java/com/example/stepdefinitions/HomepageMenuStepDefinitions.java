package com.example.stepdefinitions;

import com.example.support.DriverManager;
import com.example.support.HelperCommons;
import com.example.support.PermissionsHelper;
import com.example.support.WaitTimes;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.MobileBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.*;

public class HomepageMenuStepDefinitions {
    // Base package ID
    private static final String BASE_PACKAGE = "com.saucelabs.mydemoapp.android";
    
    // Element IDs
    private static final String MENU_RECYCLER_VIEW_ID = BASE_PACKAGE + ":id/menuRV";
    private static final String MENU_ITEM_TEXT_ID = BASE_PACKAGE + ":id/itemTV";
    private static final String PRODUCT_TITLE_ID = BASE_PACKAGE + ":id/productTV";
    private static final String WEB_VIEW_TITLE_ID = BASE_PACKAGE + ":id/webViewTV";
    
    // XPath Templates
    private static final String MENU_ITEM_XPATH_TEMPLATE = 
        "//android.widget.TextView[@resource-id='" + MENU_ITEM_TEXT_ID + "' and @text='%s']";
    private static final String PAGE_TITLE_XPATH_TEMPLATE = 
        "//android.widget.TextView[contains(@text, '%s')]";
    
    // Page Titles requiring special permissions
    private static final String GEO_LOCATION_PAGE = "Geo Location Page";
    private static final String FINGERPRINT_PAGE = "Fingerprint Page";
    
    private final AndroidDriver<MobileElement> driver;
    private final HelperCommons helperCommons;
    private final PermissionsHelper permissionsHelper;
    private final WebDriverWait wait;
    private final Map<String, Runnable> permissionActions;

    public HomepageMenuStepDefinitions() {
        this.driver = DriverManager.getDriver();
        this.helperCommons = new HelperCommons(driver);
        this.permissionsHelper = new PermissionsHelper(driver);
        this.wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        
        // Initialize permission actions map
        this.permissionActions = Map.of(
            GEO_LOCATION_PAGE, permissionsHelper::grantLocationPermission,
            FINGERPRINT_PAGE, permissionsHelper::bypassBiometricAuthentication
        );
    }

    @Before
    public void setUp() {
        helperCommons.setUp();
    }

    @Then("User able to see menu items")
    public void userAbleToSeeMenuItems() {
        WebElement menuItems = waitForVisibility(By.id(MENU_RECYCLER_VIEW_ID));
        assertThat(menuItems.isDisplayed())
            .withFailMessage("Menu items are not displayed")
            .isTrue();
    }

    @When("User tap on {string} menu item")
    public void userTapOnMenuItem(String menuItem) {
        String xpath = String.format(MENU_ITEM_XPATH_TEMPLATE, menuItem);
        clickElement(By.xpath(xpath));
    }

    @Then("User should be redirected to {string} page")
    public void userShouldBeRedirectedToPage(String pageTitle) {
        // Grant permissions if needed
        grantNecessaryPermissions(pageTitle);
        
        // Find page title using multiple locator strategies
        MobileElement pageTitleElement = findPageTitleElement(pageTitle);
        
        assertThat(pageTitleElement.isDisplayed())
            .withFailMessage("Page title '%s' is not displayed", pageTitle)
            .isTrue();

        verifyMenuDrawerIsClosed();
    }

    @After
    public void tearDown() {
        helperCommons.tearDown();
    }

    private MobileElement findPageTitleElement(String pageTitle) {
        List<By> locators = getPageTitleLocators(pageTitle);
        
        return locators.stream()
            .map(locator -> {
                try {
                    return (MobileElement) waitForVisibility(locator);
                } catch (Exception ignored) {
                    return null;
                }
            })
            .filter(Objects::nonNull)
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException(
                String.format("Page title element not found for: %s", pageTitle)));
    }

    private List<By> getPageTitleLocators(String pageTitle) {
        return Arrays.asList(
            By.xpath(String.format(PAGE_TITLE_XPATH_TEMPLATE, pageTitle)),
            MobileBy.AndroidUIAutomator(
                String.format("new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                    PRODUCT_TITLE_ID, pageTitle)),
            MobileBy.AndroidUIAutomator(
                String.format("new UiSelector().resourceId(\"%s\").descriptionContains(\"%s\")",
                    PRODUCT_TITLE_ID, pageTitle)),
            MobileBy.AndroidUIAutomator(
                String.format("new UiSelector().resourceId(\"%s\").textContains(\"%s\")",
                    WEB_VIEW_TITLE_ID, pageTitle))
        );
    }

    private void grantNecessaryPermissions(String pageTitle) {
        permissionActions.getOrDefault(pageTitle, () -> {}).run();
    }

    private void verifyMenuDrawerIsClosed() {
        try {
            boolean isMenuInvisible = wait.until(
                ExpectedConditions.invisibilityOfElementLocated(By.id(MENU_RECYCLER_VIEW_ID)));
            assertThat(isMenuInvisible)
                .withFailMessage("Menu drawer is still visible")
                .isTrue();
        } catch (Exception e) {
            throw new AssertionError("Menu drawer did not close properly", e);
        }
    }

    private WebElement waitForVisibility(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void clickElement(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
    }
}