package com.automation.shine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JobDetailsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    @FindBy(css = ".job_title")
    private WebElement jobTitleElement;

    @FindBy(css = ".company_name")
    private WebElement companyNameElement;

    @FindBy(css = "#applyButton")
    private WebElement applyButton;

    @FindBy(css = ".apply-success-message")
    private WebElement successMessage;

    public JobDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public String getJobTitle() {
        return wait.until(ExpectedConditions.visibilityOf(jobTitleElement)).getText();
    }

    public String getCompanyName() {
        return companyNameElement.getText();
    }

    public void clickApplyButton() {
        wait.until(ExpectedConditions.elementToBeClickable(applyButton)).click();
    }

    public boolean isApplicationSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOf(successMessage));
            return successMessage.getText().toLowerCase().contains("applied") ||
                    successMessage.getText().toLowerCase().contains("success");
        } catch (Exception e) {
            return false;
        }
    }
}