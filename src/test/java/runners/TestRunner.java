package runners;
import org.testng.annotations.DataProvider;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = {"src/test/resources/features/flipkart_login.feature", "src/test/resources/features/flipkart_search.feature"},
	    glue = {"stepdefs", "hooks"},
	    plugin = {
	        "pretty",
	        "html:target/cucumber-html-report.html",
	        "json:target/cucumber-report.json",
	        "testng:target/testng-cucumber.xml",
	        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
	    },
	    monochrome = true,
	    dryRun = false
	    //tags = "@smoke"
	)
	public class TestRunner extends AbstractTestNGCucumberTests {
	    @Override
	    @DataProvider(parallel = true)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
	
	}