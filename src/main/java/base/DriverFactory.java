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
        String runMode = prop.getProperty("runMode", "local");
        String browser = prop.getProperty("browser", "chrome");
        boolean isHeadless = Boolean.parseBoolean(prop.getProperty("headless", "false"));

        try {
            if (runMode.equalsIgnoreCase("local")) {
                setupLocalDriver(browser, isHeadless);
            } else if (runMode.equalsIgnoreCase("remote")) {
                String remoteType = prop.getProperty("remoteType", "grid");
                setupRemoteDriver(browser, remoteType, isHeadless);
            } else {
                throw new RuntimeException("Invalid run mode: " + runMode);
            }
        } catch (Exception e) {
            throw new RuntimeException("Driver initialization failed: " + e.getMessage(), e);
        }

        getDriver().manage().window().maximize();
        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(Long.parseLong(prop.getProperty("implicit.wait", "10"))));
        getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(Long.parseLong(prop.getProperty("page.load.timeout", "30"))));

        return getDriver();
    }

    private static void setupLocalDriver(String browser, boolean isHeadless) {
        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new", "--no-sandbox", "--disable-dev-shm-usage", "--disable-gpu", "--window-size=1920,1080");
             // Fix: add a random user data dir
                options.addArguments("--user-data-dir=/tmp/profile-" + UUID.randomUUID());
            }
            driver.set(new ChromeDriver(options));
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
            if (isHeadless) {
                options.addArguments("--headless");
            }
            driver.set(new FirefoxDriver(options));
        } else {
            throw new RuntimeException("Unsupported browser for local mode: " + browser);
        }
    }

    private static void setupRemoteDriver(String browser, String remoteType, boolean isHeadless) throws Exception {
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

        if (remoteType.equalsIgnoreCase("grid")) {
            URL gridUrl = new URL(prop.getProperty("grid.url"));
            driver.set(new RemoteWebDriver(gridUrl, capabilities));
        } else if (remoteType.equalsIgnoreCase("browserstack")) {
            capabilities.setCapability("browserstack.user", prop.getProperty("bs.user"));
            capabilities.setCapability("browserstack.key", prop.getProperty("bs.key"));
            capabilities.setCapability("name", "Athena Automation Test");
            URL bsUrl = new URL("https://" + prop.getProperty("bs.user") + ":" + prop.getProperty("bs.key") + "@hub-cloud.browserstack.com/wd/hub");
            driver.set(new RemoteWebDriver(bsUrl, capabilities));
        } else {
            throw new RuntimeException("Unsupported remote type: " + remoteType);
        }
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
