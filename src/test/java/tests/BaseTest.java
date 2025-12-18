package tests;

import utils.DriverFactory;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import utils.Constants;

public class BaseTest {
    protected WebDriver driver;

    @Before
    public void setUp() {
        driver = DriverFactory.getNewDriver();
        driver.get(Constants.BASE_URL);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
