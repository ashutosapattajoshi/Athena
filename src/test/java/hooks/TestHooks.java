
package hooks;

import io.cucumber.java.*;
import utils.ScreenshotUtil;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

import base.DriverFactory;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestHooks {

    private static final Logger logger = LogManager.getLogger(TestHooks.class);

    @Before
    public void setUp(Scenario scenario) {
        DriverFactory.initDriver();
        logger.info("▶ Starting Scenario: " + scenario.getName());
    } 
    
//    @AfterStep
//    public void afterStep(Scenario scenario) {
//        String stepInfo = scenario.getName();
//
//        try {
//            if (scenario.isFailed()) {
//                String screenshotPath = ScreenshotUtil.takeScreenshot(stepInfo);
//                if (ExtentCucumberAdapter.getCurrentStep() != null) {
//                    //ExtentCucumberAdapter.addTestStepLog("❌ Step failed: " + stepInfo);
//                    ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(screenshotPath);
//                }
//                logger.error("Step failed: " + stepInfo);
//            } else {
//                if (ExtentCucumberAdapter.getCurrentStep() != null) {
//                    //ExtentCucumberAdapter.addTestStepLog("✅ Step passed: " + stepInfo);
//                }
//                logger.info("Step passed: " + stepInfo);
//            }
//        } catch (Exception e) {
//            logger.error("Error in afterStep logging: " + e.getMessage(), e);
//        }
//    }
    @AfterStep
    public void afterStep(Scenario scenario) {
        try {
            if (scenario.isFailed()) {
                String stepText = StepEventListener.getCurrentStepText(); // ✅ Actual Gherkin step
                String base64 = ScreenshotUtil.captureScreenshotAsBase64();

                String html = "❌ Failed at Step: <b>" + stepText + "</b><br>" +
                        "<a href='data:image/png;base64," + base64 + "' target='_blank'>" +
                        "<img src='data:image/png;base64," + base64 + "' height='120' style='border:1px solid #ccc; box-shadow:2px 2px 6px #888;'/>" +
                        
                        "</a>";

                ExtentCucumberAdapter.addTestStepLog(html);
                logger.error("Step failed: " + stepText);
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
