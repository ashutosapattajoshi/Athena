package stepdefs;

import org.openqa.selenium.WebDriver;

import base.DriverFactory;
import config.ConfigReader;
import io.cucumber.java.en.*;
import pages.FlipkartHomePage;
import pages.FlipkartLoginPage;
import utils.AssertionUtil;

public class FlipkartLoginSteps {

    FlipkartHomePage homePage;
    FlipkartLoginPage loginPage;
    WebDriver driver = DriverFactory.getDriver();

    @Given("user is on Flipkart homepage")
    public void user_is_on_flipkart_homepage() throws Exception {
        //ScenarioContext.setCurrentStep("Given user is on Flipkart homepage");
        driver.get(ConfigReader.getProperty("url"));
        Thread.sleep(3000);
	        AssertionUtil.assertTrue(driver.getTitle().contains("Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!"), "Title doesn't match");
    }

    @When("user closes the login popup")
    public void user_closes_the_login_popup() {
        //ScenarioContext.setCurrentStep("When user closes the login popup");
        homePage = new FlipkartHomePage(driver);
        homePage.closeLoginPopupIfPresent();

    }

    @When("user clicks on Login and enters {string}")
    public void user_clicks_on_login_and_enters(String phoneNumber) {
    	//ScenarioContext.setCurrentStep("When user clicks on Login and enters phoneNumber");
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
    	homePage = new FlipkartHomePage(driver);
        homePage.searchProduct(product);
    }

    @Then("product search results should be displayed with {string}")
    public void product_search_results_should_be_displayed(String product) {
        // Add assertion later if needed
    	homePage = new FlipkartHomePage(driver);
        AssertionUtil.assertTrue(
        	    homePage.isSearchResultDisplayed(product),
        	    "Product not found in search results!"
        	);
        System.out.println("Product search simulated");
    }
}
