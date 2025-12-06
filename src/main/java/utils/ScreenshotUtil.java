package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import base.DriverFactory;

import java.io.File;
import java.io.IOException;

public class ScreenshotUtil {
    public static String takeScreenshot(String scenarioName) {
        File srcFile = ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.FILE);
        String fileName = scenarioName.replace(" ", "_") + ".png";
        String fullPath = "test-output/screenshots/" + fileName;
        try {
            FileUtils.copyFile(srcFile, new File(fullPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "../screenshots/" + fileName;
    }
    
    public static String captureScreenshotAsBase64() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BASE64);
    }
    
    public static byte[] captureScreenshotAsByte() {
        return ((TakesScreenshot) DriverFactory.getDriver()).getScreenshotAs(OutputType.BYTES);
    }

}