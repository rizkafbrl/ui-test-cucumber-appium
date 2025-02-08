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
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            MobileElement allowButton = (MobileElement) wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//android.widget.Button[@text='ALLOW' or @text='Allow']")));
            allowButton.click();
        } catch (Exception e) {
            System.out.println("Permission " + permission + " not granted or requested: " + e.getMessage());
        }
    }

    public void grantAllPermissions() {
        grantPermission("camera");
        grantPermission("location");
        grantPermission("storage");
    }
}