package com.ejemplo.testng;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPageFuncional {

    private WebDriver driver;

    private By usernameField = By.id("usuario");
    private By passwordField = By.id("contrasena");
    private By loginButton = By.id("btnLogin");
    private By welcomeMessage = By.id("mensajeBienvenida");

    public LoginPageFuncional(WebDriver driver) {
        this.driver = driver;
    }

    public WebElement getUsernameField() {
        return driver.findElement(usernameField);
    }

    public WebElement getPasswordField() {
        return driver.findElement(passwordField);
    }

    public WebElement getLoginButton() {
        return driver.findElement(loginButton);
    }

    public WebElement getWelcomeMessage() {
        return driver.findElement(welcomeMessage);
    }

    public void enterUsername(String username) {
        getUsernameField().sendKeys(username);
    }

    public void enterPassword(String password) {
        getPasswordField().sendKeys(password);
    }

    public void clickLoginButton() {
        getLoginButton().click();
    }

    public String getWelcomeMessageText() {
        return getWelcomeMessage().getText();
    }
}