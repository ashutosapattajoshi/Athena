package base;

import config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = base.DriverFactory.initDriver();  // if DriverFactory is in src/main/java/base
        driver.get(ConfigReader.getProperty("url"));
    }

    @AfterMethod
    public void tearDown() {
        base.DriverFactory.quitDriver();
    }
}
