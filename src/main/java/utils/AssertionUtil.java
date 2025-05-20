package utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

public class AssertionUtil {
    private static final Logger logger = LogManager.getLogger(AssertionUtil.class);

    public static void assertTrue(boolean condition, String message) {
        try {
            Assert.assertTrue(condition, message);
            logger.info("✅ ASSERT TRUE PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT TRUE FAILED: " + message);
            throw e;
        }
    }

    public static void assertFalse(boolean condition, String message) {
        try {
            Assert.assertFalse(condition, message);
            logger.info("✅ ASSERT FALSE PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT FALSE FAILED: " + message);
            throw e;
        }
    }

    public static void assertEquals(Object actual, Object expected, String message) {
        try {
            Assert.assertEquals(actual, expected, message);
            logger.info("✅ ASSERT EQUALS PASSED: " + message);
        } catch (AssertionError e) {
            logger.error("❌ ASSERT EQUALS FAILED: " + message);
            throw e;
        }
    }

    public static void fail(String message) {
        logger.error("❌ ASSERT FAIL: " + message);
        Assert.fail(message);
    }
}
