package utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class ElementUtil {

    private WebDriver driver;

    public ElementUtil(WebDriver driver) {
        this.driver = driver;
    }

    // Waits for element to be visible
    public WebElement waitForElementToBeVisible(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.visibilityOf(element));
    }

    // Waits for element to be clickable
    public WebElement waitForElementToBeClickable(WebElement element, int timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    // Click an element with wait
    public void click(WebElement element, int timeoutInSeconds) {
        waitForElementToBeClickable(element, timeoutInSeconds).click();
    }

    // Send keys to element with wait
    public void sendKeys(WebElement element, String text, int timeoutInSeconds) {
        WebElement el = waitForElementToBeVisible(element, timeoutInSeconds);
        el.clear();
        el.sendKeys(text);
    }

    // Get text from element
    public String getText(WebElement element, int timeoutInSeconds) {
        return waitForElementToBeVisible(element, timeoutInSeconds).getText();
    }

    // Check if element is displayed safely
    public boolean isElementDisplayed(WebElement element, int timeoutInSeconds) {
        try {
            return waitForElementToBeVisible(element, timeoutInSeconds).isDisplayed();
        } catch (TimeoutException | NoSuchElementException e) {
            return false;
        }
    }
}
