package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utils.ElementUtil;

public class FlipkartSearchPage {
	
	public WebDriver driver;
	public ElementUtil elementUtil; 
	
	@FindBy(linkText = "Price -- Low to High")
	private WebElement filterPriceLowToHigh;
	
	
	
	
	
	public FlipkartSearchPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		elementUtil = new ElementUtil(driver);
	}
	
	

}
