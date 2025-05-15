package tests;

import base.BaseTest;
import org.testng.annotations.Test;
import pages.FlipkartHomePage;
import pages.FlipkartLoginPage;


public class FlipkartLoginAndSearchTest extends BaseTest {

    @Test
    public void loginAndSearchProductTest() {
        FlipkartHomePage homePage = new FlipkartHomePage(driver);
        FlipkartLoginPage loginPage = new FlipkartLoginPage(driver);

        // Close popup if it appears
        homePage.closeLoginPopupIfPresent();

        // Click login (optional: may open a modal or new page depending on flow)
        homePage.clickLoginLink();

        // Simulate login (replace with valid data if needed)
        loginPage.enterUsername("9999999999");
        loginPage.clickRequestOTP(); // or loginPage.clickLogin();

        // Simulate product search
        homePage.searchProduct("Samsung Galaxy");

        // You can add assertions later for product result validation
    }
}
