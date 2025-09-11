package com.automation.shine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class JobSearchPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    @FindBy(css = "input[placeholder='Job Title, Skills']")
    private WebElement jobTitleInput;

    @FindBy(css = "input[placeholder='Location']")
    private WebElement locationInput;

    @FindBy(css = "input[placeholder='Experience']")
    private WebElement experienceInput;

    @FindBy(css = "button[type='submit']")
    private WebElement searchButton;

    @FindBy(css = ".searchList")
    private WebElement searchResults;

    @FindBy(css = ".searchList .search_list_item:nth-child(2)")
    private WebElement secondJobResult;

    public JobSearchPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void searchJob(String jobTitle, String location, String experience) {
        wait.until(ExpectedConditions.visibilityOf(jobTitleInput)).clear();
        jobTitleInput.sendKeys(jobTitle);

        locationInput.clear();
        locationInput.sendKeys(location);

        experienceInput.clear();
        experienceInput.sendKeys(experience);

        searchButton.click();
    }

    public boolean areSearchResultsDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(searchResults));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void selectSecondJob() {
        wait.until(ExpectedConditions.elementToBeClickable(secondJobResult)).click();
    }

    public String getSecondJobTitle() {
        return secondJobResult.findElement(By.cssSelector(".job_title")).getText();
    }

    public String getSecondJobCompany() {
        return secondJobResult.findElement(By.cssSelector(".company_name")).getText();
    }
}