package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RegisterPage {
    WebDriver driver;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;
    }

    private final By registerFormHeading = By.xpath(".//*[text()='Регистрация']");
    private final By nameField = By.xpath(".//fieldset[1]//input");
    private final By emailField = By.xpath(".//fieldset[2]//input");
    private final By passwordField = By.xpath(".//fieldset[3]//input");
    private final By registerButton = By.xpath(".//button[text()='Зарегистрироваться']");
    private final By passwordFieldError = By.xpath(".//fieldset[3]//p");
    private final By alreadyRegisteredLink = By.xpath(".//*[text()='Уже зарегистрированы?']/a");

    @Step("Ожидание загрузки страницы «Регистрация»")
    public void waitForPageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(registerFormHeading));
    }

    @Step("Заполнение формы регистрации: имя {name}, email {email}, пароль {password}")
    public void fillInRegistrationForm(String name, String email, String password) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(registerButton).click();
    }

    @Step("Получение текста ошибки под полем «Пароль»")
    public String getPasswordFieldErrorText() {
        return driver.findElement(passwordFieldError).getText();
    }

    @Step("Нажатие на ссылку «Уже зарегистрированы? Войти»")
    public void clickAlreadyRegisteredLink() {
        driver.findElement(alreadyRegisteredLink).click();
    }
}
