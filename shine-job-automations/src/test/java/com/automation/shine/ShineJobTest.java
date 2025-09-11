package com.automation.shine;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import org.testng.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShineJobTest extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(ShineJobTest.class);
    private LoginPage loginPage;
    private JobSearchPage jobSearchPage;
    private JobDetailsPage jobDetailsPage;

    // Test data - replace with actual credentials
    private final String EMAIL = "krishnamukhiya842@gmail.com";
    private final String PASSWORD = "Krishna@1";
    private final String JOB_TITLE = "Software Tester";
    private final String LOCATION = "Hyderabad";
    private final String EXPERIENCE = "2";

    private String jobTitle;
    private String companyName;

    @BeforeMethod
    public void setUpPages() {
        loginPage = new LoginPage(driver);
        jobSearchPage = new JobSearchPage(driver);
        // jobDetailsPage will be initialized when needed
    }

    @Test(priority = 1)
    public void testLogin() {
        logger.info("Starting login test...");

        // Navigate to login page
        loginPage.navigateToLoginPage();

        // Perform login
        loginPage.login(EMAIL, PASSWORD);

        // Verify login success
        Assert.assertTrue(loginPage.isLoginSuccessful(), "Login was not successful");

        // Take screenshot
        ScreenshotUtil.takeScreenshot(driver, "login_success");

        // Log success
        logger.info("Login Success | Title: {} | URL: {}", driver.getTitle(), driver.getCurrentUrl());
        System.out.println("Login Success | Title: " + driver.getTitle() + " | URL: " + driver.getCurrentUrl());
    }

    @Test(priority = 2, dependsOnMethods = "testLogin")
    public void testJobSearch() {
        logger.info("Starting job search test...");

        // Perform job search
        jobSearchPage.searchJob(JOB_TITLE, LOCATION, EXPERIENCE);

        // Verify search results
        Assert.assertTrue(jobSearchPage.areSearchResultsDisplayed(), "Job search results not displayed");

        // Take screenshot
        ScreenshotUtil.takeScreenshot(driver, "after_search");

        // Log search completion
        logger.info("Job search done for {}, {}, {} years experience", JOB_TITLE, LOCATION, EXPERIENCE);
        System.out.println("Job search done for " + JOB_TITLE + ", " + LOCATION + ", " + EXPERIENCE + " years experience");
    }

    @Test(priority = 3, dependsOnMethods = "testJobSearch")
    public void testJobSelection() {
        logger.info("Starting job selection test...");

        // Get job details before selecting
        jobTitle = jobSearchPage.getSecondJobTitle();
        companyName = jobSearchPage.getSecondJobCompany();

        // Select the second job
        jobSearchPage.selectSecondJob();

        // Initialize job details page
        jobDetailsPage = new JobDetailsPage(driver);

        // Verify we're on job details page
        String actualJobTitle = jobDetailsPage.getJobTitle();
        Assert.assertNotNull(actualJobTitle, "Not on job details page");

        // Take screenshot
        ScreenshotUtil.takeScreenshot(driver, "job_selected");

        logger.info("Selected Job: {} at {}", jobTitle, companyName);
    }

    @Test(priority = 4, dependsOnMethods = "testJobSelection")
    public void testJobApplication() {
        logger.info("Starting job application test...");

        // Apply for the job
        jobDetailsPage.clickApplyButton();

        // Verify application success
        Assert.assertTrue(jobDetailsPage.isApplicationSuccessful(), "Job application was not successful");

        // Take screenshot
        ScreenshotUtil.takeScreenshot(driver, "after_apply");

        // Log application details
        logger.info("Applied for Job: {} at {}", jobTitle, companyName);
        logger.info("Confirmation: Application Submitted Successfully");

        System.out.println("Applied for Job: " + jobTitle + " at " + companyName);
        System.out.println("Confirmation: Application Submitted Successfully");
    }

    @AfterMethod
    public void afterEachTest() {
        // Additional cleanup if needed between tests
    }
}