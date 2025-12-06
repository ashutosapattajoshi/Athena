package hooks;

import io.cucumber.java.*;
import utils.ScreenshotUtil;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import base.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestHooks {

    private static final Logger logger = LogManager.getLogger(TestHooks.class);

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        logger.info("▶ Starting Scenario: " + scenario.getName());
    } 
    
    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String base64 = ScreenshotUtil.captureScreenshotAsBase64();

                String html = "<a href='data:image/png;base64," + base64 + "' target='_blank'>" +
                        "<img src='data:image/png;base64," + base64 + "' style='width: 50%; max-width: 500px; border:1px solid #ccc; box-shadow:2px 2px 6px #888; cursor: pointer;'/>" + 
                        "</a>";

                ExtentCucumberAdapter.addTestStepLog(html);
            }
        } catch (Exception e) {
            logger.error("Error in afterStep logging: " + e.getMessage(), e);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("▶ Finished Scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());
        DriverFactory.quitDriver();
    }
}