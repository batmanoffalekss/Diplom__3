package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    final static Properties properties;

    // Загружаем настройки один раз при старте
    static {
        try {
            properties = new Properties();
            properties.load(new FileInputStream("src/test/resources/browser.properties"));
        } catch (IOException e) {
            throw new RuntimeException("Ошибка загрузки файла browser.properties", e);
        }
    }

    public static WebDriver getNewDriver() {
        String browser = properties.getProperty("browser", "chrome").toLowerCase();

        switch (browser) {
            case "chrome":
                return createChromeDriver();
            case "yandex":
                return createYandexDriver();
            default:
                throw new IllegalArgumentException("Неверный браузер: " + browser + ", поддерживаются: chrome, yandex");
        }
    }

    private static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Автоматически устанавливаем правильную версию ChromeDriver
        WebDriverManager.chromedriver().setup();

        return new ChromeDriver(options);
    }

    private static WebDriver createYandexDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        // Указываем путь к драйверу для Yandex Browser
        String yandexDriverPath = "./src/test/resources/yandexdriver.exe";
        System.setProperty("webdriver.chrome.driver", yandexDriverPath);

        // Указываем путь к бинарнику Yandex Browser
        String yandexPath = properties.getProperty("yandex.browser.path");
        if (yandexPath == null || yandexPath.isEmpty()) {
            throw new IllegalArgumentException("Нужно указать путь к браузеру Yandex в параметрах 'yandex.browser.path'.");
        }
        options.setBinary(yandexPath);

        return new ChromeDriver(options);
    }
}
