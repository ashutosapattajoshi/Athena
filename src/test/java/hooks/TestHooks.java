package hooks;

import io.cucumber.java.*;
import base.DriverFactory;
import utils.ScreenshotUtil;
import utils.AssertionUtil;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import java.io.IOException;

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
        String stepInfo = scenario.getName(); // or fetch from context if needed
        if (scenario.isFailed()) {
            String screenshotPath = ScreenshotUtil.takeScreenshot(stepInfo);
            ExtentCucumberAdapter.addTestStepLog("❌ Step failed: " + stepInfo);
            try {
				ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            logger.error("Step failed: " + stepInfo);
        } else {
            ExtentCucumberAdapter.addTestStepLog("✅ Step passed: " + stepInfo);
            logger.info("Step passed: " + stepInfo);
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        logger.info("▶ Finished Scenario: " + scenario.getName() + " - Status: " + scenario.getStatus());
        AssertionUtil.assertAllSoftAsserts();
        DriverFactory.quitDriver();
    }
}
