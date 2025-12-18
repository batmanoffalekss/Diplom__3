package tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.Test;
import pageobject.ConstructorPage;
import pageobject.LoginPage;
import pageobject.MainPage;
import pageobject.RegisterPage;
import utils.Constants;


public class RegisterTest extends BaseTest {
    private ConstructorPage constructorPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private RegisterPage registerPage;

    String name = RandomStringUtils.randomAlphanumeric(10);
    String email = RandomStringUtils.randomAlphanumeric(7) + "@gufum.com";
    String correctPassword = RandomStringUtils.randomAlphanumeric(6);
    String wrongPassword = RandomStringUtils.randomAlphanumeric(5);

    @Test
    @DisplayName("позитивный тест на успешную регистрацию пользователя")
    @Description("Позитивный тест на создание пользователя с заполненными полями")
    public void successfulRegistrationTest() {
        //вход на главную пейджу и клик личный кабинет
        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForPersonalAccountButton();
        constructorPage.enterPersonalAccountButton();
        //переход по кнопке зарегистрироваться
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.clickRegistrationLink();
        //заполнение формы регистрации
        registerPage = new RegisterPage(driver);
        registerPage.waitForPageLoad();
        registerPage.fillInRegistrationForm(name, email, correctPassword);
        //ждем перехода на главную после регистрации
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        Assert.assertEquals(Constants.LOGIN_PAGE, driver.getCurrentUrl());
        //вводим данные с которыми зарегистрировались
        loginPage.userDataEntry(email, correctPassword);
        loginPage.clickEnterButton();
        constructorPage.waitForPersonalAccountButton();
        //переходим в профиль
        constructorPage.enterPersonalAccountButton();
        mainPage = new MainPage(driver);
        mainPage.waitProfilePageLoad();
        //проверяем данные
        Assert.assertEquals(name, mainPage.getNameText());
        Assert.assertEquals("Email не совпадает (игнорируя регистр)", email.toLowerCase(), mainPage.getEmailText().toLowerCase());
    }

    @Test
    @DisplayName("Проверка на невозможность создание пользователя с некорректным паролем")
    @Description("Негативный тест на невозможность создания пользователя с 5 значным паролем")
    public void errorPasswordTest() {
        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForPersonalAccountButton();
        constructorPage.enterPersonalAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.clickRegistrationLink();
        registerPage = new RegisterPage(driver);
        registerPage.waitForPageLoad();
        registerPage.fillInRegistrationForm(name, email, wrongPassword);
        Assert.assertEquals("Некорректный пароль", registerPage.getPasswordFieldErrorText());
        Assert.assertEquals(Constants.REGISTER_PAGE, driver.getCurrentUrl());
    }
}