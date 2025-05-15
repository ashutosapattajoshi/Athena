package utils;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AssertionUtil {

	private static SoftAssert softAssert = new SoftAssert();
    private static final Logger logger = LogManager.getLogger(AssertionUtil.class);
    private static final String SCREENSHOT_DIR = "screenshots/";

    // Ensures screenshot directory exists
    static {
        File dir = new File(SCREENSHOT_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void assertTrue(boolean condition, String message, WebDriver driver) {
        try {
            Assert.assertTrue(condition, message);
            logger.info("✅ ASSERT TRUE PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT TRUE FAILED: " + message);
            takeScreenshot(driver, "assertTrueFail");
            throw e;
        }
    }

    public static void assertFalse(boolean condition, String message, WebDriver driver) {
        try {
            Assert.assertFalse(condition, message);
            logger.info("✅ ASSERT FALSE PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT FALSE FAILED: " + message);
            takeScreenshot(driver, "assertFalseFail");
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message, WebDriver driver) {
        try {
            Assert.assertEquals(actual, expected, message);
            logger.info("✅ ASSERT EQUALS PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT EQUALS FAILED: " + message);
            takeScreenshot(driver, "assertEqualsFail");
            throw e;
        }
    }

    public static void fail(String message, WebDriver driver) {
        logger.error("❌ ASSERT FAIL: " + message);
        takeScreenshot(driver, "assertFail");
        Assert.fail(message);
    }

    private static void takeScreenshot(WebDriver driver, String prefix) {
        if (driver == null) return;

        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String destPath = SCREENSHOT_DIR + prefix + "_" + timestamp + ".png";

        try {
            Files.copy(src.toPath(), new File(destPath).toPath());
            logger.info("📸 Screenshot saved at: " + destPath);
        } catch (IOException e) {
            logger.error("❗ Failed to save screenshot: " + e.getMessage());
        }
    }
    
    public static void softAssertTrue(boolean condition, String message) {
        if (!condition) {
            logger.warn("❌ SoftAssert TRUE failed: " + message);
        } else {
            logger.info("✅ SoftAssert TRUE passed: " + message);
        }
        softAssert.assertTrue(condition, message);
    }

    public static void softAssertEquals(Object actual, Object expected, String message) {
        if (!actual.equals(expected)) {
            logger.warn("❌ SoftAssert EQUALS failed: " + message + " | Expected: " + expected + ", Actual: " + actual);
        } else {
            logger.info("✅ SoftAssert EQUALS passed: " + message);
        }
        softAssert.assertEquals(actual, expected, message);
    }

    // Call this at the end of test to evaluate all soft asserts
    public static void assertAllSoftAsserts() {
        softAssert.assertAll();
    }
    
}
