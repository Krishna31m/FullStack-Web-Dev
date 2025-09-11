package com.automation.shine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Page URL
    private static final String LOGIN_PAGE_URL = "https://www.shine.com/login/";

    // Locators
    @FindBy(id = "id_email_login")
    private WebElement emailInput;

    @FindBy(id = "id_password")
    private WebElement passwordInput;

    @FindBy(css = "button[type='submit']")
    private WebElement loginButton;

    @FindBy(css = ".icon-user-profile")
    private WebElement profileIcon;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public void navigateToLoginPage() {
        driver.get(LOGIN_PAGE_URL);
        wait.until(ExpectedConditions.urlContains("login"));
    }

    public void login(String email, String password) {
        wait.until(ExpectedConditions.visibilityOf(emailInput)).sendKeys(email);
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
        try {
            wait.until(ExpectedConditions.visibilityOf(profileIcon));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}