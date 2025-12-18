package tests;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import model.User;
import model.UserStep;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pageobject.*;
import utils.Constants;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static utils.Constants.BASE_URL;

public class LoginTest extends BaseTest {

    private ConstructorPage constructorPage;
    private LoginPage loginPage;
    private MainPage mainPage;
    private ProfilePage profilePage;
    private RegisterPage registerPage;

    private User user;
    private UserStep userStep = new UserStep();
    private String accessToken;

    @Before
    public void setUp() {
        super.setUp();

        String name = RandomStringUtils.randomAlphanumeric(6);
        String email = RandomStringUtils.randomAlphanumeric(7) + "@yandex.ru";
        String password = RandomStringUtils.randomAlphanumeric(8);
        user = new User(email, password, name, ""); // Создали объект пользователя

        // Регистрация пользователя через API
        ValidatableResponse createResponse = userStep.createUser(user);
        createResponse.assertThat().statusCode(SC_OK); // Проверка успешности регистрации

        accessToken = userStep.extractAccessToken(createResponse); // Получаем токен
        user.setAccessToken(accessToken); // Привязываем токен к пользователю

        driver.get(BASE_URL);
        constructorPage = new ConstructorPage(driver);
    }

    @Test
    @DisplayName("позитивный тест на успешную регистрацию пользователя")
    @Description("Позитивный тест на создание пользователя с заполненными полями")
    public void successfulRegistrationTest() {
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickEnterAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.userDataEntry(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        constructorPage.waitCheckoutButton();
        constructorPage.enterPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                user.getEmail().toLowerCase(), mainPage.getEmailText().toLowerCase());
    }

    @Test
    @DisplayName("позитивный тест на вход по кнопке «Личный кабинет»")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void enterProfileButtonTest() {
        constructorPage.waitForPersonalAccountButton();
        constructorPage.enterPersonalAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.userDataEntry(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        constructorPage.waitCheckoutButton();
        constructorPage.enterPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                user.getEmail().toLowerCase(), mainPage.getEmailText().toLowerCase());
    }

    @Test
    @DisplayName("позитивный тест на вход по кнопке в форме регистрации")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void enterLinkInRegistrationFormTest() {
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickEnterAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.clickRegistrationLink();
        registerPage = new RegisterPage(driver);
        registerPage.waitForPageLoad();
        registerPage.clickAlreadyRegisteredLink();
        loginPage.waitForPageLoad();
        loginPage.userDataEntry(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        constructorPage.waitCheckoutButton();
        constructorPage.enterPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                user.getEmail().toLowerCase(), mainPage.getEmailText().toLowerCase());
    }

    @Test
    @DisplayName("позитивный тест на вход по кнопке в форме восстановления пароля")
    @Description("Позитивный тест на вход через регистрацию по API, логина пользователя и проверки по email")
    public void enterLinkInRecoverPasswordTest() {
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickEnterAccountButton();
        loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.clickRecoverPasswordLink();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        profilePage.clickRememberedPassword();
        loginPage.waitForPageLoad();
        loginPage.userDataEntry(user.getEmail(), user.getPassword());
        loginPage.clickEnterButton();
        constructorPage.waitCheckoutButton();
        constructorPage.enterPersonalAccountButton();
        profilePage = new ProfilePage(driver);
        profilePage.waitProfilePageLoad();
        assertEquals("Email в профиле не совпадает с зарегистрированным (игнорируем регистр)",
                user.getEmail().toLowerCase(), mainPage.getEmailText().toLowerCase());
    }

    @After
    public void tearDown() {
        if (user != null && user.getAccessToken() != null) {
            userStep.deleteUser(user);
        }
        super.tearDown();
    }

}
