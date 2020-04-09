/*
 * Test running class
 * Parfimovich A.V.
 */

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        tags = "@authTest1",
        glue = {"steps"}
)

public class TestRunner extends AbstractTestNGCucumberTests { }
