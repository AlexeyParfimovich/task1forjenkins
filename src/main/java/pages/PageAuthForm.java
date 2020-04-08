package pages;

import entities.PageObject;
import entities.NameOfElement;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PageAuthForm extends PageObject {
    private final Logger log = LogManager.getLogger(getClass());

    @NameOfElement("Кнопка_вызова_формы_авторизации")
    @FindBy(xpath = "//button[text()='Войти']")
    public WebElement authFormOpenButton;

    @FindBy(xpath = "//h4[text()='Войти']")
    public WebElement authFormTitle;

    @FindBy(xpath = "//input[@id='id_username']")
    public WebElement authFormUsernameField;

    @FindBy(xpath = "//input[@id='id_password']")
    public WebElement authFormPasswordField;

    @FindBy(xpath = "//button[@type='submit']")
    public WebElement authFormSubmitButton;

    @FindBy(xpath = "//a[contains(.,'Забыли пароль')]")
    public WebElement authFormForgottenPswdLink;

    @NameOfElement("Поле_ввода_логина")
    @FindBy(xpath = "//p[text()='Логин или пароль неправильны.']")
    public WebElement authFormIncorrectCredentialsAlert ;

    @FindBy(xpath = "//p[text()='Заполните оба поля.']")
    public WebElement authFormEmptyCredentialsAlert;

    @FindBy(xpath = "//li[@class='dropdown']/a[@role='button']")
    public WebElement userAccountFormOpenLink;

    @FindBy(xpath = "//li[@class='dropdown-header']/strong")
    public WebElement userAccountFormTitle;

    @FindBy(xpath = "//button[text()='Выход']")
    public WebElement userAccountExitButton;
}
