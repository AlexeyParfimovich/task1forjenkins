/**
 * Класс для запуска тестов
 * Автор Васильев И.Н. atcc@mail.ru
 * 02.12.2018
 */

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        tags = "@allTests",
        glue = {"steps"}
)

public class TestRunner extends AbstractTestNGCucumberTests { }
