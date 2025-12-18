package tests;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.ConstructorPage;

public class ConstructorTest extends BaseTest {
    WebDriver driver;
    ConstructorPage constructorPage;

    @Test
    @DisplayName("Проверка: переключение на раздел «Булки» с раздела «Соусы»")
    @Description("При открытии главной страницы на разделе «Булки» переход на раздел «Соусы» и обратно. Проверяется наличие активного CSS-класса у вкладки.")
    public void bunsActiveOnLoadTest() {
        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickSaucesLink();
        constructorPage.waitForSaucesActive(15);
        constructorPage.clickBunsLink();
        constructorPage.waitForBunsActive(15);

        Assert.assertThat(constructorPage.getClassNameBuns(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }

    @Test
    @DisplayName("Переключение на раздел «Соусы»")
    @Description("Пользователь может переключиться с раздела «Булки» на раздел «Соусы». Проверяется, что вкладка «Соусы» становится активной после клика.")
    public void switchSaucesTest() {
        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickSaucesLink();
        constructorPage.waitForSaucesActive(15);
        Assert.assertThat(constructorPage.getClassNameSauces(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }

    @Test
    @DisplayName("Переключение на раздел «Начинки»")
    @Description("Пользователь может переключиться на раздел «Начинки». Проверяется, что вкладка «Начинки» становится активной после клика.")
    public void switchFillingsTest() {
        constructorPage = new ConstructorPage(driver);
        constructorPage.waitForEnterAccountButton();
        constructorPage.clickFillingsLink();
        constructorPage.waitForFillingsActive(15);
        Assert.assertThat(constructorPage.getClassNameFillings(), CoreMatchers.containsString("tab_tab_type_current__2BEPc"));
    }
}
