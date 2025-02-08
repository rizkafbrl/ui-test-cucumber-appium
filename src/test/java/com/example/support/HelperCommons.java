package com.example.support;

import com.example.stepdefinitions.commons.HomepageCommon;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;

public class HelperCommons {
    private AndroidDriver<MobileElement> driver;
    private HomepageCommon homepageCommon;

    public HelperCommons(AndroidDriver<MobileElement> driver) {
        this.driver = driver;
        this.homepageCommon = new HomepageCommon();
    }

    public void setUp() {
        // Terminate and activate the app to navigate to the homepage
        driver.terminateApp("com.saucelabs.mydemoapp.android");
        driver.activateApp("com.saucelabs.mydemoapp.android");
        homepageCommon.userOnHomepage();
    }

    public void tearDown() {
        DriverManager.quitDriver();
    }
}