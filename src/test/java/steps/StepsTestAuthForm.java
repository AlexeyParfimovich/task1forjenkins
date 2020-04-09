package steps;

import pages.PageAuthForm;
import io.qameta.allure.Allure;
import webdriver.WebDriverManager;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import io.cucumber.java.ru.*;

import org.testng.Assert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class StepsTestAuthForm {
    private final Logger log = LogManager.getLogger(getClass());

    protected PageAuthForm page = new PageAuthForm();
    protected WebDriverWait waiter = WebDriverManager.getWaiter();

//    @Attachment(value = "{0}", type = "image/png", fileExtension = "png.")
    public void addScreenshot(String title) {
        try {
            Allure.addAttachment(title, FileUtils.openInputStream(page.getScreenshot()));
            log.debug("Сделан скиншот на шаге {}", title);
        } catch (IOException e) {
            log.error("Ошибка записи скриншота: {}", e.getMessage());
        }
    }

    @Пусть("открыт браузер и введен адрес \"(.*)\"$")
    public void openBrowserAndFollowLink(String url) {
        page.openPage(url);
        addScreenshot("Страница сайта загружена");
    }

    @И("пользователь нажимает {string}")
    public void clickLoginFormOpenButton(String name) {
        try {
            waiter.until(elementToBeClickable(page.get(name)));
            page.get(name).click();
        } catch (TimeoutException e) {
            Assert.fail("Кнопка авторизации не доступна! " + e.getMessage());
        } finally {
            addScreenshot("Вызов окна авторизации");
        }
    }

    @Когда("появилось окно Авторизации")
    public void loginFormDisplayed() {
        try {
            waiter.until(visibilityOfAllElements(page.authFormTitle, page.authFormUsernameField, page.authFormPasswordField));
        } catch (TimeoutException e) {
            Assert.fail("Окно авторизации не отобразилось! " + e.getMessage());
        } finally {
            addScreenshot("Отображение окна авторизации");
        }
    }

    @То("пользователь вводит логин \"([^\"]*)\"$")
    public void typeUsername(String username) {
        try {
            waiter.until(elementToBeClickable(page.authFormUsernameField));
            page.authFormUsernameField.click();
            page.authFormUsernameField.sendKeys(username);
        } catch  (TimeoutException e) {
            Assert.fail("Поле для ввода имени пользователя не доступно для ввода! " + e.getMessage());
        } finally {
            addScreenshot("Ввод логина");
        }
    }

    @И("пользователь вводит пароль \"([^\"]*)\"$")
    public void typePassword(String password) {
        try {
            waiter.until(elementToBeClickable(page.authFormPasswordField));
            page.authFormPasswordField.click();
            page.authFormPasswordField.sendKeys(password);
        } catch  (TimeoutException e) {
            Assert.fail("Поле для ввода пароля не доступно для ввода! " + e.getMessage());
        } finally {
            addScreenshot("Ввод пароля");
        }
    }

    @И("пользователь нажимает кнопку Входа")
    public void clickLoginSubmitButton() {
        try {
            waiter.until(elementToBeClickable(page.authFormSubmitButton));
            page.authFormSubmitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Кнопка для авторизации пользователя не доступна для нажатия! " + e.getMessage());
        } finally {
            addScreenshot("Нажатие кнопки входа");
        }
    }

    @Тогда("появилось Сообщение об неправильных учетных данных")
    public void incorrectCredentialsAlert() {
        try {
            waiter.until(visibilityOfAllElements(page.authFormIncorrectCredentialsAlert));
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается сообщение об неправильных учетных данных " + e.getMessage());
        } finally {
            addScreenshot("Отображение сообщения о неправильных учетных данных");
        }
    }

    @Тогда("появилось Сообщение об незаполненных учетных данных")
    public void emptyCredentialsAlert() {
        try {
            waiter.until(visibilityOfAllElements(page.authFormEmptyCredentialsAlert));
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается сообщение об незаполненных учетных данных " + e.getMessage());
        } finally {
            addScreenshot("Отображение сообщения о неполных учетных данных");
        }
    }

    @Если("отображается Пиктограмма активного пользователя")
    public void activeUserAvatarDisplayed() {
        try {
            waiter.until(visibilityOfAllElements(page.userAccountFormOpenLink));
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается Пиктограмма активного пользователя " + e.getMessage());
        } finally {
            addScreenshot("Отображение пиктограммы активного пользователя");
        }
    }

    @И("пользователь нажимает на Пиктограмму активного пользователя")
    public void clickActiveUserAvatar() {
        try {
            waiter.until(elementToBeClickable(page.userAccountFormOpenLink));
            page.userAccountFormOpenLink.click();
        } catch  (TimeoutException e) {
            Assert.fail("Пиктограмма активного пользователя не доступна для нажатия" + e.getMessage());
        } finally {
            addScreenshot("Нажатие на пиктограмму активного пользователя");
        }
    }

    @И("пользователь проверяет что в Заголовке личного кабинета отображается имя \"([^\"]*)\"$")
    public void activeUserTitleDisplayed(String username) {
        try {
            waiter.until(textToBePresentInElement(page.userAccountFormTitle,username));
        } catch  (TimeoutException e) {
            Assert.fail("Не отображается имя активного пользователя в Заголовке личного кабинета " + e.getMessage());
        } finally {
            addScreenshot("Отображение имени пользователя в личном кабинете");
        }
    }

    @Тогда("пользователь нажимает на кнопку Выхода из учетной записи")
    public void exitActiveUserAccount() {
        try {
            waiter.until(elementToBeClickable(page.userAccountExitButton));
            page.userAccountExitButton.click();
        } catch  (TimeoutException e) {
            Assert.fail("Кнопка выхода из учетной записи не доступна для нажатия" + e.getMessage());
        }

        try {
            WebDriverManager.getDriver().switchTo().alert().accept();
        } catch (NoAlertPresentException e) {
            log.error("No expected Alert present!",e);
        }

        addScreenshot("Подтверждение выхода из учетной записи");
    }
}
