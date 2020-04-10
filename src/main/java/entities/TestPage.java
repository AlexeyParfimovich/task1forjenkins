package entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import webdriver.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.openqa.selenium.support.PageFactory;

public abstract class TestPage {
    private final Logger log = LogManager.getLogger(getClass());
    private WebDriver driver;

    public TestPage() {
        driver = WebDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    /**
     *
     * @param url String
     */
    public void openPage(String url) {
        driver.get(url);
        log.trace("Выполнен вход на страницу: " + url);
    }

//    public void saveScreenshot(String fileName) {
//        try {
//            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(srcFile, new File(fileName));
//        } catch (IOException e) {
//            log.error("Ошибка записи скриншота: ", e.getMessage());
//        }
//    }

    /**
     *
     * @return File
     */
    public File getScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    }

    /**
     *
     * @return String alert_text
     */
    public String browserAlertAccept() {
        String alertText;
        try {
            alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            log.error("No expected Alert present: {}", e.getMessage());
            alertText = null;
        }
        return alertText;
    }

    /**
     *
     * @return String alert_text
     */
    public String browserAlertDismiss() {
        String alertText;
        try {
            alertText = driver.switchTo().alert().getText();
            driver.switchTo().alert().dismiss();
        } catch (NoAlertPresentException e) {
            log.error("No expected Alert present: {}", e.getMessage());
            alertText = null;
        }
        return alertText;
    }

    /**
     *
     * @param  cucumberElementName String
     * @return WebElement
     */
    public WebElement get(String cucumberElementName) {
        Class<?> clazz = this.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(NameOfElement.class)) {
                NameOfElement nameOfElementAnnotation = field.getAnnotation(NameOfElement.class);
                if (nameOfElementAnnotation.value().equals(cucumberElementName)) {
                    try {
                        return (WebElement) field.get(this);
                    } catch (IllegalAccessException e) {
                        log.error("ERROR: element with name " + cucumberElementName + " at page " + this.getClass().getName() + " is not public");
                    }
                }
            }
        }
        throw new IllegalArgumentException("ERROR: there is no such element with name " + cucumberElementName + " at page " + this.getClass().getName());
    }

//
//    public List<WebElement> getCollection(String cucumberElementName)
//    {
//        Class<?> clazz = this.getClass();
//        for (Field field : clazz.getDeclaredFields())
//        {
//            if (field.isAnnotationPresent(NameOfElement.class))
//            {
//                NameOfElement nameOfElementAnnotation = field.getAnnotation(NameOfElement.class);
//                if (nameOfElementAnnotation.value().equals(cucumberElementName))
//                {
//                    try
//                    {
//                        return (List<WebElement>) field.get(this);
//
//                    } catch (IllegalAccessException e)
//                    {
//                        logger.error("ERROR: element with name " + cucumberElementName + " at page " + this.getClass().getName() + " is not public");
//                    }
//                }
//            }
//        }
//        throw new IllegalArgumentException("ERROR: there is no such element with name " + cucumberElementName + " at page " + this.getClass().getName());
//    }
}
