/*
 * Test running class
 * Parfimovich A.V.
 */

import entities.TestProperties;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import webdriver.WebDriverManager;

import java.io.IOException;

@CucumberOptions(
        strict = true,
        features = {"src/test/resources/features"},
        tags = "@authTest1",
        glue = {"steps"}
)

public class TestRunner extends AbstractTestNGCucumberTests {

    private static Logger log;
    static {
        TestProperties.setProperties();
        log = LogManager.getLogger(TestRunner.class);
        log.debug("Настройки приложения установлены");
    }

    @BeforeClass
    public void beforeClass() {
        log.info("Запуск тестов");
    }

    @AfterClass
    public void afterClass() {
        log.info("Тесты завершены");
        Allure.addAttachment("Лог-файл прогона тестов:","Тесты завершены");
    }
}
