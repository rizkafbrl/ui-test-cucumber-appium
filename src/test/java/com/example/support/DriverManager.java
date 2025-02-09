package com.example.support;

import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URI;
import java.net.URL;

public class DriverManager {
    private static final String APP_PATH = System.getProperty("user.dir") + "/apps/mda-2.2.0-25.apk";
    private static final String APP_PACKAGE = "com.saucelabs.mydemoapp.android";
    private static final String APP_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.SplashActivity";
    private static final String APP_WAIT_ACTIVITY = "com.saucelabs.mydemoapp.android.view.activities.MainActivity";
    private static final String APPIUM_URL = "http://127.0.0.1:4723";

    private static AndroidDriver<MobileElement> driver;

    private DriverManager() {
        // Prevent instantiation
    }

    public static void initializeDriver() {
        if (driver == null) {
            DesiredCapabilities capabilities = createCapabilities();
            try {
                driver = new AndroidDriver<>(new URI(APPIUM_URL).toURL(), capabilities);
            } catch (Exception e) {
                throw new RuntimeException("Failed to initialize Appium driver", e);
            }
        }
    }

    public static AndroidDriver<MobileElement> getDriver() {
        if (driver == null) {
            initializeDriver();
        }
        return driver;
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static DesiredCapabilities createCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "emulator-5554");
        capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        capabilities.setCapability("appium:appPackage", APP_PACKAGE);
        capabilities.setCapability("appium:appActivity", APP_ACTIVITY);
        capabilities.setCapability("appium:appWaitActivity", APP_WAIT_ACTIVITY);
        capabilities.setCapability("appium:noReset", true);
        capabilities.setCapability("appium:fullReset", false);
        capabilities.setCapability("appium:newCommandTimeout", 120);
        capabilities.setCapability("appium:adbExecTimeout", 20000);
        capabilities.setCapability("appium:uiautomator2ServerInstallTimeout", 60000);
        capabilities.setCapability("appium:autoGrantPermissions", true);
        capabilities.setCapability(MobileCapabilityType.APP, APP_PATH);
        return capabilities;
    }
}
