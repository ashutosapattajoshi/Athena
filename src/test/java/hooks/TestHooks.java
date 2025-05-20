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
        String stepInfo = scenario.getName();

        try {
            if (scenario.isFailed()) {
                String screenshotPath = ScreenshotUtil.takeScreenshot(stepInfo);
                if (ExtentCucumberAdapter.getCurrentStep() != null) {
                    //ExtentCucumberAdapter.addTestStepLog("❌ Step failed: " + stepInfo);
                    ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
                }
                logger.error("Step failed: " + stepInfo);
            } else {
                if (ExtentCucumberAdapter.getCurrentStep() != null) {
                    //ExtentCucumberAdapter.addTestStepLog("✅ Step passed: " + stepInfo);
                }
                logger.info("Step passed: " + stepInfo);
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
