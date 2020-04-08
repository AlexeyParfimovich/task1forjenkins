package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import webdriver.WebDriverManager;

public class Hooks {
    public final Logger log = LogManager.getLogger(getClass());

    @Before
    public void beforeScenario(Scenario scenario) {
        log.info("Запуск сценария " + scenario.getName());
    }

    @After
    public void afterScenario(Scenario scenario) {
        log.info("Завершено выполнение сценария " + scenario.getName());
        WebDriverManager.quit();
    }
}
