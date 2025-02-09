package com.example.support;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermissionsHelper {
    private static final String ALLOW_BUTTON_XPATH = "//android.widget.Button[@text='ALLOW' or @text='Allow']";
    private static final By BIOMETRIC_TITLE_ID = By.id("com.saucelabs.mydemoapp.android:id/alertTitle");
    private static final By BIOMETRIC_OK_BUTTON_ID = By.id("android:id/button1");

    private final AndroidDriver<MobileElement> driver;
    private final WebDriverWait quickWait;

    public PermissionsHelper(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.quickWait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
    }

    public void grantPermission(String permission) {
        try {
            MobileElement allowButton = waitForElementToBeClickable(By.xpath(ALLOW_BUTTON_XPATH), quickWait);
            if (allowButton != null) {
                allowButton.click();
                System.out.println("Granted permission: " + permission);
            }
        } catch (Exception e) {
            System.out.println("Permission not granted or not requested: " + permission + " - " + e.getMessage());
        }
    }

    public void grantLocationPermission() {
        grantPermission("location");
    }

    public void grantAllPermissionsOnAndroid() {
        grantPermission("camera");
        grantPermission("location");
        grantPermission("storage");
    }

    public void bypassBiometricAuthentication() {
        try {
            boolean isBiometricTitleVisible = isElementPresent(BIOMETRIC_TITLE_ID, quickWait);
            if (isBiometricTitleVisible) {
                MobileElement okButton = waitForElementToBeClickable(BIOMETRIC_OK_BUTTON_ID, quickWait);
                if (okButton != null) {
                    okButton.click();
                    System.out.println("Biometric popup dismissed.");
                }
            }
        } catch (Exception e) {
            System.out.println("No biometric popup detected: " + e.getMessage());
        }
    }

    private MobileElement waitForElementToBeClickable(By locator, WebDriverWait wait) {
        try {
            return (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isElementPresent(By locator, WebDriverWait wait) {
        try {
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
