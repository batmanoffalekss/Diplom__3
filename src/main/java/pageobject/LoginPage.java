package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        waitForPageLoad();
    }

    private final By enterFormHeading = By.xpath("//*[contains(text(), 'Вход')]");
    private final By emailField = By.xpath(".//label[text()='Email']/../input");
    private final By passwordField = By.xpath(".//label[text()='Пароль']/../input");
    private final By enterProfileButton = By.xpath(".//button[text()='Войти']");
    private final By registrationLink = By.className("Auth_link__1fOlj");
    private final By recoverPasswordLink = By.xpath(".//a[text()='Восстановить пароль']");

    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(enterFormHeading));
    }

    @Step("Ввод данных пользователя: email {email} и пароль {password}")
    public void userDataEntry(String email, String password) {
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
    }

    @Step("Нажатие на кнопку «Войти»")
    public void clickEnterButton() {
        driver.findElement(enterProfileButton).click();

    }

    @Step("Переход по ссылке «Зарегистрироваться»")
    public void clickRegistrationLink() {
        driver.findElement(registrationLink).click();
    }

    @Step("Переход по ссылке «Восстановить пароль»")
    public void clickRecoverPasswordLink() {
        driver.findElement(recoverPasswordLink).click();
    }

}
