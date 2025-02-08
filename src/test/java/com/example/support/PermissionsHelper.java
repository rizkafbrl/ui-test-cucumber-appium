package com.example.support;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PermissionsHelper {
    private AndroidDriver<MobileElement> driver;

    public PermissionsHelper(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
    }

    public void grantPermission(String permission) {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.QUICK_WAIT);
        try {
            MobileElement allowButton = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.Button[@text='ALLOW' or @text='Allow']")
                    )
                );
            allowButton.click();
        } catch (Exception e) {
            System.out.println("Permission " + permission + " not granted or requested: " + e.getMessage());
        }
    }

    public void grantAllPermissionsOnAndroid() {
        grantPermission("camera");
        grantPermission("location");
        grantPermission("storage");
    }

    public void bypassBiometricPopup() {
        WebDriverWait wait = new WebDriverWait(driver, WaitTimes.LOW_WAIT);
        try {
            MobileElement okButton = (MobileElement) wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.Button[@text='OK']")
                )
            );
            okButton.click();
            System.out.println("Biometric popup dismissed.");
        } catch (Exception e) {
            System.out.println("No biometric popup detected: " + e.getMessage());
        }
    }
}