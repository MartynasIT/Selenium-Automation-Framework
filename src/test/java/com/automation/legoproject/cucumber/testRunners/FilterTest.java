package com.automation.legoproject.cucumber.testRunners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/java/com/automation/legoProject/cucumber/features/Filter.feature",
        glue = "",
        plugin = {"pretty"}
)
public class FilterTest extends AbstractTestNGCucumberTests {

}
