package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtil;

public class FlipkartHomePage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public FlipkartHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        elementUtil = new ElementUtil(driver);
    }

    // Elements

    @FindBy(css = "button._2KpZ6l._2doB4z") // Close login popup
    private WebElement closeLoginPopupBtn;

    @FindBy(css = "input[name='q']") // Search bar
    private WebElement searchInput;

    @FindBy(css = "button[type='submit']") // Search button
    private WebElement searchButton;

    @FindBy(xpath = "//a[text()='Login']") // Login link (header)
    private WebElement loginLink;

    // Actions

    public void closeLoginPopupIfPresent() {
        elementUtil.waitForElementToBeVisible(closeLoginPopupBtn, 5);
        if (closeLoginPopupBtn.isDisplayed()) {
            closeLoginPopupBtn.click();
        }
    }

    public void clickLoginLink() {
        elementUtil.waitForElementToBeClickable(loginLink, 10).click();
    }

    public void searchProduct(String productName) {
        elementUtil.waitForElementToBeVisible(searchInput, 10).sendKeys(productName);
        elementUtil.waitForElementToBeClickable(searchButton, 5).click();
    }
    
    public boolean isSearchResultDisplayed(String expectedKeyword) {
        List<WebElement> productTitles = driver.findElements(By.cssSelector("div.WOvzF4")); // Adjust selector if needed

        for (WebElement title : productTitles) {
            if (title.getText().toLowerCase().contains(expectedKeyword.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

}
