package com.launcher;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/features/PlacerAITestScenario.feature", plugin = {"pretty",
        "html:target/cucumber/PlacerAITestScenario.html",
        "rerun:target/cucumber/PlacerAITestScenario.txt",
        "json:target/cucumber/PlacerAITestScenario.json",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
        glue = {"com.definitions"})
public class PlacerAITestScenarioLauncher extends AbstractTestNGCucumberTests {
}
