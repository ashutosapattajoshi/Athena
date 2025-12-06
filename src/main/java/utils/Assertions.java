//package utils;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.testng.Assert;
//
//import java.io.File;
//
//public class Assertions {
//
//    private WebDriver driver;
//    private WebDriverWait wait;
//    private GenericMethods generic;   // Same as Cypress GenericMethod class
//
//    public Assertions(WebDriver driver) {
//        this.driver = driver;
//        this.wait = new WebDriverWait(driver, 10);
//        this.generic = new GenericMethods(driver);
//    }
//
//    /** Verify Value Equals */
//    public void verifyEquals(String expected, String actual) {
//        Assert.assertEquals(actual, expected, "Values are not equal!");
//    }
//
//    /** Verify element is displayed */
//    public void verifyElementDisplayed(By locator) {
//        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
//        Assert.assertTrue(element.isDisplayed(), "Element is not displayed!");
//    }
//
//    /** Verify element not displayed */
//    public void verifyElementNotDisplayed(By locator) {
//        try {
//            driver.findElement(locator).isDisplayed();
//            Assert.fail("Element is visible but expected NOT to be!");
//        } catch (NoSuchElementException | StaleElementReferenceException e) {
//            Assert.assertTrue(true);
//        }
//    }
//
//    /** Verify element present */
//    public void verifyElementPresent(By locator) {
//        Assert.assertTrue(driver.findElements(locator).size() > 0, "Element not present!");
//    }
//
//    /** Verify element not present */
//    public void verifyElementNotPresent(By locator) {
//        Assert.assertTrue(driver.findElements(locator).isEmpty(), "Element present but should not be!");
//    }
//
//    /** Verify Text equals */
//    public void verifyTextContent(By locator, String expected) {
//        String actual = driver.findElement(locator).getText();
//        Assert.assertEquals(actual, expected, "Text mismatch!");
//    }
//
//    /** Verify Text contains */
//    public void verifyTextContains(By locator, String expected) {
//        String actual = driver.findElement(locator).getText();
//        Assert.assertTrue(actual.contains(expected), "Text does not contain expected!");
//    }
//
//    /** Verify Equals Ignore Space */
//    public void verifyEqualsIgnoreSpace(String expected, String actual) {
//        expected = generic.removeSpaces(expected);
//        actual = generic.removeSpaces(actual);
//        Assert.assertEquals(actual, expected);
//    }
//
//    /** Verify Not Equals */
//    public void verifyNotEquals(String expected, String actual) {
//        Assert.assertNotEquals(actual, expected);
//    }
//
//    /** Verify Condition True */
//    public void verifyConditionTrue(boolean condition) {
//        Assert.assertTrue(condition);
//    }
//
//    /** Verify Condition False */
//    public void verifyConditionFalse(boolean condition) {
//        Assert.assertFalse(condition);
//    }
//
//    /** Verify File Exists */
//    public void verifyFileExists(String path) {
//        File file = new File(path);
//        Assert.assertTrue(file.exists(), "File does not exist!");
//    }
//
//    /** Verify Element Enabled */
//    public void verifyElementEnabled(By locator) {
//        Assert.assertTrue(driver.findElement(locator).isEnabled(), "Element not enabled!");
//    }
//
//    /** Verify Element Not Enabled */
//    public void verifyElementNotEnabled(By locator) {
//        Assert.assertFalse(driver.findElement(locator).isEnabled(), "Element should not be enabled!");
//    }
//
//    /** Verify Element Selected */
//    public void verifyElementSelected(By locator) {
//        Assert.assertTrue(driver.findElement(locator).isSelected());
//    }
//
//    /** Verify Element Not Selected */
//    public void verifyElementNotSelected(By locator) {
//        Assert.assertFalse(driver.findElement(locator).isSelected());
//    }
//
//    /** Verify String contains */
//    public void verifyContains(String expected, String actual) {
//        Assert.assertTrue(actual.contains(expected));
//    }
//
//    /** Verify String does not contain */
//    public void verifyDoesNotContain(String expected, String actual) {
//        Assert.assertFalse(actual.contains(expected));
//    }
//
//    /** Verify Page Title contains */
//    public void verifyPageTitle(String expected) {
//        Assert.assertTrue(driver.getTitle().contains(expected));
//    }
//
//    /** Verify Page URL contains */
//    public void verifyPageUrl(String expected) {
//        Assert.assertTrue(driver.getCurrentUrl().contains(expected));
//    }
//
//    /** Verify Attribute Value */
//    public void verifyAttributeValue(By locator, String attribute, String expected) {
//        String actual = driver.findElement(locator).getAttribute(attribute);
//        Assert.assertTrue(actual.contains(expected));
//    }
//
//    /** Verify CSS Value */
//    public void verifyCssValue(By locator, String property, String expected) {
//        String actual = driver.findElement(locator).getCssValue(property);
//        Assert.assertTrue(actual.contains(expected));
//    }
//
//    /** Verify Greater Than */
//    public void verifyGreaterThan(int value1, int value2) {
//        Assert.assertTrue(value1 > value2);
//    }
//
//    /** Verify Less Than */
//    public void verifyLessThan(int value1, int value2) {
//        Assert.assertTrue(value1 < value2);
//    }
//
//    /** Verify Elements Count */
//    public void verifyElementCountEquals(By locator, int expectedLen) {
//        Assert.assertEquals(driver.findElements(locator).size(), expectedLen);
//    }
//
//    public void verifyElementCountGreaterThan(By locator, int expectedLen) {
//        Assert.assertTrue(driver.findElements(locator).size() > expectedLen);
//    }
//
//    public void verifyElementCountLessThan(By locator, int expectedLen) {
//        Assert.assertTrue(driver.findElements(locator).size() < expectedLen);
//    }
//}
