package stepdefs;

import org.openqa.selenium.WebDriver;

import base.BaseTest;
import base.DriverFactory;
import config.ConfigReader;
import io.cucumber.java.en.*;
import pages.FlipkartHomePage;
import pages.FlipkartLoginPage;
import utils.AssertionUtil;
import utils.ScenarioContext;

public class FlipkartLoginSteps extends BaseTest {

    FlipkartHomePage homePage;
    FlipkartLoginPage loginPage;
    WebDriver driver = DriverFactory.getDriver();

    @Given("user is on Flipkart homepage")
    public void user_is_on_flipkart_homepage() {
        ScenarioContext.setCurrentStep("Given user is on Flipkart homepage");
        driver.get(ConfigReader.getProperty("url"));
        AssertionUtil.assertTrue(driver.getTitle().contains("Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!"), "Title doesn't match", driver);
    }

    @When("user closes the login popup")
    public void user_closes_the_login_popup() {
        ScenarioContext.setCurrentStep("When user closes the login popup");
        homePage = new FlipkartHomePage(driver);
        homePage.closeLoginPopupIfPresent();

    }

    @When("user clicks on Login and enters {string}")
    public void user_clicks_on_login_and_enters(String phoneNumber) {
    	ScenarioContext.setCurrentStep("When user clicks on Login and enters phoneNumber");
        homePage.clickLoginLink();
        loginPage = new FlipkartLoginPage(driver);
        loginPage.enterUsername(phoneNumber);
    }

    @When("user requests OTP")
    public void user_requests_otp() {
        loginPage.clickRequestOTP();
    }

    @When("user searches for {string}")
    public void user_searches_for(String product) {
        homePage.searchProduct(product);
        AssertionUtil.assertTrue(
        	    homePage.isSearchResultDisplayed("iPhone"),
        	    "Product not found in search results!",
        	    DriverFactory.getDriver()
        	);

    }

    @Then("product search results should be displayed")
    public void product_search_results_should_be_displayed() {
        // Add assertion later if needed
        System.out.println("Product search simulated");
    }
}
