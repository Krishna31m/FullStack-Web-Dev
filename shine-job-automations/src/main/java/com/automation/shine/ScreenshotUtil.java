package com.automation.shine;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotUtil.class);
    private static final String SCREENSHOT_DIR = "screenshots/";

    static {
        // Create screenshots directory if it doesn't exist
        Path path = Paths.get(SCREENSHOT_DIR);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                logger.info("Created screenshots directory: {}", SCREENSHOT_DIR);
            } catch (IOException e) {
                logger.error("Failed to create screenshots directory: {}", e.getMessage());
            }
        }
    }

    public static String takeScreenshot(WebDriver driver, String screenshotName) {
        try {
            // Create timestamp for unique filename
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String fileName = SCREENSHOT_DIR + screenshotName + "_" + timestamp + ".png";

            // Take screenshot
            File screenshotFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

            // Save screenshot
            Path destination = Paths.get(fileName);
            Files.copy(screenshotFile.toPath(), destination);

            logger.info("Screenshot saved: {}", fileName);
            return fileName;
        } catch (IOException e) {
            logger.error("Failed to take screenshot: {}", e.getMessage());
            return null;
        }
    }
}