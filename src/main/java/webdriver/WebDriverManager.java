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
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 *
 */
public class WebDriverManager {

    private static final Logger log = LogManager.getLogger(WebDriverManager.class);
    public static WebDriverWait waiter;
    public static WebDriver driver;

    private static final String WINDOWS_platform = "windows";
    private static final String LINUX_platform = "linux";

    private static final String FIREFOX_browser = "firefox";
    private static final String CHROME_browser = "chrome";
    private static final String OPERA_browser = "opera";
    private static final String browser;
    private static final String platform;
    static {
        browser = System.getProperties().getProperty("webbrowser",CHROME_browser);
        platform = System.getProperties().getProperty("testplatform",WINDOWS_platform);
    }

    /**
     *
     */
    private static void initChromeDriver() throws UnreachableBrowserException {
        ChromeOptions option = new ChromeOptions();

        if (platform.equals(LINUX_platform)) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.linux.path"));
            option.addArguments("--headless");
            option.addArguments("--no-sandbox");
            option.addArguments("--disable-gpu");
            option.addArguments("--disable-extensions");
//            option.addArguments("--disable-dev-shm-usage");
//            option.addArguments("--remote-debugging-port=9222");
            log.debug("Выбран драйвер браузера {} для {}",browser,platform);
        } else {
            System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.windows.path"));
            log.debug("Выбран драйвер браузера {} для {}",browser,platform);
        }
        driver = new ChromeDriver(option);
        log.trace("Драйвер создан");
    }

    /**
     *
     */
    private static void initOperaDriver() throws UnreachableBrowserException {
        OperaOptions option = new OperaOptions();

        if (platform.equals(LINUX_platform)) {
            System.setProperty("webdriver.opera.driver", System.getProperty("opera.linux.path"));
            option.addArguments("--headless");
            option.addArguments("--no-sandbox");
            option.addArguments("--disable-gpu");
            option.addArguments("--disable-extensions");
//            option.addArguments("--disable-dev-shm-usage");
//            option.addArguments("--remote-debugging-port=9222");
            log.debug("Выбран драйвер браузера {} для {}",browser,platform);
        } else {
            System.setProperty("webdriver.opera.driver", System.getProperty("opera.windows.path"));
            log.debug("Выбран драйвер браузера {} для {}",browser,platform);
        }
        driver = new OperaDriver(option);
        log.trace("Драйвер создан");
    }

    /**
     *
     */
    private static void initGeckoDriver() throws UnreachableBrowserException {
        FirefoxOptions option = new FirefoxOptions();

        if (platform.equals(LINUX_platform)) {
            System.setProperty("webdriver.gecko.driver", System.getProperty("gecko.linux.path"));
            option.addArguments("--headless");
            option.addArguments("--no-sandbox");
            option.addArguments("--disable-gpu");
            option.addArguments("--disable-extensions");
            log.debug("Выбран драйвер браузера {} для {}", browser, platform);
        } else {
            System.setProperty("webdriver.gecko.driver", System.getProperty("gecko.windows.path"));
            log.debug("Выбран драйвер браузера {} для {}",browser,platform);
        }
        driver = new FirefoxDriver(option);
        log.trace("Драйвер создан");
    }


    /**
     *
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            try {
                switch (browser) {
                    case FIREFOX_browser: initGeckoDriver(); break;
                    case OPERA_browser: initOperaDriver(); break;
                    case CHROME_browser:
                    default: initChromeDriver();
                }
                log.debug("Иницализирован драйвер браузера {}",browser);
            } catch (Exception e) {
                log.error("Ошибка инциализизации драйвера: {}", e.getMessage());
            }
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit.wait")), TimeUnit.SECONDS);
        }
        return driver;
    }

    /**
     *
     * @return WebDriverWait
     */
    public static WebDriverWait getWaiter() {
        if (waiter == null) {
            waiter = new WebDriverWait(WebDriverManager.getDriver(), Integer.parseInt(System.getProperty("explicit.wait")));
        }
        return waiter;
    }

    /**
     *
     */
    public static void quit() {
        if (driver == null) {
            log.debug("Ошибка закрытия драйвера: драйвер не был инициализирован!");
        } else try {
            driver.quit();
            driver = null;
            waiter = null;
        } catch (UnreachableBrowserException e) {
            log.error("Ошибка закрытия драйвера: {}", e.getMessage());
        }
    }
}