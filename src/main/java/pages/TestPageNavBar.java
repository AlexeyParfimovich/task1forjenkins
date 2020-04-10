package pages;

import entities.TestPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class TestPageNavBar extends TestPage {
    private final Logger log = LogManager.getLogger(getClass());

    @FindBy(xpath = "//span[text()='Lanit']")
    public WebElement navBarTitle;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[1]/a")
    public WebElement navTopicLink;
    @FindBy(xpath = "//h1[text()='Lanit education']")
    public WebElement navTopicTitle;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[2]/a")
    public WebElement navCategoryLink;
    @FindBy(xpath = "//h1[text()='Категории']")
    public WebElement navCategoryTitle;

    @FindBy(xpath = "//ul[@class='nav navbar-nav']/li[3]/a")
    public WebElement navPosterLink;
    @FindBy(xpath = "//h1[text()='Пользователи']")
    public WebElement navPosterTitle;

    @FindBy(xpath = "//div[@class='navbar-search dropdown']/a/i")
    public WebElement navSearchLink;
    @FindBy(xpath = "//Input[@class='form-control']")
    public WebElement navSearchTitle;

    @FindBy(xpath = "//button[@class='btn navbar-btn btn-default btn-sign-in']")
    public WebElement navLoginLink;
    @FindBy(xpath = "//h4[text()='Войти']")
    public WebElement navLoginTitle;

    @FindBy(xpath = "//button[text()='Регистрация']")
    public WebElement navRegLink;
    @FindBy(xpath = "//h4[text()='Регистрация']")
    public WebElement navRegTitle ;
}
