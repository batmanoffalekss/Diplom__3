package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    WebDriver driver;

    public MainPage(WebDriver driver) {

        this.driver = driver;
    }

    private final By profileTextLocator = By.xpath(".//*[contains(text(), 'изменить свои персональные данные')]");
    private final By nameField = By.xpath(".//li[1]//input");
    private final By emailField = By.xpath(".//li[2]//input");

    @Step("Ожидание загрузки страницы профиля")
    public void waitProfilePageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(profileTextLocator));
    }

    @Step("Получение значения поля «Email»")
    public String getEmailText() {
        return driver.findElement(emailField).getAttribute("value");
    }

    @Step("Получение значения поля «Имя»")
    public String getNameText() {
        return driver.findElement(nameField).getAttribute("value");
    }
}
