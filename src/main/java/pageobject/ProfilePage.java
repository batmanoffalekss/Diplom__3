package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProfilePage {
    WebDriver driver;

    public ProfilePage(WebDriver driver) {

        this.driver = driver;
    }

    private final By restoreFormHeading = By.xpath(".//*[text()='Восстановление пароля']");
    private final By rememberedPasswordLink = By.xpath(".//a[text()='Войти']");


    @Step("Ожидание загрузки страницы «Восстановление пароля»")
    public void waitProfilePageLoad() {
        new WebDriverWait(driver, Duration.ofSeconds(15)).until(ExpectedConditions.visibilityOfElementLocated(restoreFormHeading));
    }

    @Step("Нажатие на ссылку «Войти» под формой восстановления")
    public void clickRememberedPassword() {
        driver.findElement(rememberedPasswordLink).click();
    }
}
