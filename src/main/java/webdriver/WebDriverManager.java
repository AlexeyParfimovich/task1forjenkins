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
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
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
    private static final String OPERA_browser = "opera";
    private static final String IE11_browser = "ie";
    private static final String browser;
    static {
        browser = System.getProperties().getProperty("webbrowser") == null ? CHROME_browser
                : System.getProperties().getProperty("webbrowser");
    }

    /**
     *
     */
    private static void initChromeDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.chrome.driver",
                System.getProperty("chrome.driver.path","chromedriver.exe"));
        ChromeOptions option = new ChromeOptions();
        option.addArguments("--window-size=1360,768");
        //option.addArguments("-start-maximized");//+ System.getProperty("window-size","1920,1080"));
        driver = new ChromeDriver();
    }

    /**
     *
     */
    private static void initGeckoDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.gecko.driver",
                System.getProperty("gecko.driver.path","geckodriver.exe"));
        FirefoxOptions option = new FirefoxOptions();
        option.addArguments("--window-size=1360,768");
        driver = new FirefoxDriver(option);
    }

    /**
     *
     * @throws UnreachableBrowserException
     */
    private static void initOperaDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.opera.driver",
                    System.getProperty("opera.driver.path","operadriver.exe"));
        OperaOptions option = new OperaOptions();
        option.addArguments("--window-size=1360,768");
        driver = new OperaDriver(option);
    }

    /**
     *
     * @throws UnreachableBrowserException
     */
    private static void initIEDriver() throws UnreachableBrowserException {
        System.setProperty("webdriver.ie.driver",
                System.getProperty("ie.driver.path","IEDriverServer.exe"));
        InternetExplorerOptions option = new InternetExplorerOptions();
        option.addCommandSwitches("--window-size=1360,768");
        driver = new InternetExplorerDriver(option);
    }

    /**
     *
     * @return WebDriver
     */
    public static WebDriver getDriver() {
        if (driver == null) {

            try {
                System.getProperties().load(ClassLoader.getSystemResourceAsStream("driver.properties"));
                log.debug("Чтение настроек двайвера из файла .properties");
            } catch (IOException e) {
                log.error("Ошибка чтения файла настроек двайвера: {}", e.getMessage());
            }

            try {
                switch (browser) {
                    case FIREFOX_browser: initGeckoDriver(); break;
                    case OPERA_browser: initOperaDriver(); break;
                    case IE11_browser: initIEDriver(); break;
                    case CHROME_browser:
                    default: initChromeDriver();
                }
                log.debug("Иницализирован драйвер браузера {}",browser);
            } catch (UnreachableBrowserException e) {
                log.error("Ошибка инциализизации драйвера: {}", e.getMessage());
            }

            driver.manage().timeouts().implicitlyWait(Integer.parseInt(System.getProperty("implicit.wait","10")), TimeUnit.SECONDS);
//            if (Boolean.parseBoolean(System.getProperty("start-maximized","false"))) {
//                driver.manage().window().maximize();
//            }

        }
        return driver;
    }

    /**
     *
     * @return WebDriverWait
     */
    public static WebDriverWait getWaiter() {
        if (waiter == null) {
            waiter = new WebDriverWait(WebDriverManager.getDriver(), Integer.parseInt(System.getProperty("explicit.wait","10")));
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
