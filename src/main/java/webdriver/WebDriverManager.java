/*
 * Web-driver manager class
 * Parfimovich A.V.
 */

package webdriver;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.UnreachableBrowserException;

import java.util.concurrent.TimeUnit;

public class WebDriverManager {
    private static final String BROWSER = java.lang.System.getProperties().getProperty("webbrowser");
    private static final Logger log = LogManager.getLogger(WebDriverManager.class);
    public static WebDriver driver;

    private WebDriverManager() {
        //browser = java.lang.System.getProperties().getProperty("webbrowser");
        //log.info("первоначальная инициализация класса WebDriverManager");
        //log.debug("запуск тестов для браузере {}",BROWSER);
    }

    public static WebDriver getDriver() {
        if (driver == null) {

            log.info("первоначальная инициализация класса WebDriverManager");
            log.debug("запуск тестов для браузере {}",BROWSER);

            System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver.exe");
            try {
                ChromeOptions option = new ChromeOptions();
                option.addArguments("--window-size=1360,768");
                driver = new ChromeDriver(option);
            } catch(UnreachableBrowserException e) {
               log.error("Невозможно инциализировать драйвер!", e);
            }
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        }
        return driver;
    }

    public static void quit() {
        try {
            driver.quit();
            driver = null;
        } catch (UnreachableBrowserException e) {
            log.error("Невозможно закрыть браузер!");
        }
    }
}