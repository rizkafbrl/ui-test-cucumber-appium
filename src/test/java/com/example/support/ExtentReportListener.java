package com.example.support;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import java.util.logging.Logger;

public class ExtentReportListener {
    private static final Logger LOGGER = Logger.getLogger(ExtentReportListener.class.getName());
    private static ExtentReports extent;
    private static ExtentTest test;

    @Before
    public void setUp(Scenario scenario) {
        LOGGER.info("Setting up Extent Report listener");
        if (extent == null) {
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter("target/extent-reports/extent-report.html");
            sparkReporter.config().setTheme(Theme.STANDARD);
            sparkReporter.config().setDocumentTitle("Automation Report");
            sparkReporter.config().setReportName("Test Report");

            extent = new ExtentReports();
            extent.attachReporter(sparkReporter);
        }

        test = extent.createTest(scenario.getName());
        LOGGER.info("Created test: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        LOGGER.info("Tearing down Extent Report listener");
        if (scenario.isFailed()) {
            test.fail("Scenario failed: " + scenario.getName());
        } else {
            test.pass("Scenario passed: " + scenario.getName());
        }

        extent.flush();
        LOGGER.info("Flushed Extent Report");
    }
}