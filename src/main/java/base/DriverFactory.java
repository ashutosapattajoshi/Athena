package base;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.remote.*;

import java.net.URL;
import java.time.Duration;
import java.util.*;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static Properties prop = ConfigReader.initProperties();

    public static WebDriver initDriver() {
        // Override with system properties (can be set via Jenkins)
        String runMode = System.getProperty("runMode", prop.getProperty("runMode", "local"));
        String browser = System.getProperty("browser", prop.getProperty("browser", "chrome"));
        boolean isHeadless = Boolean.parseBoolean(System.getProperty("headless", prop.getProperty("headless", "false")));

        try {
            if (runMode.equalsIgnoreCase("local")) {
                setupLocalDriver(browser, isHeadless);
            } else if (runMode.equalsIgnoreCase("remote")) {
                setupRemoteDriver(browser, "grid", isHeadless); // only grid now
            } else {
                throw new RuntimeException("Invalid run mode: " + runMode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Driver initialization failed: " + e.getMessage(), e);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(
                Long.parseLong(prop.getProperty("implicit.wait", "10"))));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(
                Long.parseLong(prop.getProperty("page.load.timeout", "30"))));

        return getDriver();
    }

    private static void setupLocalDriver(String browser, boolean isHeadless) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) options.addArguments("--headless=new");
            driver.set(new ChromeDriver(options));
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("--headless");
            driver.set(new FirefoxDriver(options));
        } else {
            throw new RuntimeException("Unsupported browser for local mode: " + browser);
        }
    }

    private static void setupRemoteDriver(String browser, String remoteType, boolean isHeadless) throws Exception {
        if (!remoteType.equalsIgnoreCase("grid")) {
            throw new RuntimeException("Unsupported remote type: " + remoteType);
        }

        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (browser.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) options.addArguments("--headless=new");
            capabilities.setCapability(ChromeOptions.CAPABILITY, options);
            capabilities.setBrowserName("chrome");
        } else if (browser.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) options.addArguments("--headless");
            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
            capabilities.setBrowserName("firefox");
        } else {
            throw new RuntimeException("Unsupported browser for remote: " + browser);
        }

        URL gridUrl = new URL(prop.getProperty("grid.url")); // Docker Selenium Grid URL
        driver.set(new RemoteWebDriver(gridUrl, capabilities));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}

