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
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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

    private static final String FIREFOX_browser = "firefox";
    private static final String CHROME_browser = "chrome";
    private static final String IE11_browser = "ie";
    private static final String browser;
    static {
        browser = System.getProperties().getProperty("webbrowser",CHROME_browser);
    }

    /**
     *
     */
    private static void initChromeDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.chrome.driver", System.getProperty("chrome.driver.path"));
        //ChromeOptions option = new ChromeOptions();
        //option.addArguments("--window-size=1360,768");
        driver = new ChromeDriver();
    }

    /**
     *
     */
    private static void initGeckoDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.gecko.driver", System.getProperty("gecko.driver.path"));
        driver = new FirefoxDriver();
    }

    /**
     *
     * @throws UnreachableBrowserException
     */
    private static void initIEDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.ie.driver", System.getProperty("ie.driver.path"));
        driver = new InternetExplorerDriver();
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
                    case IE11_browser: initIEDriver(); break;
                    case CHROME_browser:
                    default: initChromeDriver();
                }
                log.debug("Иницализирован драйвер браузера {}",browser);
            } catch (UnreachableBrowserException e) {
                log.error("Ошибка инциализизации драйвера: {}", e.getMessage());
            }
            driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit.wait")), TimeUnit.SECONDS);
            if (Boolean.parseBoolean(System.getProperty("start-maximized"))) {
                driver.manage().window().maximize();
            }
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