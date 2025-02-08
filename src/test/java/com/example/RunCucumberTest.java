package com.example;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features/",
        glue = {"com.example.stepdefinitions", "com.example.support"},
        plugin = {"pretty", "json:target/cucumber-reports/cucumber.json"}
)
public class RunCucumberTest {
    @BeforeClass
    public static void setup() {
        String tags = System.getProperty("cucumber.tags");
        if (tags != null) {
            System.setProperty("cucumber.filter.tags", tags);
        }
    }
}