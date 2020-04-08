package entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import webdriver.WebDriverManager;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public abstract class PageObject {
    public final Logger log = LogManager.getLogger(getClass());
    protected WebDriver driver;

    public PageObject() {
        driver = WebDriverManager.getDriver();
        PageFactory.initElements(driver, this);
    }

    public File getScreenshot() {
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
    }

    public void saveScreenshot(String fileName) {
        try {
            File srcFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(srcFile, new File(fileName));
        } catch (IOException e) {
            log.error("Ошибка записи скриншота: ", e.getMessage());
        }
    }

    public void openPage(String url) {
        driver.get(url);
        log.trace("Выполнен вход на страницу: " + url);
    }

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
