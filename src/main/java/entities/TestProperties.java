package entities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

public class TestProperties {

    private static Logger log;

    public static void setProperties() {
        try{
            System.getProperties().load(ClassLoader.getSystemResourceAsStream("test.properties"));
            log = LogManager.getLogger(TestProperties.class);
            log.debug("Выполнено чтение настроек из файла .properties");
        } catch (IOException e) {
            System.setProperty("log.filename", "logs/test.log");
            System.setProperty("chrome.driver.path", "src/main/resources/drivers/chromedriver.exe");
            System.setProperty("gecko.driver.path", "src/main/resources/drivers/geckodriver.exe");
            System.setProperty("ie.driver.path", "src/main/resources/drivers/iedriverserver.exe");
            System.setProperty("start-maximized", "false");
            System.setProperty("implicit.wait", "10");
            System.setProperty("explicit.wait", "10");

            log = LogManager.getLogger(TestProperties.class);
            log.error("Ошибка чтения настроек из файла .properties: {}", e.getMessage());
            log.debug("Установлены настройки по умолчанию");
        }
    }
}
