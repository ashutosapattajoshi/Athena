package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ElementUtil;

public class FlipkartLoginPage {

    private WebDriver driver;
    private ElementUtil elementUtil;

    public FlipkartLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        elementUtil = new ElementUtil(driver);
    }

    // Elements

    @FindBy(xpath = "//input[@class='_2IX_2- VJZDxU']") // Mobile/email input
    private WebElement usernameField;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Request OTP')]")
    private WebElement requestOtpButton;

    @FindBy(xpath = "//button[@type='submit' and contains(.,'Login')]")
    private WebElement loginButton;

    @FindBy(xpath = "//div[@class='KzDlHZ']") // Error message (optional)
    private WebElement errorMsg;

    // Actions

    public void enterUsername(String username) {
        elementUtil.waitForElementToBeVisible(usernameField, 10).sendKeys(username);
    }

    public void clickRequestOTP() {
        elementUtil.waitForElementToBeClickable(requestOtpButton, 5).click();
    }

    public void clickLogin() {
        elementUtil.waitForElementToBeClickable(loginButton, 5).click();
    }

    public String getErrorMessage() {
        return elementUtil.waitForElementToBeVisible(errorMsg, 5).getText();
    }
}
